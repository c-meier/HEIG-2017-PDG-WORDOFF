package ch.heigvd.wordoff.client.Util;

import ch.heigvd.wordoff.client.Controller.FriendlyTournamentSettingsController;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.common.Dto.Game.PowerDto;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to present user with dialog popups and error messages
 */
public class Dialog {

    private Dialog() {
    }

    /**
     * Singleton instance of the class
     */
    private static Dialog INSTANCE = new Dialog();

    /**
     * Retrieves class instance
     */
    public static Dialog getInstance() {
        return INSTANCE;
    }

    /**
     * Apply the StyleSheet to dialog window
     */
    private void applyStyleSheet(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toExternalForm()));

        DialogPane dialogPane = alert.getDialogPane();
        ObservableList<String> st = dialogPane.getStylesheets();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/Style_alert.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

    }

    /**
     * Presents an error popup with parameter as message
     * @param error an error String
     */
    public void signalError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(error);

        applyStyleSheet(alert);

        alert.showAndWait();
    }

    /**
     * Presents an information popup with parameter as information
     * @param information an information String
     */
    public void signalInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText(information);

        applyStyleSheet(alert);

        alert.showAndWait();
    }

    /**
     * Allows a selection of yes or no based on parameter string
     * @param msg a question String
     * @return true if accepted, false otherwise
     */
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


    /**
     * Dialog to type choice with more informations, title, content, typeChoice and choices
     * @param title title of demand
     * @param content content text
     * @param typeChoice type of choice
     * @param choices choices
     * @return return choice of user
     */
    public String choicesBoxDialog(String title, String content, String typeChoice, String ... choices){
        final String[] choose = {""};
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(title);

        Label labelContent = new Label();
        labelContent.setText(content);

        Label labelTypeChoice = new Label();
        labelTypeChoice.setText(typeChoice);

        ChoiceBox<String> choicesBox = new ChoiceBox<>();
        for(String choice : choices){
            choicesBox.getItems().add(choice);
        }
        choicesBox.getSelectionModel().selectFirst();

        BorderPane borderPane = new BorderPane();
        FlowPane paneCenter = new FlowPane();

        borderPane.setTop(labelContent);
        borderPane.setCenter(paneCenter);
        borderPane.getCenter().setStyle("-fx-padding: 20,20,20,20");
        paneCenter.getChildren().add(labelTypeChoice);
        paneCenter.getChildren().add(choicesBox);

        alert.getDialogPane().setContent(borderPane);

        ButtonType no = new ButtonType("Annuler", ButtonBar.ButtonData.OK_DONE);
        ButtonType yes = new ButtonType("Confirmer", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);

        applyStyleSheet(alert);

        alert.showAndWait();

        if (alert.getResult().getButtonData().getTypeCode() == yes.getButtonData().getTypeCode()) {
            return choicesBox.getSelectionModel().getSelectedItem().toString();
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
    public String choiceNameOpponent(String header){
        String opponent = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        applyStyleSheet(alert);
        alert.setHeaderText(header);

        Label label = new Label();
        label.setText("Nom : ");

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


    /**
     * Dialog to select participants to a friendly tournament
     * @return list of participant
     */
    public List<String> getFriendlyTournamentParticipants() {
        final Stage popUp = new Stage();
        popUp.initOwner(MainApp.getStage());
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/friendlyTournamentSettings.fxml"));
        BorderPane c;
        FriendlyTournamentSettingsController controller = new FriendlyTournamentSettingsController();
        try {
            loader.setController(controller);
            c = loader.load();
            Scene testScene = new Scene(c);
            popUp.setScene(testScene);

            popUp.setTitle("Tournoi amical");
            popUp.sizeToScene();
            popUp.setResizable(false);

            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.showAndWait();
            return controller.getParticipants();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;


    }

    /**
     * Signals a user does not have enough coins to use a power
     * @param power a PowerDto
     */
    public void signalPowerError(PowerDto power) {
        signalError(UtilStringReference.TOO_FEW_COINS + "Il vous en faut " + power.getCost() + ".");
    }

    /**
     * Asks user to confirms they would like to use parameter power
     * @param power a PowerDto
     * @return true if user wants to use power, false otherwise
     */
    public boolean powerConfirm(PowerDto power) {
        return popUpYesNo("Ce pouvoir coûte " + power.getCost() + ". Êtes-vous sûrs de vouloir l'utiliser?");
    }
}
