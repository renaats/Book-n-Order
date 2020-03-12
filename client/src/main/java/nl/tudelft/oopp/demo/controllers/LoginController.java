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
     * Changes current to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Takes care of authenticating a user.
     * @throws IOException input will be valid, hence we throw.
     */
    public void loginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String message = ServerCommunication.loginUser(username, password);

        if (message.equals("Login and/or password is incorrect.")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    /**
     * Changes to registrationScene.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void registrationScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/registrationScreen.fxml");
    }

    public void goTomainMenuTemp(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}