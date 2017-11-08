package ch.heigvd.wordoff.common;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
public interface Constants {
    // source : https://github.com/ManiacDC/TypingAid/tree/master/Wordlists
    String ENGLISH_DICTIONARY = "../Common/src/main/resources/dictionary/english_dico_scrabble.txt";
    String FRENCH_DICTIONARY = "../Common/src/main/resources/dictionary/francais_dico_scrabble.txt";

    int CHALLENGE_SIZE = 7;
    int PLAYER_RACK_SIZE = 7;
    int SWAP_RACK_SIZE = 2;

    String SERVER_ADDRESS = "localhost";
    int SERVER_PORT = 8080;

    String EASY = "EASY";
    String AVERAGE = "AVERAGE";
    String HARD = "HARD";
    String YOU_RE_SCREWED = "YOU_RE_SCREWED";
}
