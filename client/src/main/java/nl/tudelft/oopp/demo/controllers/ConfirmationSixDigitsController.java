package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class ConfirmationSixDigitsController {

    @FXML
    public TextField sixDigitCode;

    public void confirmValidity(ActionEvent actionEvent) throws IOException {
            int code = Integer.parseInt(sixDigitCode.getText());
            ServerCommunication
    }
}

