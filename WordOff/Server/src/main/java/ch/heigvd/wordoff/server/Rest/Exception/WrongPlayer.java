package ch.heigvd.wordoff.server.Rest.Exception;

public class WrongPlayer extends RuntimeException {
    public WrongPlayer(String s) {
        super(s);
    }
}
