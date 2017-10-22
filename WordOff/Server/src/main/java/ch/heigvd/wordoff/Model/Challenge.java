package ch.heigvd.wordoff.Model;

import ch.heigvd.wordoff.common.Cases.*;
import ch.heigvd.wordoff.common.logic.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.Cases.SlotType;
import ch.heigvd.wordoff.common.Constants;

public class Challenge {
    private ObservableList<Slot> slots = FXCollections.observableArrayList();
    private Dictionary dictionary;

    public Challenge(ArrayList<SlotType> slots, Dictionary dictionary) {
        for (int i = 0; i < Constants.CHALLENGE_SIZE; i++) {
            switch (slots.get(i)) {
                case L2SLOT:
                    this.slots.add(new L2());
                    break;
                case L3SLOT:
                    this.slots.add(new L3());
                    break;
                case SWAP:
                    this.slots.add(new Swap());
                    break;
                case SEVENTH:
                    this.slots.add(new SevenTh());
                    break;
                default:
                    this.slots.add(new Slot());
                    break;
            }
        }

        this.dictionary = dictionary;
    }

    public ObservableList<Slot> getSlots() {
        return slots;
    }

    public boolean checkWord() {
        String word = "";
        for (Slot s : slots) {
            if (s.getTile() != null) {
                char c = s.getTile().getValue();
                word += c;
            }
        }
        return dictionary.contains(word);
    }

    public int getScoreWord() {
        int score = 0;
        for (Slot s : slots) {
            score += s.getScore();
        }
        return score;
    }

    private void endTurn() {
        // Signal à side la fin du tour
        // Soit Side vient prendre les infos, soit on lui envoit
    }

    public boolean playTurn() {
        // Jouer le tour
        if (checkWord() == true) {
            endTurn();
            return true;
        } else {
            return false;
        }
    }

    public int getSizeChallenge() {
        return Constants.CHALLENGE_SIZE;
    }

    public boolean addTile(Tile tile) {
        for (Slot s : slots) {
            if (s.isEmpty()) {
                return s.addTile(tile);
            }
        }
        return false;
    }

    public boolean addTileToPos(Tile tile, int pos) {
        if (slots.get(pos).isEmpty()) {
            slots.get(pos).addTile(tile);
            return true;
        }
        return false;
    }

    public Tile getTileToPos(int pos) {
        if (!slots.get(pos).isEmpty()) {
            return slots.get(pos).removeTile();
        }
        return null;
    }

    public void moveTo(int fromPos, int toPos) {
        if (!slots.get(fromPos).isEmpty() && slots.get(toPos).isEmpty()) {
            slots.get(toPos).addTile(slots.get(fromPos).removeTile());
        }
    }
}
