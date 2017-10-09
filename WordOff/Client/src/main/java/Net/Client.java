package Net;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    /*
     * The TCP port to which the client will connect.
     */
    private int port = -1;

    /*
     * The address of the server
     */
    private String addr;

    /*
     * The socket, used to connect with the server.
     */
    private Socket socket;

    /*
     * A flag that indicates whether the server should use secure TLS connexion with
     * each client.
     */
    private boolean usingTLS = true;

    /*
     * The location needed to use the trusted store.
     */
    private final String TRUST_STORE = "/WordOffClientTrustStore.jks";
    private final String TRUST_STORE_PWD = "wordoff";

    public Client(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    /**
     * Call this to use (or not) TLS Encryption
     */
    public void useSecureConnexion(boolean secure) {
        usingTLS = secure;
    }

    /**
     * Connects the client to the server. Uses SSL handshake to set up secure connection.
     *
     * @throws IOException
     */
    public void connect() throws IOException {

        if (socket == null || !socket.isBound()) {
            if (usingTLS) {
                URL truststoreURL = Client.class.getResource(TRUST_STORE);
                System.setProperty("javax.net.ssl.trustStore", truststoreURL.getPath());
                //System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PWD);

                SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                socket = sslsocketfactory.createSocket(addr, port);

                ((SSLSocket) socket).startHandshake();
            } else {
                socket = new Socket(addr, port);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            LOG.log(Level.INFO, br.readLine());
            pw.println("Client");
        }
    }

    /**
     * Disconnects the client from the server.
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        socket.close();
    }

    /**
     * Flag to know if the client is connected
     *
     * @return isConnected
     */
    public boolean isConnected() {
        if (socket != null) {
            return socket.isConnected();
        }
        return false;
    }

}
