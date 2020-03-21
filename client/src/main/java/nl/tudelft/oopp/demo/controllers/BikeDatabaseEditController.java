package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the database edit bike view
 */
public class BikeDatabaseEditController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();
    final ObservableList<Bike> bikeSearchResult = FXCollections.observableArrayList();
    final ObservableList<Building> locationsSearchResults = FXCollections.observableArrayList();

    @FXML
    private TextField BikeChangeToField;
    @FXML
    private TextField bikeDeleteByIdTextField;
    @FXML
    private Button confirmDeleteByIdButton;
    @FXML
    private CheckBox available;
    @FXML
    private ChoiceBox locationChangeChoiceBoxSearch;
    @FXML
    private ChoiceBox locationChangeChoiceBox;
    @FXML
    private CheckBox availableCheckbox;
    @FXML
    private TextField bikeChangeToField;
    @FXML
    private TextField bikeDeleteByIdTextField;
    @FXML
    private Button confirmDeleteByIdButton;
    @FXML
    private CheckBox available;
    @FXML
    private TableView table;
    @FXML
    private TextField bikeFindByIdTextField;
    @FXML
    private TableColumn<Bike, Integer> colId;
    @FXML
    private TableColumn<Bike, String> colLocation;
    @FXML
    private TableColumn<Bike, String> colAvailable;
    @FXML
    private TextField bikeDeleteByIdTextField;
    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField bikeFindByIdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        loadDataIntoChoiceBox();
        loadBikesIntoTable();
    }

    /**
     * loads the current bikes into the table
     */
    public void loadBikesIntoTable() {
        List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
        bikeSearchResult.clear();
        bikeSearchResult.addAll(bikes);
        table.setItems(bikeSearchResult);
    }

    public void loadBikesIntoTableAvailable() {
        List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
        bikeSearchResult.clear();
        for (Bike b : bikes) {
            if (b.isAvailable() == available.isSelected()) {
                bikeSearchResult.add(b);
            }
        }
    }

        /**
         * Shows the bike with the chosen id in the table.
         */
    public void loadBikesIntoTableId() {
        List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
        bikeSearchResult.clear();
        for (int i = 0; i < bikes.size(); i++) {
            if (bikes.get(i).getId() == Integer.parseInt(bikeFindByIdTextField.getText())) {
                bikeSearchResult.add(bikes.get(i));
            }
        }
        table.setItems(bikeSearchResult);
    }

    public void loadBikesIntoTableLocation() {
        List<Building> locations = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
        locationsSearchResults.clear();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getName().equals(locationSearch.getText())) {
                locationsSearchResults.add(locations.get(i));
            }
        }
        bikeSearchResult.clear();
        for (int i = 0; i < bikes.size(); i++) {
            if (locationsSearchResults.contains(bikes.get(i).getLocation())){
                bikeSearchResult.add(bikes.get(i));
            }
        }
        table.setItems(bikeSearchResult);
    }
    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataIntoChoiceBox() {
        updateChoiceBoxList.clear();
        String a = "location";
        String b = "available";
        updateChoiceBoxList.addAll(a, b);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
        listBikesButtonClicked();
    }


    /**
     * Handles clicking the list button.
     */
    public void listBikesButtonClicked() {
        try {
            List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
            bikeResult.clear();
            bikeResult.addAll(bikes);
            table.setItems(bikeResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No buildings found.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking the building find button.
     */
    public void buildingIdButtonClicked() {
        try {
            int id = Integer.parseInt(bikeFindByIdTextField.getText());
            Bike building = JsonMapper.buildingMapper(ServerCommunication.findBuilding(id));
            buildingResult.clear();
            buildingResult.add(building);
            table.setItems(buildingResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
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
}
