package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MyPreviousFoodReservationsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * Should load the previous reservations
     */
    public void loadData() {
        //TODO
    }

    /**
     * change the view to the main menu when the home icon is clicked
     * @throws IOException should never be thrown as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * changes view to the my Previous bookings view when the back arrow is clicked
     * @throws IOException should never be thrown as the input is always the same
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }
}
