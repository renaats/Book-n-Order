package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.authentication.AuthenticationKey;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.user.UserInformation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to ConfirmationSixDigits.fxml file
 */
public class ConfirmationSixDigitsController {

    @FXML
    public TextField sixDigitCode;

    /**
     * Checks the authenticity of user's email.
     * @param actionEvent A confirm button click
     * @throws IOException Deals with improper input
     */
    public void confirmValidity(ActionEvent actionEvent) throws IOException {
        try {
            int code = Integer.parseInt(sixDigitCode.getText());
            String response =  ServerCommunication.validateUser(code);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();
            if (response.equals("Successfully executed.")) {
                ApplicationDisplay.changeScene("/mainMenu.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Goes to login menu if the user clicks on the back arrow.
     * @param mouseEvent The click on the back arrow
     * @throws IOException Deals with improper input
     */
    public void goToLoginScreen(MouseEvent mouseEvent) throws IOException {
        AuthenticationKey.setBearerKey(null);
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }
}

