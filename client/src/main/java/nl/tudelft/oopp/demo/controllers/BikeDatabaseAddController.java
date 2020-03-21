package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class BikeDatabaseAddController implements Initializable {

    public ChoiceBox locationsCheckBox;
    public Button addButton;
    public ToggleButton availableToggle;
    public TextField number;
    private boolean available;
    private List<Building> buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());

    /**
     * loads all the buildings of the database into the choice box as options for bike locations
     */
    private void loadData() {
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

    /**
     * Adds the building to the database
     */
    public void databaseAddBike() {
        int buildingId = -1;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getName().equals(locationsCheckBox.getValue())){
                buildingId = buildings.get(i).getId();
            }
        }
        for (int i = 0; i<Integer.parseInt(number.getText()); i++){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bike adder");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.addBike(buildingId,available));
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}
