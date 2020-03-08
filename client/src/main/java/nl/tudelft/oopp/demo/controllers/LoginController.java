package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;


    /**
     * Changes to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void loginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Building remover");
//        alert.setHeaderText(null);
//        alert.setContentText(ServerCommunication.updateRoom(id, attribute, changeValue));
//        alert.showAndWait();
        ServerCommunication.loginUser(username, password);
    }

    /**
     * Changes to registrationScene.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void registrationScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/registrationScreen.fxml");
    }
}