/*
 * File: ChallengeUtils.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Utils;

import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;

public class ChallengeUtils {
    public static Challenge fillChallenge (LangSet set, Challenge c, String w) {
        for(char l : w.toCharArray()) {
            c.addTile(set.getTileByValue(l));
        }
        return c;
    }
}
