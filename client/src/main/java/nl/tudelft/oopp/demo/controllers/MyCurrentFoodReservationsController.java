package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "myCurrentFoodReservations.fxml" file
 */
public class MyCurrentFoodReservationsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * will load the food orders
     */
    public void loadData() {
        //TODO
    }

    /**
     * change the view to the main menu when the home icon is clicked
     * @throws IOException should never be thrown
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * changes view to the my current bookings view when the back arrow is clicked
     * @throws IOException should never be thrown
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }
}
