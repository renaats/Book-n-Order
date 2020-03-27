package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.StageStyle;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the database add bike view.
 */
public class BikeDatabaseAddController implements Initializable {

    @FXML
    private ChoiceBox locationsCheckBox;
    @FXML
    private Button addButton;
    @FXML
    private ToggleButton availableToggle;
    @FXML
    private TextField number;

    private boolean available;
    private List<Building> buildings;

    /**
     * Will load a list of buildings into the buildings list.
     * @throws IOException will throw when there are no buildings.
     */
    public BikeDatabaseAddController() throws IOException {
        //This method is required because this throws and exception:
        // private List<Building> buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());
        try {
            buildings = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("There are currently no buildings in the database");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * loads all the buildings of the database into the choice box as options for bike locations.
     */
    private void loadData() {
        for (int i = 0; i < buildings.size(); i++) {
            locationsCheckBox.getItems().add(buildings.get(i).getName());
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
     * @param e Just an ActionEvent param.
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
     * Adds the building to the database.
     */
    public void databaseAddBike() {
        int buildingId = -1;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getName().equals(locationsCheckBox.getValue())) {
                buildingId = buildings.get(i).getId();
            }
        }
        boolean success = false;
        for (int i = 1; i < Integer.parseInt(number.getText()); i++) {
            if (ServerCommunication.addBike(buildingId,available).equals("Successfully added!")) {
                success = true;
            }
        }
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(number.getText() + " Bike/s have been " + ServerCommunication.addBike(buildingId,available));
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.addBike(buildingId,available));
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}
