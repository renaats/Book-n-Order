package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
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
     * sends the user to the edit building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * Adds building to the database
     */
    public void databaseAddBuilding() {
        String name = null;
        String street = null;
        // -1 is a placeholder since you cannot initialize an empty integer.
        int houseNumber = -1;
        try {
            name = nameTextField.getText();
            street = streetTextField.getText();
            if (name.equals("") || street.equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Missing attributes.");
                alert.initStyle(StageStyle.UNDECORATED);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
                alert.showAndWait();
                return;
            }
            try {
                houseNumber = Integer.parseInt(houseNumberTextField.getText());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Not an integer.");
                alert.initStyle(StageStyle.UNDECORATED);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/alertError.css").toExternalForm());
                alert.showAndWait();
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Could not parse attributes.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertError.css").toExternalForm());
            alert.showAndWait();
        }
        String response = ServerCommunication.addBuilding(name, street, houseNumber);
        if (response.equals("Successfully added!")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertInformation.css").toExternalForm());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertError.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Goes back to the database building menu when the icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
