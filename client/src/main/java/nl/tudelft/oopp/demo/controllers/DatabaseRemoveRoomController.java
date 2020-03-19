package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the content correct content into the FXML objects that need to display server information and
 * controls all the user inputs thought the GUI made by the user in the "DatabaseRemoveRooms.fxml" file
 */
public class DatabaseRemoveRoomController implements Initializable {

    public Button confirmDeleteByIdButton;
    public ImageView goToRoomMenuButton;

    /**
     * Will send the user to the main database menu when clicked.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * When the room icon is clicked it take you to the RoomEditOrAdd.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void goToRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/RoomsEditOrAdd.fxml");
    }

    /**
     * When the menu item add is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseEditRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }

    /**
     * When the menu item remove is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseRemoveRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveRoom.fxml");
    }
}
