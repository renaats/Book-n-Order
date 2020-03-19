package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ForgotPasswordController {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password reset");
        alert.setHeaderText(null);
        alert.setContentText("You will shortly receive an email to recover your account");
        alert.showAndWait();
    }
}
