package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseMenuFoodRestaurantsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    public void goToAddRestaurants(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    public void goToEditRestaurants(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }
}
