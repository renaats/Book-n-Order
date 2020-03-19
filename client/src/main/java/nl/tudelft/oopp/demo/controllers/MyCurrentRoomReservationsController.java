package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the content correct content into the FXML objects that need to display server information and
 * controls all the user inputs thought the GUI made by the user in the "myCurrentRoomReservations.fxml" file
 */
public class MyCurrentRoomReservationsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    public void loadData() {

    }

    /**
     * change the view to the main menu when the home icon is clicked
     * @throws IOException should never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * changes view to the my current bookings view when the back arrow is clicked
     * @throws IOException should never throw an exception
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }
}
