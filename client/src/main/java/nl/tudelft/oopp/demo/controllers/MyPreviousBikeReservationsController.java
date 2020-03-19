package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MyPreviousBikeReservationsController implements Initializable {
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
     * @throws IOException should never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * changes view to the my previous bookings view when the back arrow is clicked
     * @throws IOException should never throw an exception
     */
    public void goToMyPreviousReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }
}
