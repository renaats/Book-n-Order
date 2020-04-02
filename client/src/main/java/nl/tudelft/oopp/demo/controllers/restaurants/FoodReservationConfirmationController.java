package nl.tudelft.oopp.demo.controllers.restaurants;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "FoodConfirmation.fxml" file
 */
public class FoodReservationConfirmationController {

    /**
     * Changes the scene to the main mane when the background image is clicked
     * @throws IOException the input is always the same, so it should not throw and exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
