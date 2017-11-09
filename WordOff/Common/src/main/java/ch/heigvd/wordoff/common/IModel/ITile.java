package ch.heigvd.wordoff.common.IModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ITile {
    char getValue();
    int getId();
    int getScore();
    void setValue(char c);

    @JsonIgnore
    default boolean isJoker() {
        return getScore() == 0;
    }

}
