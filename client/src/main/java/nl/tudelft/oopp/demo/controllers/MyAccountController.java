package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyAccountController {
    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will be valid.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml", null);
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml", null);
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml", null);
    }

    /**
     * Changes to templateScene.fxml.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/templateScene.fxml", null);
    }

    public void adminPanel(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml", null);
    }
}
