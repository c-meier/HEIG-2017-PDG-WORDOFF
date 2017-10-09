import Net.Client;

import java.io.IOException;

/**
 * Project : WordOff
 * Date : 26.09.17
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world !");

        // dico test
        Dictionary dico = new Dictionary(Main.class.getResource(Constants.FRENCH_DICTIONARY).getPath());
        System.out.println(dico.getAnagrams("astuce"));

        Client client = new Client("127.0.0.1", Constants.SERVER_PORT);
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
