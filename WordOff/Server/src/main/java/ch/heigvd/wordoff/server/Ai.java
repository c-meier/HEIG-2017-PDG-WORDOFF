package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Constants;

import java.util.TreeMap;

/**
 * Created by Daniel on 21.10.2017.
 */
public class Ai extends Player {
    private String level;

    public Ai(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
