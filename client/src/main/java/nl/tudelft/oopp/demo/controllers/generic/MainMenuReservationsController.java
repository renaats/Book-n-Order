package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.PersonalCalendarView;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MainMenuReservations.fxml" file
 */
public class MainMenuReservationsController {

    /**
     * Handles the clicking of the button under the bike image at the main menu.
     * @throws IOException when it fails
     */
    public void rentBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeReservations.fxml");
    }

    /**
     * Changes to BookRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void bookRoom() throws IOException {
        ApplicationDisplay.changeScene("/BookRoom.fxml");
    }

    /** Handles clicking the food button.
     * @throws IOException Input will be valid, hence we throw.
     */
    public void orderFood() throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodChooseRestaurant.fxml");
    }
    
    /**
     * Changes to MyPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */

    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     * Returns to the main menu when the home icon is clicked
     * @throws IOException this method should never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Goes to room reservations when the room image is clicked
     * @throws IOException this method should never throw an exception
     */
    public void bookRoomIcon() throws IOException {
        ApplicationDisplay.changeScene("/BookRoom.fxml");
    }

    /**
     * Goes to food reservations when the food image is clicked
     * @throws IOException this method should never throw an exception
     */
    public void orderFoodIcon() throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodChooseRestaurant.fxml");
    }

    /**
     * Goes to bike reservations when the bike image is clicked
     * @throws IOException this method should never throw an exception
     */
    public void rentBikeIcon() throws IOException {
        ApplicationDisplay.changeScene("/BikeReservations.fxml");
    }

    /**
     * Goes to personal calendar when the calendar image is clicked
     */
    public void openCalendar() {
        ApplicationDisplay.showCalendarScene(new PersonalCalendarView());
    }
}
