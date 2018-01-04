package ch.heigvd.wordoff.client.Util;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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



    public String choicesBoxDialog(String title, String content, String typeChoice, String ... choices){
        final String[] choose = {""};
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(title);

        Label labelContent = new Label();
        labelContent.setText(content);

        Label labLang = new Label();
        labLang.setText(typeChoice);

        ChoiceBox<String> lang = new ChoiceBox<>();
        for(String choice : choices){
            lang.getItems().add(choice);
        }
        lang.getSelectionModel().selectFirst();

        BorderPane borderPane = new BorderPane();
        FlowPane paneCenter = new FlowPane();

        borderPane.setTop(labelContent);
        borderPane.setCenter(paneCenter);
        borderPane.getCenter().setStyle("-fx-padding: 20,20,20,20");
        paneCenter.getChildren().add(labLang);
        paneCenter.getChildren().add(lang);

        alert.getDialogPane().setContent(borderPane);

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

    /**
     * Dialog to retrieve the parameters of a new duel.
     * Returns the information in a list. Index 0 for the language and index 1 for the duel type
     * @return informations
     */
    public List<String> newGame(){
        List<String> state = new LinkedList<>();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        applyStyleSheet(alert);
        alert.setHeaderText("Démarrer une nouvelle partie");

        Label labelLang = new Label();
        Label labelType = new Label();
        labelLang.setText("Langue : ");
        labelType.setText("Type : ");

        ChoiceBox<String> lang = new ChoiceBox<>();
        for(String choice : UtilStringReference.TEXT_PARAM_LANG){
            lang.getItems().add(choice);
        }
        lang.getSelectionModel().selectFirst();

        ChoiceBox<String> type = new ChoiceBox<>();
        type.getItems().add("Adversaire aléatoire");
        type.getItems().add("Contre un ami");
        type.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(labelLang, 0,0);
        grid.add(lang,1,0);
        grid.add(labelType,0,1);
        grid.add(type,1,1);

        alert.getDialogPane().setContent(grid);

        alert.showAndWait();
        state.add(lang.getValue());
        state.add(type.getValue());

        if(alert.getResult().getButtonData().isCancelButton())
            return null;

        return state;
    }

    /**
     * Dialog to choice the opponent duel
     * @return opponent
     */
    public String choiceNameOpponent(){
        String opponent = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        applyStyleSheet(alert);
        alert.setHeaderText("Entrez le nom de votre adversaire");

        Label label = new Label();
        label.setText("Nom de l'adversaire: : ");

        TextField input = new TextField();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(label, 0,0);
        grid.add(input,1,0);
        alert.getDialogPane().setContent(grid);

        alert.showAndWait();

        if(!alert.getResult().getButtonData().isCancelButton() && !input.getText().isEmpty()) {
            opponent = input.getText();
            System.out.println(opponent);
        }

        return opponent;
    }


}
