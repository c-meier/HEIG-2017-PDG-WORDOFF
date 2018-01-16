/*
 * File: UtilStringReference.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the messages used by the GUI.
 */
public class UtilStringReference {

    public static final String ERROR_TOKEN= "Session expirée, veuillez vous reconnecter";
    public static final String ERROR = "Une erreur s'est produite";
    public static final String TOO_FEW_COINS = "Nombre de pièces insuffisant pour ce pouvoir. ";
    public static final String PLAYER_NOT_FOUND = "Ce joueur n'existe pas. ";

    public static final String INFOS_SELECT_LANGUAGE = "Veuillez sélectionner la langue";

    public static final String TEXT_PARAM_SOUND = "Activer / Désactiver le son dans le jeu";
    public static final String TEXT_PARAM_HELP = "Activer / Désactiver l'aide du jeu";
    public static final String TEXT_PARAM_NOTIF = "Activer / Désactiver les notifications";

    /* Langage */
    public static final String LANG_FR = "Français";
    public static final String LANG_EN = "English";
    public static final List<String> TEXT_PARAM_LANG = new LinkedList<>(Arrays.asList(LANG_FR, LANG_EN));
}
