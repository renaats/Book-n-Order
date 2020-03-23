package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "mainMenuReservations.fxml" file
 */
public class MainMenuReservationsController {

    /**
     * Handles the clicking of the button under the bike image at the main menu.
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
        ApplicationDisplay.changeScene("/OrderFoodChooseRestaurant.fxml");
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

    /**
     * will lead to the calendar scene
     */
    public void calendarIcon() {
        //TODO
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
        ApplicationDisplay.changeScene("/OrderFoodChooseRestaurant.fxml");
    }

    /**
     * goes to bike reservations when the bike image is clicked
     * @param mouseEvent the clicking of the bike icon
     * @throws IOException this method should never throw an exception
     */
    public void rentBikeIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }
}
