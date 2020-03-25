package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class DatabaseAddFoodRestaurantsController implements Initializable {

    @FXML
    private ChoiceBox locationsCheckBox;
    @FXML
    private TextField nameTextField;
    private List<Building> buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());

    public DatabaseAddFoodRestaurantsController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * loads all the buildings of the database into the choice box as options for bike locations
     */
    private void loadData() {
        for (int i = 0; i < buildings.size(); i++) {
            locationsCheckBox.getItems().add(buildings.get(i).getName());
        }
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

    public void databaseAddRestaurant(ActionEvent actionEvent) {
    }

    public void goToRestaurantsMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenuFoodRestaurants.fxml");
    }

    /**
     * Adds the building to the database
     */
    public void databaseAddRestaurant() {
        int buildingId = -1;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getName().equals(locationsCheckBox.getValue())) {
                buildingId = buildings.get(i).getId();
            }
        }
        String name=nameTextField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Restaurant adder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.addRestaurant(buildingId,name));
        alert.showAndWait();
    }
}
