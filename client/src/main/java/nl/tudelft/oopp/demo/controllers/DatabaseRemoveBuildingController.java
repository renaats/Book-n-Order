package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseRemoveBuildingController {
    @FXML
    private TextField buildingDeleteByIdTextField;

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
     * Handles clicking the remove button.
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
     * return to the database building menu when the building icon on the menu bar is clicked
     * @param mouseEvent the click on the home icon on the databased screens
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void goToBuildingMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
