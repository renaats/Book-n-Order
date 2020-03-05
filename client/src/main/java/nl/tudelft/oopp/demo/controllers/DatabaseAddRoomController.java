package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseAddRoomController {

    @FXML
    private ToggleButton facultySpecificToggle;
    @FXML
    private ToggleButton screenToggle;
    @FXML
    private ToggleButton projectorToggle;

    private boolean facultySpecificToggleFlag;
    private boolean screenToggleFlag;
    private boolean projectorToggleFlag;

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e
     */
    @FXML
    private void toggleClickFacultySpecific(ActionEvent e) {
        if (facultySpecificToggleFlag) {
            facultySpecificToggle.setText("False");
            facultySpecificToggleFlag = false;
        } else {
            facultySpecificToggle.setText("True");
            facultySpecificToggleFlag = true;
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e
     */
    @FXML
    private void toggleClickProjector(ActionEvent e) {
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
     * @param e
     */
    @FXML
    private void toggleClickScreen(ActionEvent e) {
        if (screenToggleFlag) {
            screenToggle.setText("False");
            screenToggleFlag = false;
        } else {
            screenToggle.setText("True");
            screenToggleFlag = true;
        }
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

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }
}
