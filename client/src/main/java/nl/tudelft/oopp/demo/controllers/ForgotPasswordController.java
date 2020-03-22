package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to ForgotPasswordScene.fxml file
 */
public class ForgotPasswordController {

    @FXML
    TextField emailField;

    /**
     * Goes back to the login screen
     * @throws IOException the input is always the same so there should not be an exception
     */
    public void loginScene() throws IOException {
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }

    /**
     * Checks with the server if the email exist and have the server send an email if it does
     */
    public void sendEmail() {
        String email = emailField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.sendRecoveryPassword(email));
        alert.showAndWait();
    }
}
