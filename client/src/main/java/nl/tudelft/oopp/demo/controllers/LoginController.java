package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class LoginController {
    /**
     * Changes to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to registrationScene.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void registrationScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/registrationScreen.fxml");
    }
}