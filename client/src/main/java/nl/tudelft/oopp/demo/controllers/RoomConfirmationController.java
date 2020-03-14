package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class RoomConfirmationController {

    /**
     * will change to main menu when the background is clicked
     * @throws IOException should never throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
