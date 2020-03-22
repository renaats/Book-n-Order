package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ChangePasswordController {
    @FXML
    public PasswordField newPassword2;
    public PasswordField newPassword1;

    /**
     * returns to the "myAccount" scene when the back arrow is pressed
     * @param mouseEvent the pressing of the back arrow icon
     * @throws IOException this method should never throw an exception
     */
    public void goToMyAccountScene(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * checks if the 2 versions of the password are the same and if they are it changes scene to
     * the myAccount scene. if they are not It shows an alert
     * @throws IOException should never throw an exception
     */
    public void changePassword() throws IOException {
        if (true) {
            ApplicationDisplay.changeScene("/myAccountScene.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("the 2 new passwords are not the same, please re-enter them");
            alert.showAndWait();
        }
    }
}
