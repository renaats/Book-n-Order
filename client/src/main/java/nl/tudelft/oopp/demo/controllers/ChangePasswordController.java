package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "changePassword.fxml" file
 */
public class ChangePasswordController {

    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword2;
    @FXML
    private PasswordField newPassword1;

    /**
     * returns to the "myAccount" scene when the back arrow is pressed
     * @throws IOException this method should never throw an exception
     */
    public void goToMyAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * checks if the 2 versions of the password are the same and if they are it changes scene to
     * the myAccount scene. if they are not It shows an alert
     * @throws IOException should never throw an exception
     */
    public void changePassword() throws IOException {
        String password1 = newPassword1.getText();
        String password2 = newPassword2.getText();
        if (password1.equals(password2)) {
            String response = ServerCommunication.ChangePassword("r.jursevskis@student.tudelft.nl",password1);
            System.out.println(response);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change password successful");
            alert.setHeaderText(null);
            alert.setContentText("Your passwords match, they have been changed");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change password successful");
            alert.setHeaderText(null);
            alert.setContentText("Your passwords match, they have been changed");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Your passwords match, they have been changed");
            alert.showAndWait();
        } else {
            CustomAlert.errorAlert("Passwords do not match.");
        }
    }
}
