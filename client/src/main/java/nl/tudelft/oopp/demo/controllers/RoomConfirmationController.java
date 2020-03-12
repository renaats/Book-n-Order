package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class RoomConfirmationController {
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
