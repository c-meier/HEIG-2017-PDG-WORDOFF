package ch.heigvd.wordoff.common;

/**
 * Project : WordOff
 * Date : 26.09.17
 */
public interface Protocol {
    // Error codes
    int USER_ALREADY_EXISTS = 100;
    int PLAYER_NOT_EXISTS = 101;
    int LANG_NOT_EXISTS = 201;
    int GAME_NOT_EXISTS = 301;
    int NOT_PLAYER_GAME = 302;
    int INVALID_WORD = 303;
    int CHEATING = 304;
    int NON_EXISTANT_PLAYER_LVL = 305;
    int NOT_YOUR_TURN = 306;
    int TOO_MANY_PARTICIPANTS = 421;
}
