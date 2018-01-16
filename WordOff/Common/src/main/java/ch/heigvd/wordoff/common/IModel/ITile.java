/*
 * File: ITile.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.IModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ITile {
    char getValue();
    void setValue(char c);
    int getId();
    void setId(int id);
    int getScore();
    void setScore(int score);

    ITile duplicate();

    @JsonIgnore
    default boolean isJoker() {
        return getScore() == 0;
    }
}
