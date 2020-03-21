package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class BikeDatabaseMenuController {

    /**
     * Changes view to the database main menu when the home icon is clicked
     * @throws IOException should never throw an IOException as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Changes view to the database bike add when the add bikes button is clicked
     * @throws IOException should never throw an IOException as the input is always the same
     */
    public void goToAddBikes() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    /**
     * Changes view to the database bike edit when the add bikes button is clicked
     * @throws IOException should never throw an IOException as the input is always the same
     */
    public void goToEditBikes() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }
}
