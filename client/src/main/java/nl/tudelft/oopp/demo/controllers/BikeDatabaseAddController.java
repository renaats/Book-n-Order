package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the database add bike view.
 */
public class BikeDatabaseAddController implements Initializable {

    @FXML
    private ChoiceBox<String> locationsCheckBox;
    @FXML
    private ToggleButton availableToggle;
    @FXML
    private TextField number;

    private boolean available;
    private List<Building> buildings;

    /**
     * Will load a list of buildings into the buildings list.
     */
    public BikeDatabaseAddController() {
        try {
            buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());

        } catch (IOException e) {
            CustomAlert.warningAlert("There are currently no buildings in the database");
        }
    }

    /**
     * Loads all the buildings of the database into the choice box as options for bike locations.
     */
    private void loadData() {
        for (Building building : buildings) {
            locationsCheckBox.getItems().add(building.getName());
        }
    }

    /**
     * Changes view to main menu.
     * @throws IOException should never throw an exception as the input is always the same.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Changes view to main Bike database menu.
     * @throws IOException should never throw an exception as the input is always the same.
     */
    public void goToBikeMenu() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseMenu.fxml");
    }

    /**
     * Changes view to BikeDatabaseAdd.
     * @throws IOException should never throw an exception as the input is always the same.
     */
    public void goToAddBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    /**
     * Changes view to main BikeDatabaseEdit.
     * @throws IOException should never throw an exception as the input is always the same.
     */
    public void goToEditBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickAvailable() {
        if (available) {
            availableToggle.setText("False");
            available = false;
        } else {
            availableToggle.setText("True");
            available = true;
        }
    }

    /**
     * Adds the bike to the database.
     */
    public void databaseAddBike() {
        int buildingId = -1;
        for (Building building : buildings) {
            if (building.getName().equals(locationsCheckBox.getValue())) {
                buildingId = building.getId();
            }
        }
        boolean success = false;
        int numberOfBikes = 0;
        try {
            numberOfBikes = Integer.parseInt(number.getText());
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Please use and integer for the number of bikes");
            return;
        }
        if (numberOfBikes > 0) {
            String response = "";
            for (int i = 0; i < Integer.parseInt(number.getText()); i++) {
                response = ServerCommunication.addBike(buildingId,available);
                if (response.equals("Successfully added!")) {
                    success = true;
                } else {
                    success = false;
                    CustomAlert.warningAlert("An error occurred while adding the bikes");
                    break;
                }
            }
            if (success) {
                CustomAlert.informationAlert(number.getText() + " Bike/s have been successfully");
            } else {
                CustomAlert.warningAlert(response);
            }
        } else {
            CustomAlert.warningAlert("Please select a number bigger than 0");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}
