package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the content correct content into the FXML objects that need to display server information and
 * controls all the user inputs thought the GUI made by the user in the "changePassword.fxml" file
 */
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertError.css").toExternalForm());
            alert.showAndWait();
        }
    }
}
