package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "RoomEditOrAdd.fxml" file
 */
public class RoomEditOrAddController {

    /**
     * Changes the scene to the main menu of the database.
     * @throws IOException the input is always correct, so it should never throw and IOException.
     */

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * takes the user to the view where they can add rooms to the database
     * @throws IOException the input is always correct, so it should never throw and IOException.
     */
    public void goToAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * changes scene to the edit room scene
     * @throws IOException should never throw an exception
     */
    public void goToEditRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }
}
