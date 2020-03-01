package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField buildingFindID;
    @FXML
    private TextField buildingDeleteID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();
    }

    /**
     * Handles clicking the building find button.
     */
    public void building_id_ButtonClicked() {
        int id = Integer.parseInt(buildingFindID.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building Finder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.findBuilding(id));
        alert.showAndWait();
    }

    /**
     * Handles clicking the quote button.
     */
    public void quoteButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quote for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getQuote());
        alert.showAndWait();
    }

    /**
     * Handles clicking the user button.
     */
    public void userButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getUser());
        alert.showAndWait();
    }

    /**
     * Handles clicking the add button.
     */
    public void addBuildingButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A new building has been added!");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.postBuilding());
        alert.showAndWait();
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getBuilding());
        alert.showAndWait();
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        loadDataUpdateChoiceBox();
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     * Handles clicking the remove button.
     */
    public void deleteBuildingButtonClicked() {
        int id = Integer.parseInt(buildingDeleteID.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building remover");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBuilding(id));
        alert.showAndWait();
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.removeAll();
        String a = "Name";
        String b = "Email";
        updateChoiceBoxList.addAll(a, b);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

}
