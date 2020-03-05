package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

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

    /**
     * Switches scene to DatabaseAddBuildings.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml", null);
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml", "/DatabaseAddRooms.css");
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml", null);
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml", null);
    }
}
