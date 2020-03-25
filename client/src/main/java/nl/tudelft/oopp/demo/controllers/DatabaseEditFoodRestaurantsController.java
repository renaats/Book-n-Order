package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseEditFoodRestaurantsController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //need to load the possible building
    }


    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    public void goToAddRestaurants(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    public void goToEditRestaurants(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRestaurants.fxml");
    }

    public void goToRestaurantsMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenuFoodRestaurants.fxml");
    }
}
