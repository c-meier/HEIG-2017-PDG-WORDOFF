/*
 * File: Protocol.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common;

/**
 * Error code used in the protocol to allow for quick understanding of an error.
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
    int NOT_ENOUGH_COINS = 307;
    int GAME_IS_OVER = 308;
    int TOO_MUCH_GAMES_FOR_DAY_X = 309;
    int TOO_MANY_PARTICIPANTS = 421;
    int INVALID_INVITATION_STATUS = 407;
}
