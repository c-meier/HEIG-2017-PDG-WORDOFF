package ch.heigvd.wordoff.client.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UtilStringReference {

    public static String ERROR_TOKEN= new String("Une erreur s'est produire, veuilleu vous reconnecter");
    public static String ERROR = new String("Une erreur s'est produite");

    public static String INFOS_SELECT_LANGUAGE = new String("Veuillez sélectionner la langue");

    public static String TEXT_PARAM_SOUND = new String("Activer / Désactiver le son dans le jeu");
    public static String TEXT_PARAM_HELP = new String("Activer / Désactiver l'aide du jeu");
    public static String TEXT_PARAM_NOTIF = new String("Activer / Désactiver les notifications");
    public static List<String> TEXT_PARAM_LANG = new LinkedList<>(Arrays.asList("Français","English"));

}
