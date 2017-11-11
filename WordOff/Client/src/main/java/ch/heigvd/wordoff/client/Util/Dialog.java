package ch.heigvd.wordoff.client.Util;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.text.LabelView;

public class Dialog {

    /**
     * Constructeur privé
     */
    private Dialog() {
    }

    /**
     * Instance unique pré-initialisée
     */
    private static Dialog INSTANCE = new Dialog();

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static Dialog getInstance() {
        return INSTANCE;
    }

    public void signalError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(error);

        applyStyleSheet(alert);

        alert.showAndWait();
    }

    public void signalInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText(information);

        applyStyleSheet(alert);

        alert.showAndWait();
    }

    public boolean popUpYesNo(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Question");
        alert.setHeaderText("Veuillez confirmer");
        alert.setContentText(msg);
        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.OK_DONE);
        ButtonType yes = new ButtonType("Oui", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);

        applyStyleSheet(alert);

        alert.showAndWait();

        if (alert.getResult().getButtonData().getTypeCode() == yes.getButtonData().getTypeCode()) {
            return true;
        }
        return false;
    }

    private void applyStyleSheet(Alert alert) {
        // Set Icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toExternalForm()));
        //   stage.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        ObservableList<String> st = dialogPane.getStylesheets();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/Style_alert.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

    }

    public String choicesDialog(String msg, String typeChoice, String ... choices){
        final String[] choose = {""};
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Démarrer une nouvelle partie");
        alert.setContentText(msg);

        Label labLang = new Label();
        labLang.setText(typeChoice);

        ChoiceBox<String> lang = new ChoiceBox<>();
        for(String choice : choices){
            lang.getItems().add(choice);
        }

        FlowPane pane = new FlowPane();
        pane.getChildren().add(labLang);
        pane.getChildren().add(lang);

        alert.getDialogPane().setContent(pane);

        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.OK_DONE);
        ButtonType yes = new ButtonType("Oui", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);

        applyStyleSheet(alert);


        alert.showAndWait();

        if (alert.getResult().getButtonData().getTypeCode() == yes.getButtonData().getTypeCode()) {
            return lang.getSelectionModel().getSelectedItem().toString();
        }
        return null;
    }
}
