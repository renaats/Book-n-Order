package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.user.UserInformation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyAccountController implements Initializable {

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label facultyLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button adminControl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserInformation userInformation = JsonMapper.userInformationMaper(ServerCommunication.getOwnUserInformation());
        fullNameLabel.setText(userInformation.getName() + " " + userInformation.getSurname());
        emailLabel.setText(userInformation.getEmail());
        facultyLabel.setText(userInformation.getFaculty());


        boolean showAdminButton = false;
//        try {
//            showAdminButton = ServerCommunication.getAdminButtonPermission();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (!showAdminButton) {
            anchorPane.getChildren().remove(adminControl);
        }
    }

    /**
     * Changes current scene to myCurrentBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes current scene to myPreviousBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     *  method changes the view to that of the DatabaseMenu
     * @param actionEvent clicking the button admin
     * @throws IOException the method will never throw an exception
     */
    public void adminPanel(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @param actionEvent clicking the button to go back to menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Takes care of clicking the logout button and communicating it onwards
     * @throws IOException input is valid hence we throw.
     */
    public void logoutUser() throws IOException {
        ServerCommunication.logoutUser();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authenticator");
        alert.setHeaderText(null);
        alert.setContentText("Logged out!");
        alert.showAndWait();
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }

    /**
     * Goes to change password scene when clicked
     * @param actionEvent the clicking of the change password button
     * @throws IOException this should never throw an exception
     */
    public void changePasswordScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/changePassword.fxml");
    }

    /**
     * return to the main menu when the home icon is clicked.
     * @param mouseEvent when th home icon is pressed
     * @throws IOException this method should never throw an exception
     */
    public void mainMenuIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
