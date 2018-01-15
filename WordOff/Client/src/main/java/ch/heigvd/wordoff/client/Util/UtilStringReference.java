package ch.heigvd.wordoff.client.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UtilStringReference {

    public static final String ERROR_TOKEN= new String("Une erreur s'est produire, veuilleu vous reconnecter");
    public static final String ERROR = new String("Une erreur s'est produite");
    public static final String TOO_FEW_COINS = new String("Nombre de pièces insuffisant pour ce pouvoir. ");
    public static final String PLAYER_NOT_FOUND = new String("Ce joueur n'existe pas. ");

    public static final String INFOS_SELECT_LANGUAGE = new String("Veuillez sélectionner la langue");

    public static final String TEXT_PARAM_SOUND = new String("Activer / Désactiver le son dans le jeu");
    public static final String TEXT_PARAM_HELP = new String("Activer / Désactiver l'aide du jeu");
    public static final String TEXT_PARAM_NOTIF = new String("Activer / Désactiver les notifications");

    /* Langage */
    public static final String LANG_FR = "Français";
    public static final String LANG_EN = "English";
    public static final List<String> TEXT_PARAM_LANG = new LinkedList<>(Arrays.asList(LANG_FR, LANG_EN));
}
