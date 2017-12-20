package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.server.Model.Game;

public class DuelMode extends Mode {
    public Game getGame() {
        return getGames().get(0);
    }


    @Override
    public boolean isEnded() {
        return getGame().isEnded();
    }
}
