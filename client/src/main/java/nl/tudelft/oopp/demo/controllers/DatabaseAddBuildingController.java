package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Takes care of the functionality of the DataBaseAddBuilding.fxml file
 */
public class DatabaseAddBuildingController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;

    /**
     * return to the database main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void goToAddBuildings() throws IOException {
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
     * Adds building to the database
     * @param actionEvent actionEvent parameter.
     */
    public void databaseAddBuilding(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String street = streetTextField.getText();
        int houseNumber = Integer.parseInt(houseNumberTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building adder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.addBuilding(name, street, houseNumber));
        alert.showAndWait();
    }

    /**
     * Goes back to the database building menu when the icon is clicked
     * @paramthe click on the building icon on the databased screens
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
