package ch.heigvd.wordoff.Model;

import ch.heigvd.wordoff.common.Constants;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class Game {
    private List<Side> sideList;
    private Bag bag;
    private boolean withAi;
    private String lang;

    public Game(Player player1, Player player2, boolean withAi, String lang) {
        if (withAi) {
            /* init la game avec une ai en tant que 2ème joueur */
        } else {
            /* init la game avec 2 player */
        }
    }

    public void aiPlay() {

    }

    public String chooseWord(Player player) {
        // Donini is crying
        if (player instanceof Ai) {
            String word = "";
            TreeMap<Integer, String> wordsByScore = sideList.get(2).getWordsByScore();

            int sizeWordsByScore = wordsByScore.size();
            Random random = new Random();
            int index;
            int lowerBound = sizeWordsByScore / 3;
            int middleUpperBound = (lowerBound * 2) + 1;

            if (sizeWordsByScore != 0) {
                switch (((Ai) player).getLevel()) {
                    case Constants.EASY:
                        index = random.nextInt(lowerBound);
                        word = wordsByScore.values().toArray()[index].toString();
                        break;
                    case Constants.AVERAGE:
                        index = random.nextInt(middleUpperBound) + lowerBound;
                        word = wordsByScore.values().toArray()[index].toString();
                        break;
                    case Constants.HARD:
                        index = random.nextInt(sizeWordsByScore - 1) + middleUpperBound;
                        word = wordsByScore.values().toArray()[index].toString();
                        break;
                    case Constants.YOU_RE_SCREWED:
                        word = wordsByScore.lastEntry().getValue();
                        break;
                    default:
                        /* Message d'erreur */
                        break;
                }
            } else {
                /* TO DO MESSAGE A FAIRE */
                return "PASS";
            }

            return word;
        } else {
            /* TO DO Message d'erreur */
            return "You're not an AI";
        }
    }
}
