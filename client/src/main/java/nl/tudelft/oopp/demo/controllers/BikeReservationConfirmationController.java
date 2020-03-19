package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bikeConfirmations.fxml" file
 */
public class BikeReservationConfirmationController implements Initializable {
    public Text youReservationsHas;
    public Text clickAny;
    public Label label;
    public Text yourIdIs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * Should get the order ID and write it here
     */
    private void loadData() {
        //TODO
    }

    /**
     * when you click on the background it takes you back to the main menu
     * @throws IOException the input is always the same, so it should not throw an IOException
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
