package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseMainMenuController {


    public void dataBaseBikes(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void databaseFood(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void databaseBuildings(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }

    public void databaseRooms(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/RoomsEditOrAdd.fxml");
    }

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
