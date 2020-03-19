package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the content correct content into the FXML objects that need to display server information and
 * controls all the user inputs thought the GUI made by the user in the "DatabaseMainMenu.fxml" file
 */
public class DatabaseMainMenuController {

    public void dataBaseBikes() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void databaseFood() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void databaseBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }

    public void databaseRooms() throws IOException {
        ApplicationDisplay.changeScene("/RoomsEditOrAdd.fxml");
    }

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
