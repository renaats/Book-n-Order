package nl.tudelft.oopp.demo.controllers;

import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class roomConfirmationController {
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
