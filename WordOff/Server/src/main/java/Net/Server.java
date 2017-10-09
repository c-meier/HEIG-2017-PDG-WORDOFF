package Net;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the main logic of the Server. It starts a loop
 * to accept incoming connections on a TCP port. When a new connection request
 * is made by a client, the server spawns a new thread. It instantiates a client
 * handler, to which it delegates the processing of the session (the client
 * handler executes on the thread).
 *
 * It is possible to either create an instance of the Server by
 * specifying an explicit TCP port (typically, the default port defined in the
 * protocol specification), or to let the OS allocate an ephemeral port. While
 * this is not a common approach for TCP servers (because clients generally want
 * to have a well-known port to establish the communication), it is useful in
 * the context of automated tests (which may execute in parallel and therefore
 * need "isolated" servers).
 *
 * @author Olivier Liechti
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    /*
     * The server maintains a list of client workers, so that they can be notified
     * when the server shuts down
     */
    private final List<ClientWorker> clientWorkers = new CopyOnWriteArrayList<>();

    /*
     * The TCP port where client connection requests are accepted. -1 indicates that
     * we want to use an ephemeral port number, assigned by the OS
     */
    private int listenPort = -1;

    /*
     * The server socket, used to accept client connection requests
     */
    private ServerSocket serverSocket;

    /*
     * A flag that indicates whether the server should continue to run (or whether
     * a shutdown is in progress)
     */
    private boolean shouldRun;

    /*
     * A flag that indicates whether the server should use secure TLS connexion with
     * each client.
     */
    private boolean usingTLS = true;

    /*
     * The location and password needed to use the trusted store.
     */
    private final String KEY_STORE = "/WordOffKeyStore.jks";
    private final String KEY_STORE_PWD = "wordoff";

    /**
     * Constructor used to create a server that will accept connections on a known
     * TCP port
     *
     * @param listenPort the TCP port on which connection requests are accepted
     */
    public Server(int listenPort) {
        this.listenPort = listenPort;
    }

    /**
     * Call this to use (or not) TLS Encryption
     */
    public void useSecureConnexion(boolean secure) {
        usingTLS = secure;
    }

    /**
     * Starts the server by loading the certificate keys and the store, creating the thread and booting it up
     *
     * @throws IOException if anything goes wrong
     */
    public void startServer() throws IOException {
        if (serverSocket == null || !serverSocket.isBound()) {
            if (usingTLS) {
                System.setProperty("javax.net.ssl.keyStore", Server.class.getResource(KEY_STORE).getPath());
                System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PWD);

                ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
                serverSocket = ssf.createServerSocket();
            } else {
                serverSocket = new ServerSocket();
            }
            if (listenPort == -1) {
                bindOnEphemeralPort();
            } else {
                bindOnKnownPort(listenPort);
            }
        }

        Thread serverThread = new Thread(() -> {
            shouldRun = true;
            while (shouldRun) {
                try {
                    LOG.log(Level.INFO, "Listening for client connection on {0}", serverSocket.getLocalSocketAddress());
                    Socket clientSocket = serverSocket.accept();
                    LOG.info("New client has arrived...");
                    ClientWorker worker = new ClientWorker(clientSocket, this);
                    clientWorkers.add(worker);
                    LOG.info("Delegating work to client worker...");
                    Thread clientThread = new Thread(worker);
                    clientThread.start();
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, "IOException in main server thread, exit: {0}", ex.getMessage());
                    shouldRun = false;
                }
            }
        });
        serverThread.start();
    }

    /**
     * Indicates whether the server is accepting connection requests, by checking
     * the state of the server socket
     *
     * @return true if the server accepts client connection requests
     */
    public boolean isRunning() {
        return (serverSocket.isBound());
    }

    /**
     * Getter for the TCP port number used by the server socket.
     *
     * @return the port on which client connection requests are accepted if the
     * server socket is created or the wanted port otherwise.
     */
    public int getPort() {
        if (serverSocket == null) {
            return listenPort;
        } else {
            return serverSocket.getLocalPort();
        }
    }

    /**
     * Requests a server shutdown. This will close the server socket and notify
     * all client workers.
     *
     * @throws IOException
     */
    public void stopServer() throws IOException {
        shouldRun = false;
        serverSocket.close();
        for (ClientWorker clientWorker : clientWorkers) {
            clientWorker.notifyServerShutdown();
        }
    }

    /**
     * Bind the socket on the given port
     *
     * @param port the port we want to bind to
     * @throws IOException if binding fails
     */
    private void bindOnKnownPort(int port) throws IOException {
        serverSocket.bind(new InetSocketAddress(port));
    }

    /**
     * Bind the socket on any port
     *
     * @throws IOException if binding fails
     */
    private void bindOnEphemeralPort() throws IOException {
        serverSocket.bind(null);
        listenPort = serverSocket.getLocalPort();
    }

    /**
     * This method is invoked by the client worker when it has completed its
     * interaction with the server (e.g. the user has issued the BYE command, the
     * connection has been closed, etc.)
     *
     * @param worker the worker which has completed its work
     */
    public void notifyClientWorkerDone(ClientWorker worker) {
        clientWorkers.remove(worker);
    }
}
