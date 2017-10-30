package Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of this class are created whenever a client has arrived and a
 * connection has been established (the instances are created by the TCPServer
 * class). The class is responsible for setting up and cleaning up the
 * communication streams, but delegates the hard work (i.e. the implementation
 * of our own application protocol) to a class that implements the
 * IClientHandler interface.
 *
 * This means that we could reuse this class, develop a new class that
 * implements the IClientHandler interface and implement another application
 * protocol.
 *
 * @author Olivier Liechti
 */
class ClientWorker implements Runnable {

    private static final Logger LOG = Logger.getLogger(ClientWorker.class.getName());

    private final IClientHandler handler;
    private final Socket clientSocket;
    private final InputStream is;
    private final OutputStream os;
    private final Server server;
    private boolean done;

    /**
     * Ctor
     *
     * @param clientSocket the socket of the client
     * @param server       the server instance
     * @throws IOException if anything goes wrong
     */
    public ClientWorker(Socket clientSocket, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.handler = new ConnexionHandler();
        this.server = server;
        this.is = clientSocket.getInputStream();
        this.os = clientSocket.getOutputStream();
    }

    /**
     * Main routine of the worker
     */
    public void run() {
        try {
            handler.handleClientConnection(is, os);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Exception in client handler: {0}", ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            done = true;
            server.notifyClientWorkerDone(this);
            try {
                clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
            try {
                is.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
            try {
                os.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
        }
    }

    /**
     * Check if the worker is done
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Close all sockets and cease everything
     */
    public void notifyServerShutdown() {
        try {
            is.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing input stream on the server: {0}", ex.getMessage());
        }

        try {
            os.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing output stream on the server: {0}", ex.getMessage());
        }

        try {
            clientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing socket on the server: {0}", ex.getMessage());
        }
    }

}
