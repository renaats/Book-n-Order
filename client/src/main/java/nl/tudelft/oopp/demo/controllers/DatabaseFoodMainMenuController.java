package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseFoodMainMenuController {
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    public void goToAddRestaurant() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    public void goToEditRestaurant() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRestaurants.fxml");
    }

    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    public void goToEditMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditMenu.fxml");
    }

    public void goToAddRestaurantHours() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurantHours.fxml");
    }

    public void goToEditRestaurantHours() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRestaurantHours.fxml");
    }

    public void goToAddDish() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddDish.fxml");
    }
}
