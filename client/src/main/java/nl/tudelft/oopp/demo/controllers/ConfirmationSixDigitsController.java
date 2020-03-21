package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class ConfirmationSixDigitsController {

    @FXML
    public TextField sixDigitCode;

    public void confirmValidity(ActionEvent actionEvent) throws IOException {

        try {
            int code = Integer.parseInt(sixDigitCode.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.validateUser(code));
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    public void goToMyAccountScene(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("login-screen.fxml");
    }
}

