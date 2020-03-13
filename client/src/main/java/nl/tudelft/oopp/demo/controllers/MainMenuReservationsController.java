package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MainMenuReservationsController {

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

    /** Handles clicking the food button.
     * @throws IOException Input will be valid, hence we throw.
     */
    public void orderFood() throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodChoseRestaurant.fxml");
    }
    
    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */

    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }


    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }

    public void calendarIcon(MouseEvent mouseEvent) {

    }

    /**
     * returns to the main menu when the home icon is clicked
     * @param mouseEvent the clicking of the home icon
     * @throws IOException this method should never throw an exception
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * goes to room reservations when the room image is clicked
     * @param mouseEvent the clicking of the room icon
     * @throws IOException this method should never throw an exception
     */
    public void bookRoomIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    /**
     * goes to food reservations when the food image is clicked
     * @param mouseEvent the clicking of the food icon
     * @throws IOException this method should never throw an exception
     */
    public void orderFoodIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodChoseRestaurant.fxml");
    }

    /**
     * goes to bike reservations when the bike image is clicked
     * @param mouseEvent the clicking of the bike icon
     * @throws IOException this method should never throw an exception
     */
    public void rentBikeIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * goes back to my account scene when the back arrow icon is pressed
     * @param mouseEvent
     * @throws IOException
     */
    public void goToMainMenuReservations(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }
}
