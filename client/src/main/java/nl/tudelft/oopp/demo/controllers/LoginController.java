package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class LoginController {
    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/templateScene.fxml");
    }
}