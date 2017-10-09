import Net.ConnexionHandler;
import Net.Server;

import java.io.IOException;

/**
 * Project : WordOff
 * Date : 26.09.17
 */

public class Main {
    public static void main(String[] args) {
        Server server = new Server(Constants.SERVER_PORT);
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("hello server");

    }
}
