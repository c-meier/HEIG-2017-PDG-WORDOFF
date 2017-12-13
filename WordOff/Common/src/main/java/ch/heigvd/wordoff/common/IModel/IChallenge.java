package ch.heigvd.wordoff.common.IModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface IChallenge {
    IRack getSwapRack();
    List<ISlot> getSlots();

    default boolean addTile(ITile tile) {
        for (ISlot s : getSlots()) {
            if (s.isEmpty()) {
                return s.addTile(tile);
            }
        }
        return false;
    }

    @JsonIgnore
    default String getWord() {
        StringBuilder word = new StringBuilder();

        for (ISlot s : getSlots()) {
            if (s.getTile() != null) {
                char c = s.getTile().getValue();
                word.append(c);
            }
        }

        return word.toString();
    }

    @JsonIgnore
    default int getScore() {
        int score = 0;
        for (ISlot s : getSlots()) {
            score += s.getScore();
        }

        // Check if swap bonus
        //      Rack vide = x2 sur le score
        //      Rack non vide = score - valeur de chaque tuile
        IRack rack = getSwapRack();
        if (rack.isEmpty()) {
            score *= 2;
        } else {
            for (ITile t : rack.getTiles()) {
                score -= t.getScore();
            }
        }

        return score;
    }

    default ITile getTileById(int id){
        for(ISlot s : getSlots()){
            if(!s.isEmpty()){
                if(s.getTile().getId() == id){
                    return s.removeTile();
                }
            }
        }
        return null;
    }

    IChallenge duplicate();
}
