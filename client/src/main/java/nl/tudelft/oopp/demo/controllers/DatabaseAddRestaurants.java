package nl.tudelft.oopp.demo.controllers;

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
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseAddRestaurants implements Initializable {

    final ObservableList<Building> buildings = FXCollections.observableArrayList();

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> buildingsChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataIntoChoiceBoxes();
    }

    private void loadDataIntoChoiceBoxes() {
        try {
            buildings.addAll(JsonMapper.buildingListMapper(ServerCommunication.getBuildings()));
            for (Building building : buildings) {
                buildingsChoiceBox.getItems().add(building.getName());
            }
        } catch (IOException e) {
            CustomAlert.warningAlert("There are no buildings in the database");
        }
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to add restaurant view
     * @throws IOException Should never throw the exception
     */
    public void goToAddRestaurants() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    /**
     * Goes to the edit restaurant view
     * @throws IOException Should never throw the exception
     */
    public void goToEditRestaurants() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRestaurants.fxml");
    }

    /**
     * Goes to the menu for editing restaurants
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantsMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseFoodMainMenu.fxml");
    }

    /**
     * Adds a room to the database
     */
    public void databaseAddRoom() {
        String building = buildingsChoiceBox.getValue();
        String name = nameTextField.getText();
        int id = -1;
        for (Building value : buildings) {
            if (building.equals(value.getName())) {
                id = value.getId();
            }
        }
        CustomAlert.informationAlert(ServerCommunication.addRestaurant(id,name));
    }
}
