package ch.heigvd.wordoff.common;

import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Project : WordOff
 * Date : 24.10.17
 */
public class WordAnalyzer {

    /**
     * @param dico       Dictionnaire à utiliser
     * @param challIn    Challenge dont les Slots sont vides
     * @param plRackIn   PlayerRack (plein)
     * @param beginsWith Premières lettres posées
     * @return liste des Tiles à remettre surle Challenge
     */
    public static List<ITile> getHint(Dictionary dico, IChallenge challIn, IRack plRackIn, List<ITile> beginsWith) {
        IChallenge challenge = challIn.duplicate();
        List<Pair<Integer, List<ITile>>> bestWords = getWordsByScore(dico, challIn, plRackIn);
        if(bestWords.isEmpty()) {
            return new ArrayList<>();
        }
        List<ITile> bestWord = bestWords.get(bestWords.size() - 1).getValue();
        List<ITile> result = new ArrayList<>(beginsWith);

        if (bestWords.isEmpty()) {
            // S'il n'y a pas de solution, on renvoie une liste vide.
            result.clear();
            return result;
        }

        if (bestWord.size() < result.size()) {
            // Si le mot entré est plus grand que la solution optimale, on vide le mot
            result.clear();
        }

        // Vide le mot entré si une lettre ne correspond pas
        for (int i = 0; i < result.size(); ++i) {
            if (result.get(i).getValue() != bestWord.get(i).getValue()) {
                result.clear();
                break;
            }
        }

        // Si toutes les lettres correspondaient au début du mot optimal et le mot optimal n'est pas déjà en input,
        // on ajoute la lettre suivante
        if (bestWord.size() != result.size()) {
            result.add(bestWord.get(result.size()));
        }

        return result;
    }

    /**
     * Calcule les scores des mots possibles et renvoie les mots dans l'ordre croissant du score
     * qu'ils marqueraient sur ce Side. Prend en compte le SwapRack. La clé du la paire est le score.
     * L'array renvoyé est vide si aucun mot n'est possible.
     *
     * @param dico     Dictionnaire à utiliser
     * @param challIn  Challenge dont les slots sont vides
     * @param plRackIn PlayerRack (plein)
     * @return List<Pair
                                        * <
                                                                                 *   Integer
                       *       (
                                                                  *       score
                       *       )
                                                                  *       ,
                       *       List
                                                                     *       <
                       *       ITile> (mot)>>
     */
    public static List<Pair<Integer, List<ITile>>> getWordsByScore(Dictionary dico, IChallenge challIn,
                                                                   IRack plRackIn) {
        IChallenge challenge = challIn.duplicate();
        List<Pair<Integer, List<ITile>>> pairList = new ArrayList<>();

        // construit la String des lettres disponibles
        StringBuilder lettersBuilder = new StringBuilder();
        for (ITile tile : plRackIn.getTiles()) {
            lettersBuilder.append(tile.getValue());
        }
        for (ITile tile : challenge.getSwapRack().getTiles()) {
            lettersBuilder.append(tile.getValue());
        }
        String letters = lettersBuilder.toString();

        // récupère les mots possibles
        List<String> anagrams = dico.getAnagrams(letters);

        for (String str : anagrams) {
            // challenge et racks temporaires
            IChallenge tempChall = new ChallengeDto(new ArrayList<>(challenge.getSlots()),
                    new SwapRackDto(new ArrayList<>(challenge.getSwapRack().getTiles())));
            tempChall.getSlots().forEach(ISlot::removeTile);
            IRack playerRack = new PlayerRackDto(new ArrayList<>());
            plRackIn.getTiles().forEach((el) -> playerRack.addTile(el.duplicate()));
            SwapRackDto swapRack = new SwapRackDto(new ArrayList<>());
            challenge.getSwapRack().getTiles().forEach(swapRack::addTile);

            // pour chaque lettre
            for (int i = 0; i < str.length(); i++) {
                boolean tileFound = false;
                // cherche la tile dans le tempSwap en premier
                for (ITile tile : swapRack.getTiles()) {
                    if ((tile.isJoker() && str.charAt(i) == '#') || (tile.getValue() == str.charAt(i) && !tile
                            .isJoker())) {
                        tileFound = true;
                        ITile dup = tile.duplicate();
                        if (tile.isJoker()) {
                            dup.setValue(str.charAt(++i));
                        }
                        tempChall.addTile(dup);
                        swapRack.removeTile(tile.getId());
                        break;
                    }
                }

                if (!tileFound) {
                    // cherche la position du la Tile correspondante dans le playerRack
                    for (ITile tile : playerRack.getTiles()) {
                        if ((tile.isJoker() && str.charAt(i) == '#') || (tile.getValue() == str.charAt(i) && !tile
                                .isJoker())) {
                            ITile dup = tile.duplicate();
                            if (tile.isJoker()) {
                                dup.setValue(str.charAt(++i));
                            }
                            tempChall.addTile(dup);
                            playerRack.removeTile(tile.getId());
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

        pairList.sort(Comparator.comparingInt(Pair::getKey));
        return pairList;
    }
}
