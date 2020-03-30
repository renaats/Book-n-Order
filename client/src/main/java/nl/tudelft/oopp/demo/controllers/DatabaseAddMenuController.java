package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseAddMenuController {
    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to add menu view
     * @throws IOException Should never throw the exception
     */
    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    /**
     * Goes to the edit menu view
     * @throws IOException Should never throw the exception
     */
    public void goToEditMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditMenu.fxml");
    }

    /**
     * Goes to the menu for editing restaurants
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantsMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseFoodMainMenu.fxml");
    }
}
