package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the User inputs for the DatabaseAddMenu.fxml scene
 */
public class DatabaseAddMenuController implements Initializable {

    final ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();
    final ObservableList<Dish> dishes = FXCollections.observableArrayList();

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> restaurantChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurants.addAll(JsonMapper.restaurantListMapper(ServerCommunication.getRestaurants()));
        loadDataToChoiceBoxes();
        for (Restaurant restaurant : restaurants) {
            restaurantChoiceBox.getItems().add(restaurant.getName());
        }

    }

    /**
     * Loads the restaurants and dishes to the choice boxes.
     */
    private void loadDataToChoiceBoxes() {
    }

    /**
     * Returns to the main database menu.
     * @throws IOException Should never throw the exception.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to add menu view.
     * @throws IOException Should never throw the exception.
     */
    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    /**
     * Goes to the edit menu view.
     * @throws IOException Should never throw the exception.
     */
    public void goToEditMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditMenu.fxml");
    }

    /**
     * Goes to the menu for editing restaurants.
     * @throws IOException Should never throw the exception.
     */
    public void goToRestaurantsMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseFoodMainMenu.fxml");
    }

    /**
     * Adds a menu to the database when the add menu button is clicked.
     */
    public void databaseAddMenu() {
        String name = nameTextField.getText();
        if (name.equals("")) {
            CustomAlert.warningAlert("please input a name");
        }
        String restaurantName = restaurantChoiceBox.getValue();
        if (name.equals(null)) {
            CustomAlert.warningAlert("select a menu");
        }
        int id = -1;
        for (Restaurant value : restaurants) {
            if (restaurantName.equals(value.getName())) {
                id = value.getId();
            }
        }
        CustomAlert.informationAlert(ServerCommunication.addMenu(name, id));
    }
}
