package ch.heigvd.wordoff.common;

import ch.heigvd.wordoff.common.Dto.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
     * qu'ils marqueraient sur ce Side. Prend en compte le SwapRack. La clé du la paire est le score.
     * ATTENTION : LES SLOTS DU CHALLENGE SERONT VIDES APRES APPEL
     *
     * @return List<Pair<Integer (score), List<ITile> (mot)>>
     */
    public List<Pair<Integer, List<ITile>>> getWordsByScore() {
        List<Pair<Integer, List<ITile>>> pairList = new ArrayList<>();

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
            // challenge et racks temporaires
            IChallenge tempChall = new ChallengeDto(new ArrayList<ISlot>(challenge.getSlots()),
                    new SwapRackDto(new ArrayList<>(challenge.getSwapRack().getTiles())));
            tempChall.getSlots().forEach(ISlot::removeTile);
            IRack tempPlayerRack = new PlayerRackDto(new ArrayList<>());
            playerRack.getTiles().forEach(tempPlayerRack::addTile);
            SwapRackDto tempSwap = new SwapRackDto(new ArrayList<>());
            challenge.getSwapRack().getTiles().forEach(tempSwap::addTile);

            // pour chaque lettre
            for (int i = 0; i < str.length(); i++) {
                boolean tileFound = false;
                // cherche la tile dans le tempSwap en premier
                for (ITile tile : tempSwap.getTiles()) {
                    if ((tile.isJoker() && str.charAt(i) == '#') || (tile.getValue() == str.charAt(i) && !tile.isJoker())) {
                        tileFound = true;
                        tempChall.addTile(new TileDto(tile.getId(), tile.isJoker() ? str.charAt(++i) : tile.getValue(), tile.getScore()));
                        tempSwap.removeTile(tile.getId());
                        break;
                    }
                }

                if (!tileFound) {
                    // cherche la position du la Tile correspondante dans le playerRack
                    for (ITile tile : tempPlayerRack.getTiles()) {
                        if ((tile.isJoker() && str.charAt(i) == '#') || (tile.getValue() == str.charAt(i) && !tile.isJoker())) {
                            tempChall.addTile(new TileDto(tile.getId(), tile.isJoker() ? str.charAt(++i) : tile.getValue(), tile.getScore()));
                            tempPlayerRack.removeTile(tile.getId());
                            break;
                        }
                    }
                }
            }

            // ajoute le mot et son score à la liste, en tenant compte de l'état du swapRack temporaire
            List<ITile> tiles = new ArrayList<>();
            for (ISlot slot : tempChall.getSlots()) {
                if (slot != null && slot.getTile() != null) {
                    tiles.add(slot.getTile());
                }
            }
            pairList.add(new Pair<>(tempChall.getScore(), tiles));
            tempChall.getSlots().forEach(ISlot::removeTile); // retire les tiles des slots
        }

        System.out.println("-------");
        pairList.forEach((k) -> {
            k.getValue().forEach((j) -> System.out.print(j.getValue()));
            System.out.println();
        });

        pairList.sort(Comparator.comparingInt(Pair::getKey));
        return pairList;
    }
}
