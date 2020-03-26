package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "login-screen.fxml" file
 */
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
     */
    public void loginButton() {
        String username = usernameField.getText();
        if (username.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Email is required.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
            return;
        }

        String password = passwordField.getText();
        if (password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Password is required.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
            return;
        }

        String message = ServerCommunication.loginUser(username, password);
        if (message.equals("Login and/or password is incorrect.")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertError.css").toExternalForm());
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

    /**
     * Changes scene to forgot password scene
     * @throws IOException should never throw, since the input is always the same
     */
    public void forgotPassword() throws IOException {
        ApplicationDisplay.changeScene("/ForgotPasswordScene.fxml");
    }
}