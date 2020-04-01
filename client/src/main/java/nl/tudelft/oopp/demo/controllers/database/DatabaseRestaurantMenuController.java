package nl.tudelft.oopp.demo.controllers.database;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class DatabaseRestaurantMenuController {
    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }
}
