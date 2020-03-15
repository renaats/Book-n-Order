package nl.tudelft.oopp.demo.controllers;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class BikeReservationConfirmationController implements Initializable {
    @FXML
    private Label idNumber;

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
     * @param mouseEvent the clicking of the background
     * @throws IOException the input is always the same, so it should not throw an IOException
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
