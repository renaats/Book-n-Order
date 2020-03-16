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
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to DatabaseEditBuildings.fxml file
 */
public class DatabaseEditBuildingController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField buildingFindByIdTextField;

    @FXML
    private TextField buildingFindByIdUpdateField;
    @FXML
    private TextField buildingChangeToField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();
    }

    /**
     * Handles clicking the building find button.
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
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getBuildings());
        alert.showAndWait();
    }

    /**
     * Handles the sending of update values.
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
        updateChoiceBoxList.removeAll();
        String a = "Name";
        String b = "Street";
        String c = "House Number";
        updateChoiceBoxList.addAll(a, b, c);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

    /**
     * return to the database main menu when the home icon is clicked
     * @param mouseEvent the click on the home icon on the databased screens
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToAddBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }
    /**
     * sends the user to the remove building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void goToRemoveBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveBuildings.fxml");
    }

    /**
     * sends the user to the edit building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * return to the database building menu when the building icon on the menu bar is clicked
     * @param mouseEvent the click on the home icon on the databased screens
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
