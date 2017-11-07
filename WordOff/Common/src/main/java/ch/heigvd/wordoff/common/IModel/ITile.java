package ch.heigvd.wordoff.common.IModel;

public interface ITile {
    char getValue();
    int getId();
    int getScore();
    void setValue(char c);

    default boolean isJocker() {
        return getScore() == 0;
    }

}
