package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the ForgotPassword View
 */
public class ForgotPasswordController {

    @FXML
    TextField emailField;

    /**
     * Goes back to the login screen
     * @throws IOException the input is always the same so there should not be an exception
     */
    public void loginScene() throws IOException {
        ApplicationDisplay.changeScene("/LoginScreen.fxml");
    }

    /**
     * Checks with the server if the email exist and have the server send an email if it does
     */
    public void sendEmail() {
        String email = emailField.getText();
        CustomAlert.informationAlert(UserServerCommunication.sendRecoveryPassword(email));
    }
}
