package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.scene.input.MouseEvent;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "FoodConfirmation.fxml" file
 */
public class FoodReservationConfirmationController {
    /**
     * Changes the scene to the main mane when the background image is clicked
     * @param mouseEvent the event tis the clicking of the background
     * @throws IOException the input is always the same, so it should not throw and exception
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
