package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BikeDatabaseAddController implements Initializable {

    public ChoiceBox locationsCheckBox;
    public Button addButton;
    public ToggleButton availableToggle;
    private boolean available;

    private void loadData() {
        JsonMapper.buildingListMapper(ServerCommunication.).getBuildings()
    }

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    public void goToBikeMenu() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseMenu.fxml");
    }

    public void goToAddBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    public void goToEditBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }
    public void toggleClick() {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }


}
