package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class LoginController {

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }
}
