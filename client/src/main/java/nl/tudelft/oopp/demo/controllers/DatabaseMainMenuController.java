package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

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

    public void goToBikeDatabse() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseMenu.fxml");
    }
}
