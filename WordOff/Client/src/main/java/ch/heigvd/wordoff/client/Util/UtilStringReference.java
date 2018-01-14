package ch.heigvd.wordoff.client.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class of the content of differents elements of the game.
 */
public class UtilStringReference {

    /* String of error */
    public static final String ERROR_TOKEN= "Session expirée, veuillez vous reconnecter";
    public static final String ERROR = "Une erreur s'est produite";
    public static final String TOO_FEW_COINS = "Nombre de pièces insuffisant pour ce pouvoir. ";
    public static final String PLAYER_NOT_FOUND = "Ce joueur n'existe pas. ";

    /* String of informations : select langage */
    public static final String INFOS_SELECT_LANGUAGE = "Veuillez sélectionner la langue";

    /* String of game settings */
    public static final String TEXT_PARAM_SOUND = "Activer / Désactiver le son dans le jeu";
    public static final String TEXT_PARAM_HELP = "Activer / Désactiver l'aide du jeu";
    public static final String TEXT_PARAM_NOTIF = "Activer / Désactiver les notifications";

    /* String of choice of game langages  */
    public static final String LANG_FR = "Français";
    public static final String LANG_EN = "English";
    public static final List<String> TEXT_PARAM_LANG = new LinkedList<>(Arrays.asList(LANG_FR, LANG_EN));
}
