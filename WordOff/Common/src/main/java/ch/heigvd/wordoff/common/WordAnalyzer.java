package ch.heigvd.wordoff.common;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Project : WordOff
 * Date : 24.10.17
 */
public class WordAnalyzer {
    private final Dictionary DICTIONARY;
    private IChallenge challenge;
    private IRack playerRack;

    public WordAnalyzer(Dictionary dico, IChallenge challenge, IRack playerRack) {
        DICTIONARY = dico;
        this.challenge = challenge;
        this.playerRack = playerRack;
    }

    /*
     * Calcule les scores des mots possibles et renvoie les mots dans l'ordre croissant du score
     * qu'ils marqueraient sur ce Side. Prend en compte le SwapRack. La clé du la Map est le score.
     * @return TreeMap<score (Integer), mot (String)>
     */
    public TreeMap<Integer, List<ITile>> getWordsByScore() {
        TreeMap<Integer, List<ITile>> map = new TreeMap<>();

        // construit la String des lettres disponibles
        StringBuilder lettersBuilder = new StringBuilder();
        for (ITile tile : playerRack.getTiles()) {
            lettersBuilder.append(tile.getValue());
        }
        for (ITile tile : challenge.getSwapRack().getTiles()) {
            lettersBuilder.append(tile.getValue());
        }
        String letters = lettersBuilder.toString();

        // récupère les mots possibles
        List<String> anagrams = DICTIONARY.getAnagrams(letters);

        for (String str : anagrams) {
            List<ITile> tiles = new ArrayList<>();
            // challenge et racks temporaires
            ChallengeDto tempChall = new ChallengeDto(challenge.getSlots(), challenge.getSwapRack());
            IRack tempPlayer = new PlayerRackDto(new ArrayList<>());
            playerRack.getTiles().forEach(tempPlayer::addTile);
            SwapRackDto tempSwap = new SwapRackDto(new ArrayList<>());
            challenge.getSwapRack().getTiles().forEach(tempSwap::addTile);

            // pour chaque lettre
            for (int i = 0; i < str.length(); i++) {
                boolean tileFound = false;
                // cherche la tile dans le tempSwap en premier
                for (ITile tile : tempSwap.getTiles()) {
                    if (tile.getValue() == str.charAt(i)) {
                        // retire la tuile, et l'ajoute au challenge
                        tempChall.addTile(tempSwap.getTile(tile.getId()));
                        tiles.add(tempSwap.getTile(tile.getId()));
                        tileFound = true;
                        if(tile.isJoker()) {
                            tile.setValue(str.charAt(++i));
                        }
                        break;
                    }
                }

                if (!tileFound) {
                    // cherche la position du la Tile correspondante dans le playerRack
                    for (ITile tile : tempPlayer.getTiles()) {
                        if (tile.getValue() == str.charAt(i)) {
                            // ajoute la tile au challenge
                            tempChall.addTile(tempPlayer.getTile(tile.getId()));
                            tiles.add(tempPlayer.getTile(tile.getId()));
                            if(tile.isJoker()) {
                                tile.setValue(str.charAt(++i));
                            }
                            break;
                        }
                    }
                }
            }

            // ajoute le mot et son score à la map, en tenant compte de l'état du swapRack temporaire
            map.put(tempChall.getScore(), tiles);
        }

        return map;
    }
}
