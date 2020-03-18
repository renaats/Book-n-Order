package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class ForgotPasswordController {
    /**
     * Goes back to the login screen
     * @throws IOException the input is always the same wo there should not be an exception
     */
    public void loginScene() throws IOException {
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }

    /**
     * should check with the server if the email exist and have the server send an email if it does
     */
    public void sendEmail() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password reset");
        alert.setHeaderText(null);
        alert.setContentText("If the email is correct you will shortly receive an email to recover your account");
        alert.showAndWait();
    }
}
