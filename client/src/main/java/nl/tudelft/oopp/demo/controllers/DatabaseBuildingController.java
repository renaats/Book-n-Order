package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to DatabaseEdditBuildings.fxml file
 */
public class DatabaseBuildingController implements Initializable {

    final ObservableList<String> updateChoiceBoxList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField buildingFindByIdTextField;
    @FXML
    private TextField buildingDeleteByIdTextField;
    @FXML
    private TextField buildingFindByIdUpdateField;
    @FXML
    private TextField buildingChangeToField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();
    }

    /**
     * Handles clicking of the Find Building button.
     */
    public void building_id_ButtonClicked() {
        try {
            int id = Integer.parseInt(buildingFindByIdTextField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building Finder");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.findBuilding(id));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking of the List All Buildings button.
     */
    public void listBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getBuildings());
        alert.showAndWait();
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEdditBuildings.fxml");
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Handles clicking of the Remove Building button.
     */
    public void deleteBuildingButtonClicked() {
        try {
            int id = Integer.parseInt(buildingDeleteByIdTextField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building remover");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.deleteBuilding(id));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the sending of updated values.
     */
    public void updateBuildingButtonClicked() {
        try {
            int id = Integer.parseInt(buildingFindByIdUpdateField.getText());
            String attribute = updateChoiceBox.getValue().replaceAll(" ", "");
            String changeValue = buildingChangeToField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building remover");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateBuilding(id, attribute, changeValue));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.clear();
        String a = "Name";
        String b = "Street";
        String c = "House Number";
        updateChoiceBoxList.addAll(a, b, c);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

    /**
     * Switches scene to DatabaseAddBuildings.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    public void myAccountScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }
    /**
     * returns to the main menu
     * @param actionEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}
