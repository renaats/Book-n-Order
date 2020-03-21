package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.user.UserInformation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class ConfirmationSixDigitsController {

    @FXML
    public TextField sixDigitCode;

    public void confirmValidity(ActionEvent actionEvent) throws IOException {

        try {
            int code = Integer.parseInt(sixDigitCode.getText());
            String response =  ServerCommunication.validateUser(code);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();
            if(response == "Successfully executed.") {
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

    public void goToLoginScreen(MouseEvent mouseEvent) throws IOException {
        UserInformation.setBearerKey(null);
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }
}

