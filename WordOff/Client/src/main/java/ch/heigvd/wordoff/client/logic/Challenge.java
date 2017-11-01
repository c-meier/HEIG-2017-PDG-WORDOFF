/*package ch.heigvd.wordoff.client.logic;

import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

import ch.heigvd.wordoff.common.Dictionary;

public class Challenge {
    private Side side;
    private List<Slot> slots;
    private final int sizeChallenge = 7;
    Dictionary dictionary;

    // Identifiant des cases
    private final int basicSlot = 1;
    private final int l2Slot = 2;
    private final int l3Slot = 3;
    private final int swapSlot = 4;
    private final int sevenThSlot = 5;

    public Challenge(List<Integer> slots, Dictionary dictionary) {
        initChallenge(slots);
        this.dictionary = dictionary;
    }
    public Challenge(List<Integer> slots) {
        initChallenge(slots);
    }


    public void initChallenge(List<Integer> slots) {
        this.slots = new ArrayList<>();
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

    public List<Slot> getSlots() {
        return slots;
    }

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

    public boolean addTile(Tile tile){
        for(Slot s : slots){
            if(s.isEmpty()){
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
*/