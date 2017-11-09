package ch.heigvd.wordoff.client.rest;

import ch.heigvd.wordoff.client.Logic.Game;

public class RestAcces {
    /**
     * Constructeur privé
     */
    private RestAcces() {
    }

    /**
     * Instance unique pré-initialisée
     */
    private static RestAcces INSTANCE = new RestAcces();

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static RestAcces getInstance() {
        return INSTANCE;
    }

    public static void GetListMode() {
        // TODO
    }

    public static void GetListGame() {
        // TODO
    }

    public static Game GetFullGame(int id) {
        // TODO
        return new Game();
    }

    public static void GetGame() {
        // TODO
    }

    public static void PostNewGame() {
        // TODO
    }

    public static void getHint() {
        // TODO
    }

    public static void PostPlay() {
        // TODO
    }

    public static void PostDiscard() {
        // TODO
    }

    public static void GetPeek() {
        // TODO
    }

    public static void DeleteGame() {
        // TODO
    }
}
