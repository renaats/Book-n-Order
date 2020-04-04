package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "ChangePassword.fxml" file
 */
public class ChangePasswordController {

    @FXML
    private PasswordField newPassword2;
    @FXML
    private PasswordField newPassword1;

    /**
     * Returns to the user's "Account" scene when the back arrow is pressed
     * @throws IOException this method should never throw an exception
     */
    public void goToMyAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/MyAccountScene.fxml");
    }

    /**
     * Checks if the 2 versions of the password are the same and if they are it changes scene to
     * the myAccount scene. if they are not It shows an alert
     * @throws IOException should never throw an exception
     */
    public void changePassword() throws IOException {
        String password1 = newPassword1.getText();
        String password2 = newPassword2.getText();
        if (password1.equals(password2)) {
            String response = UserServerCommunication.changeUserPassword(password1);
            CustomAlert.informationAlert(response);
            ApplicationDisplay.changeScene("/LoginScreen.fxml");
        } else {
            CustomAlert.errorAlert("Passwords do not match.");
        }
    }
}
