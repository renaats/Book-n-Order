package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to ConfirmationSixDigits.fxml file
 */
public class ConfirmationSixDigitsController {

    @FXML
    public TextField sixDigitCode;

    /**
     * Checks the authenticity of user's email.
     */
    public void confirmValidity() {
        try {
            int code = Integer.parseInt(sixDigitCode.getText());
            String response =  UserServerCommunication.validateUser(code);
            if (response.equals("Successfully executed.")) {
                ApplicationDisplay.changeScene("/MainMenu.fxml");
            } else {
                CustomAlert.warningAlert(response);
            }
        } catch (Exception e) {
            CustomAlert.warningAlert("Please provide a six digit code.");
        }
    }

    /**
     * Goes to login menu if the user clicks on the back arrow.
     * @throws IOException Deals with improper input
     */
    public void goToLoginScreen() throws IOException {
        AuthenticationKey.setBearerKey(null);
        ApplicationDisplay.changeScene("/LoginScreen.fxml");
    }
}

