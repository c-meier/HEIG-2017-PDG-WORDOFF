package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Entity
public class Side {
    @Id
    @GeneratedValue
    private Long id;

//    private final Dictionary DICTIONARY;

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @Embedded
    private Challenge challenge;

    @Embedded
    private SwapRack swapRack;

    @Embedded
    private PlayerRack playerRack;

    @OneToMany(mappedBy = "side", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Side() {
        this.swapRack = new SwapRack();
        this.playerRack = new PlayerRack();
        this.answers = new ArrayList<>();
        this.challenge = new Challenge();
    }

    public Side(Player player) {
        this();
        this.player = player;
    }


//    private BooleanProperty playerTurn;
//    private BooleanProperty walsActive;
//    private int score;
//    // How many time the player has pass
//    private int nbPass;
//
//    public Side(Player player, ArrayList<SlotType> slots, Dictionary dico) {
//        this.DICTIONARY = dico;
//        swapRack = new SwapRack();
//        playerRack = new PlayerRack();
//        this.player = player;
//        score = 0;
//        updateChallenge(slots);
//        nbPass = 0;
//    }
//
//    *
//     * @return true if the word is valid, else false
//     * @brief check if the word exist
//
//    public boolean checkWord() {
//        return challenge.checkWord();
//    }
//
//    *
//     * @brief Calculate the score for this side
//
//    public int getScoreWord() {
//        return swapRack.applyBonus(challenge.getScoreWord());
//    }
//
//    *
//     * @param newTiles  The list of tiles to add to the player rack
//     * @param swapTiles The list of tiles to add to the swap rack if this side
//     * @brief Update the side rack with
//
//    public void fillRacks(List<Tile> newTiles, List<Tile> swapTiles) {
//        for (int i = 0; i < newTiles.size(); i++) {
//            playerRack.addTile(newTiles.get(i));
//        }
//
//        for (int i = 0; i < swapTiles.size(); i++) {
//            swapRack.addTile(swapTiles.get(i));
//        }
//    }
//
//    public void updateChallenge(ArrayList<SlotType> slots) {
//        this.slots = slots;
//        challenge = new Challenge(slots, DICTIONARY);
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    *
//     * Calcule les scores des mots possibles et renvoie les mots dans l'ordre croissant du score
//     * qu'ils marqueraient sur ce Side. Prend en compte le SwapRack. La clé du la Map est le score.
//     * @return TreeMap<score (Integer), mot (String)>
//
//    public TreeMap<Integer, String> getWordsByScore() {
//        TreeMap<Integer, String> map = new TreeMap<>();
//
//        // construit la String des lettre disponibles
//        String letters = "";
//        for (Tile tile : playerRack.getRack()) {
//            letters += tile.getValue();
//        }
//        for (Tile tile : swapRack.getRack()) {
//            letters += tile.getValue();
//        }
//
//        // récupère les mots possibles
//        List<String> anagrams = DICTIONARY.getAnagrams(letters);
//
//        for (String str : anagrams) {
//            // challenge et racks temporaires
//            Challenge tempChall = new Challenge(slots, DICTIONARY);
//            PlayerRack tempPlayer = new PlayerRack();
//            playerRack.getRack().forEach(tempPlayer::addTile);
//            SwapRack tempSwap = new SwapRack();
//            swapRack.getRack().forEach(tempSwap::addTile);
//
//            // pour chaque lettre
//            for (int i = 0; i < str.length(); i++) {
//                boolean tileFound = false;
//                // cherche la tile dans le tempSwap en premier
//                for (Tile tile : tempSwap.getRack()) {
//                    if (tile.getValue() == str.charAt(i)) {
//                        // retire la tile, et l'ajoute au challenge
//                        tempChall.addTile(tempSwap.getTile(tile.getId()));
//                        tileFound = true;
//                        break;
//                    }
//                }
//
//                if (!tileFound) {
//                    // cherche la position du la Tile correspondante dans le playerRack
//                    for (Tile tile : tempPlayer.getRack()) {
//                        if (tile.getValue() == str.charAt(i)) {
//                            // ajoute la tile au challenge
//                            tempChall.addTile(tempPlayer.getTile(tile.getId()));
//                            break;
//                        }
//                    }
//                }
//            }
//
//            // ajoute le mot et son score à la map, en tenant compte de l'état du swapRack temporaire
//            map.put(tempSwap.applyBonus(tempChall.getScoreWord()), str);
//        }
//
//        return map;
//    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRack swapRack) {
        this.swapRack = swapRack;
    }

    public PlayerRack getPlayerRack() {
        return playerRack;
    }

    public void setPlayerRack(PlayerRack playerRack) {
        this.playerRack = playerRack;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
