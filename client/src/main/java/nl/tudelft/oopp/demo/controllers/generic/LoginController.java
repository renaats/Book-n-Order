package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "LoginScreen.fxml" file
 */
public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    /**
     * Changes current to MainMenu.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Takes care of authenticating a user.
     */
    public void loginButton() {
        String username = usernameField.getText();
        if (username.equals("")) {
            CustomAlert.errorAlert("Email is required.");
            return;
        }

        String password = passwordField.getText();
        if (password.equals("")) {
            CustomAlert.warningAlert("Password is required.");
            return;
        }

        // PUT IN PLACE FOR TA TESTING, CONFIRMED BY OUR OWN TA.
        UserServerCommunication.addUser("staff@tudelft.nl", "Staff", "AlsoStaff", "EWI", "1234", "");
        UserServerCommunication.addUser("admin@tudelft.nl", "Admin", "AlsoAdmin", "None", "1234", "");
        UserServerCommunication.addUser("building_admin@tudelft.nl", "BuildingAdmin", "AlsoBuildingAdmin", "None", "1234", "");
        UserServerCommunication.addUser("bike_admin@tudelft.nl", "BikeAdmin", "AlsoBikeAdmin", "None", "1234", "");
        UserServerCommunication.addUser("restaurant@tudelft.nl", "Restaurant", "AlsoRestaurant", "None", "1234", "");
        // PUT IN PLACE FOR TA TESTING, CONFIRMED BY OUR OWN TA.

        String message = UserServerCommunication.loginUser(username, password);
        if (message.equals("Login and/or password is incorrect.")) {
            CustomAlert.errorAlert(message);
        }
    }

    /**
     * Changes to registrationScene.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void registrationScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/RegistrationScreen.fxml");
    }

    /**
     * Changes scene to forgot password scene
     * @throws IOException should never throw, since the input is always the same
     */
    public void forgotPassword() throws IOException {
        ApplicationDisplay.changeScene("/ForgotPasswordScene.fxml");
    }
}