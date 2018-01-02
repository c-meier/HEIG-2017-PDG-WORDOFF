package ch.heigvd.wordoff.common;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
public interface Constants {
    // source : https://github.com/ManiacDC/TypingAid/tree/master/Wordlists

    int CHALLENGE_SIZE = 7;
    int PLAYER_RACK_SIZE = 7;
    int SWAP_RACK_SIZE = 2;

    String AUTHORIZATION_HEADER = "Authorization";
    String SERVER_ADDRESS = "localhost";
    int SERVER_PORT = 8080;
    String SERVER_URI = "http://"+SERVER_ADDRESS+":"+SERVER_PORT;

    String EASY = "EASY";
    String AVERAGE = "AVERAGE";
    String HARD = "HARD";
    String YOU_RE_SCREWED = "YOU_RE_SCREWED";

    String COMPETITION_TOURNAMENT_NAME = "Tournoi Compétitif";

    // The number of days of the tournament.
    int TOURNAMENT_DURATION = 5;
    int MAX_USER_IN_TOURNAMENT = 20;
    int MAX_GAMES_PER_DAY = 2;

}
