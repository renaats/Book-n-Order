package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import javax.swing.*;

public class BikeDatabaseEditController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();

    @FXML
    private TextField BikeChangeToField;
    @FXML
    private TextField bikeDeleteByIdTextField;
    @FXML
    private Button confirmDeleteByIdButton;
    @FXML
    private CheckBox available;
    @FXML
    private TextField locationSearch;
    @FXML
    private TableView table;
    @FXML
    private TextField bikeFindByIdTextField;
    @FXML
    private TableColumn<Bike, String> colId;
    @FXML
    private TableColumn<Bike, String> colLocation;
    @FXML
    private TableColumn colAvailable;
    @FXML
    private TextField buildingDeleteByIdTextField;

    @FXML
    private ChoiceBox<String> updateChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
        loadDataUpdateChoiceBox();
    }

    /**
     * loads the current bikes into the table
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.removeAll();
        String a = "Location";
        String b = "Available";
        updateChoiceBoxList.addAll(a, b);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
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
