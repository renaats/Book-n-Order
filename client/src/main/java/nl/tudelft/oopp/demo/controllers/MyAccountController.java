package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyAccountController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button adminControl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean showAdminButton = false;
        try {
            // Server does not support it yet, try it out with
            // HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/user/find?email=r.jursevskis@student.tudelft.nl")).build();
            // in ServerCommunication line 29. This would then work for Renats account that has been given in the auth merge request.
            AppUser user = JsonMapper.appUserMapper(ServerCommunication.getUser());
            for (Role role : user.getRoles()) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    showAdminButton = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!showAdminButton) {
            anchorPane.getChildren().remove(adminControl);
        }
    }

    /**
     * Changes current scene to myAccountScene.fxml.
     * @throws IOException input will be valid.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
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
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @param actionEvent clicking the button to go back to menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /** Handles clicking the food button.
     * @throws IOException Input will be valid, hence we throw.
     */
    public void orderFood() throws IOException {
        ApplicationDisplay.changeScene("/orderFood.fxml");
    }

    /**
     * Handels the clicking of the button under the bike image at the main menu.
     * @throws IOException when it fails
     */
    public void rentBike() throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void bookRoom() throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
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

}
