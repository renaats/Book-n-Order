package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseMainMenu.fxml" file
 */
public class DatabaseMainMenuController {

    public void databaseBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void databaseRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    public void databaseRestaurants() throws  IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    public void databaseBikes() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBikeMenu.fxml");
    }
}
