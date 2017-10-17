package ch.heigvd.wordoff.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import ch.heigvd.wordoff.Dictionary;

public class Challenge {
    private Side side;
    private ObservableList<Slot> slots = FXCollections.observableArrayList();
    private ArrayList<Slot> swapSlots;
    private final int sizeChallenge = 7;
    Dictionary dictionary;
    // Identifiant des cases
    private final int basicSlot = 1;
    private final int l2Slot = 2;
    private final int l3Slot = 3;
    private final int swapSlot = 4;
    private final int sevenThSlot = 5;


    public Challenge(Side side, ArrayList<Integer> slots, Dictionary dictionary) {
        this.side = side;
        initChallenge(slots);
        this.dictionary = dictionary;
    }

    public void initChallenge(ArrayList<Integer> slots) {
        for (int i = 0; i < sizeChallenge; i++)
            switch (slots.get(i)) {
                case basicSlot:
                    this.slots.add(new Slot());
                    break;
                case l2Slot:
                    this.slots.add(new L2());
                    break;
                case l3Slot:
                    this.slots.add(new L3());
                    break;
                case swapSlot:
                    this.slots.add(new Swap());
                    break;
                case sevenThSlot:
                    this.slots.add(new SevenTh());
                    break;
            }
    }

    public ObservableList<Slot> getSlots() {
        return slots;
    }

    /**
     * Vérifie si le mot existe auprès du dictionnaire
     */
    public boolean checkWord() {
        String word = "";
        for (Slot s : slots) {
            if (null != s.getTile()) {
                char c = s.getTile().getValue();
                word += c;
            }
        }
        return dictionary.contains(word);
    }

    /**
     * Calcul le score du mot du challenge en fonctio des cases
     *
     * @return
     */
    public int getScoreWord() {
        int score = 0;
        for (Slot s : slots) {
            score += s.getScore();
        }
        return score;
    }

    private void endTurn() {
        // Signal à side la fin du utour
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
        return sizeChallenge;
    }
}
