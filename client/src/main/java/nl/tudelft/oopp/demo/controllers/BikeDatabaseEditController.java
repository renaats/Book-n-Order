package nl.tudelft.oopp.demo.controllers;

import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class BikeDatabaseEditController {

    /**
     * Changes view to main menu
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Changes view to main Bike database menu
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToBikeMenu() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseMenu.fxml");
    }

    /**
     * Changes view to BikeDatabaseAdd
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToAddBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    /**
     * Changes view to main BikeDatabaseEdit
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToEditBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }
}
