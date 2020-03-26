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

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.StageStyle;

import javafx.stage.StageStyle;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the database edit bike view
 */
public class BikeDatabaseEditController implements Initializable {

    final ObservableList<Bike> bikeSearchResult = FXCollections.observableArrayList();
    final ObservableList<Building> locationsSearchResults = FXCollections.observableArrayList();

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
    private TableColumn<Bike, Boolean> colAvailable;
    @FXML
    private ChoiceBox<String> updateChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        try {
            loadDataIntoChoiceBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Loads the bike that have the same value of "available" than the checkBox
     */
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

    /**
     * Loads database bikes into table filtering by location
     * @throws IOException throws when there is nothing in the location filter
     */
    public void loadBikesIntoTableLocation() throws IOException {
        List<Building> locations = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        locationsSearchResults.clear();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getName().equals((String) locationChangeChoiceBoxSearch.getValue())) {
                locationsSearchResults.add(locations.get(i));
            }
        }
        bikeSearchResult.clear();
        List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
        for (int i = 0; i < bikes.size(); i++) {
            if (locationsSearchResults.contains(bikes.get(i).getLocation())) {
                bikeSearchResult.add(bikes.get(i));
            }
        }
        table.setItems(bikeSearchResult);
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataIntoChoiceBox() throws IOException {
        List<Building> locations = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        locationsSearchResults.clear();
        locationsSearchResults.addAll(locations);
        for (int i = 0; i < locationsSearchResults.size(); i++) {
            locationChangeChoiceBoxSearch.getItems().add(locations.get(i).getName());
        }
        locationsSearchResults.clear();
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
     * deletes a bike based on the ID of the text box
     */
    public void deleteBikeById() {
        int id = Integer.parseInt(bikeDeleteByIdTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBike(id));
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
        alert.showAndWait();
        loadBikesIntoTable();
    }

    /**
     * Changes the attribute of the bike at the top of the table
     */
    public void updateBikeAttribute() {
        int id = bikeSearchResult.get(0).getId();
        String ava;
        if (availableCheckbox.isSelected()) {
            ava = "true";
        } else {
            ava = "false";
        }
        if (locationChangeChoiceBox.getValue() != null) {
            int buildingId = nameGetId((String) locationChangeChoiceBox.getValue());
            System.out.println("building ID = ." + buildingId + ".");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateBike(id,"location", "" + buildingId));
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        ServerCommunication.updateBike(id,"available",ava);
        loadBikesIntoTable();
    }

    /**
     * gets the ID out od the string of form "name (ID)"
     * @param value the name of the given form
     * @return the ID present in the initial string
     */
    private int nameGetId(String value) {
        value = value.replaceAll("\\D+", "");
        value = value.trim();
        if (value.equals("")) {
            return -1;
        }
        return Integer.parseInt(value);
    }

    /**
     * Deletes the selected  bike from the table and the database
     */
    public void deleteBikeUsingTable() {
        Bike bike = (Bike) table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBike(bike.getId()));
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
        alert.showAndWait();
        loadBikesIntoTable();
    }

    /**
     * allows you to select the bike you wish to update in the table
     */
    public void updateUsingTable() throws IOException {
        Bike bike = (Bike) table.getSelectionModel().getSelectedItem();
        bikeFindByIdTextField.setText("" + bike.getId());
        loadBikesIntoTableId();
        availableCheckbox.setSelected(bike.isAvailable());
        List<Building> locations = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        locationChangeChoiceBox.getItems().clear();
        for (int i = 0; i < locations.size(); i++) {
            locationChangeChoiceBox.getItems().add(locations.get(i).getName() + " (" + locations.get(i).getId() + ")");
        }
    }
}
