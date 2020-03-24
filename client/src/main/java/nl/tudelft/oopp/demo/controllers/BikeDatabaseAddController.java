package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages all user input on the BikeDatabaseAdd.fxml view
 */
public class BikeDatabaseAddController implements Initializable {

    public ChoiceBox locationsCheckBox;
    public Button addButton;
    public ToggleButton availableToggle;
    private boolean available;

    /**
     * loads all the buildings of the database into the choice box as options for bike locations
     */
<<<<<<< HEAD
    private void loadData() throws IOException {
=======
    private void loadData() {
>>>>>>> 161-BikeDatabaseUI
        List<Building> buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());
        for (int i = 0; i < buildings.size(); i++) {
            locationsCheckBox.getItems().add(buildings.get(i).getName());
        }
    }

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

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e Just an ActionEvent param
     */
    @FXML
    private void toggleClickAvailable(ActionEvent e) {
        if (available) {
            availableToggle.setText("False");
            available = false;
        } else {
            availableToggle.setText("True");
            available = true;
        }
    }

    public void databaseAddBike() {
        //TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
=======
        loadData();
>>>>>>> 161-BikeDatabaseUI
    }
}
