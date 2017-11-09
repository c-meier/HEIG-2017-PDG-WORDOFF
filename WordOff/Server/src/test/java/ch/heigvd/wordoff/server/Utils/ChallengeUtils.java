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
