package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bikeConfirmations.fxml" file
 */
public class BikeReservationConfirmationController {
    public Text youReservationsHas;
    public Text clickAny;
    public Text yourIdIs;

    /**
     * when you click on the background it takes you back to the main menu
     * @throws IOException the input is always the same, so it should not throw an IOException
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }
}
