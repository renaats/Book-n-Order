package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Changes to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/templateScene.fxml", null);
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml", null);
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml", null);
    }

    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml", null);
    }
}
