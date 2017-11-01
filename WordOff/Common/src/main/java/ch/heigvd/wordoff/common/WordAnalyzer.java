package ch.heigvd.wordoff.common;

import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Project : WordOff
 * Date : 24.10.17
 */
public class WordAnalyzer {
    private final Dictionary DICTIONARY;
    private Side side;

    public WordAnalyzer(Dictionary dico, Side side) {
        DICTIONARY = dico;
        this.side = side;
    }

    /*
     * Calcule les scores des mots possibles et renvoie les mots dans l'ordre croissant du score
     * qu'ils marqueraient sur ce Side. Prend en compte le SwapRack. La clé du la Map est le score.
     * @return TreeMap<score (Integer), mot (String)>
     */
    public TreeMap<Integer, List<Tile>> getWordsByScore() {
        TreeMap<Integer, List<Tile>> map = new TreeMap<>();

        // construit la String des lettre disponibles
        String letters = "";
        for (Tile tile : side.getPlayerRack().getRack()) {
            letters += tile.getValue();
        }
        for (Tile tile : side.getChallenge().getSwapRack().getRack()) {
            letters += tile.getValue();
        }

        // récupère les mots possibles
        List<String> anagrams = DICTIONARY.getAnagrams(letters);

        for (String str : anagrams) {
            List<Tile> tiles = new ArrayList<>();
            // challenge et racks temporaires
            Challenge tempChall = new Challenge(side.getChallenge().getSlots());
            PlayerRack tempPlayer = new PlayerRack();
            side.getPlayerRack().getRack().forEach(tempPlayer::addTile);
            SwapRack tempSwap = new SwapRack();
            side.getChallenge().getSwapRack().getRack().forEach(tempSwap::addTile);

            // pour chaque lettre
            for (int i = 0; i < str.length(); i++) {
                boolean tileFound = false;
                // cherche la tile dans le tempSwap en premier
                for (Tile tile : tempSwap.getRack()) {
                    if (tile.getValue() == str.charAt(i)) {
                        // retire la tile, et l'ajoute au challenge
                        tempChall.addTile(tempSwap.getTile(tile.getId()));
                        tiles.add(tempSwap.getTile(tile.getId()));
                        tileFound = true;
                        break;
                    }
                }

                if (!tileFound) {
                    // cherche la position du la Tile correspondante dans le playerRack
                    for (Tile tile : tempPlayer.getRack()) {
                        if (tile.getValue() == str.charAt(i)) {
                            // ajoute la tile au challenge
                            tempChall.addTile(tempPlayer.getTile(tile.getId()));
                            tiles.add(tempPlayer.getTile(tile.getId()));
                            break;
                        }
                    }
                }
            }

            // ajoute le mot et son score à la map, en tenant compte de l'état du swapRack temporaire
            map.put(tempChall.getScoreWord(), tiles);
        }

        return map;
    }
}
