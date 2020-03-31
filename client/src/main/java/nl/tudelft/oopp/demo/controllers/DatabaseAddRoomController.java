package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseAddRooms.fxml" file
 */
public class DatabaseAddRoomController {

    @FXML
    private ChoiceBox<String> studySpecificChoiceBox;
    @FXML
    private ToggleButton screenToggle;
    @FXML
    private ToggleButton projectorToggle;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField facultyTextField;
    @FXML
    private TextField buildingIdTextField;
    @FXML
    private TextField capacityTextField;
    @FXML
    private TextField plugsTextField;

    private boolean screenToggleFlag;
    private boolean projectorToggleFlag;

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickProjector() {
        if (projectorToggleFlag) {
            projectorToggle.setText("False");
            projectorToggleFlag = false;
        } else {
            projectorToggle.setText("True");
            projectorToggleFlag = true;
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickScreen() {
        if (screenToggleFlag) {
            screenToggle.setText("False");
            screenToggleFlag = false;
        } else {
            screenToggle.setText("True");
            screenToggleFlag = true;
        }
    }

    /**
     * Adds a room to the database
     */
    public void databaseAddRoom() {
        String name = nameTextField.getText();
        int buildingId = Integer.parseInt(buildingIdTextField.getText());
        String studySpecific = studySpecificChoiceBox.getValue();
        boolean screen = Boolean.parseBoolean(screenToggle.getText());
        boolean projector = Boolean.parseBoolean(projectorToggle.getText());
        int capacity = Integer.parseInt(capacityTextField.getText());
        int plugs = Integer.parseInt(plugsTextField.getText());
        String response = ServerCommunication.addRoom(name, buildingId, studySpecific, screen, projector, capacity, plugs);
        if (response.equals("Successfully added!")) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void goToRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }
}
