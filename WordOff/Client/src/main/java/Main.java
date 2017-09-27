import java.nio.file.Paths;

/**
 * Project : WordOff
 * Date : 26.09.17
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world !");

        // dico test
        Dictionary dico = new Dictionary(Constants.FRENCH_DICTIONARY);
        System.out.println(dico.getAnagrams("gfewgio"));
    }
}
