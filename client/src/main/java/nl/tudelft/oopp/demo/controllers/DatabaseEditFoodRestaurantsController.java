package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseEditFoodRestaurantsController implements Initializable {

    final ObservableList<Restaurant> restaurantsToTable = FXCollections.observableArrayList();
    List<Building> locations;
    List<Restaurant> restaurants = JsonMapper.restaurantListMapper(ServerCommunication.getRestaurants());

    @FXML
    private TextField editName;
    @FXML
    private ChoiceBox<String> editLocation;
    @FXML
    private TextField selectedRestaurantId;
    @FXML
    private TextField findRestaurantByNameTextField;
    @FXML
    private ChoiceBox<String> findByLocationChoiceBox;
    @FXML
    private TextField findRestaurantByIdTextField;
    @FXML
    private TableView<Restaurant> table;
    @FXML
    private TableColumn<Restaurant,String> colId;
    @FXML
    private TableColumn<Restaurant,String> colName;
    @FXML
    private TableColumn<Restaurant,String> colBuilding;
    @FXML
    private TableColumn<Restaurant,String> colMenu;

    /**
     * runs when the scene is opened and check if there are buildings in the database
     */
    public DatabaseEditFoodRestaurantsController() {
        try {
            locations = JsonMapper.buildingListMapper(ServerCommunication.getBuildings());
        } catch (IOException e) {
            CustomAlert.warningAlert("There are no buildings in the database");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChoiceBoxes();
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colMenu.setCellValueFactory(new PropertyValueFactory<>("getMenuName"));
        loadTable();
    }

    private void loadChoiceBoxes() {
        for (Building location : locations) {
            findByLocationChoiceBox.getItems().add(location.getName());
            editLocation.getItems().add(location.getName());
        }
    }

    @FXML
    private void loadTable() {
        restaurants = JsonMapper.restaurantListMapper(ServerCommunication.getRestaurants());
        restaurantsToTable.clear();
        assert restaurants != null;
        restaurantsToTable.addAll(restaurants);
        table.setItems(restaurantsToTable);
    }

    private void loadTable(int id) {
        assert restaurants != null;
        restaurantsToTable.clear();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                restaurantsToTable.add(restaurant);
                break;
            }
        }
    }

    private void loadTable(ArrayList<Integer> id) {
        assert restaurants != null;
        restaurantsToTable.clear();
        for (Restaurant restaurant : restaurants) {
            if (id.contains(restaurant.getId())) {
                restaurantsToTable.add(restaurant);
            }
        }
    }

    /**
     * Selects the restaurant selected from the button to edit
     */
    public void editRestaurantFromTable() {
        try {
            Restaurant restaurant = table.getSelectionModel().getSelectedItem();
            loadTable(restaurant.getId());
            selectedRestaurantId.setText("" + restaurant.getId());
        } catch (NullPointerException e) {
            CustomAlert.warningAlert("Select a restaurant.");
        }
    }

    /**
     * Selects the restaurant selected from the button to edit
     */
    public void deleteRestaurantFromTable() {
        try {
            Restaurant restaurant = table.getSelectionModel().getSelectedItem();
            int id = restaurant.getId();
            System.out.println(id);
            CustomAlert.informationAlert(ServerCommunication.deleteRestaurant(id));
            loadTable();
        } catch (NullPointerException e) {
            CustomAlert.warningAlert("Select a restaurant.");
        }
    }

    /**
     * Loads the restaurant with the same id as the Id text filed into the table
     */
    public void findById() {
        int id;
        try {
            id = Integer.parseInt(findRestaurantByIdTextField.getText());
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Please use a number as an Id");
            return;
        }
        loadTable(id);
    }

    /**
     * Loads the restaurant with the same name as the name text filed into the table
     */
    public void findByName() {
        ArrayList<Integer> ids = new ArrayList<>();
        String name = findRestaurantByNameTextField.getText();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                ids.add(restaurant.getId());
            }
        }
        loadTable(ids);
    }

    /**
     * Loads the restaurant with the same location as the locationChoiceBox filed into the table
     */
    public void findByLocation() {
        ArrayList<Integer> ids = new ArrayList<>();
        String location = findByLocationChoiceBox.getValue();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getBuilding().getName().equals(location)) {
                ids.add(restaurant.getId());
            }
        }
        loadTable(ids);
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
     * Updates the selected room with the given name and building
     */
    public void change() {
        String name;
        if (!editName.getText().equals("")) {
            name = editName.getText();
        } else {
            CustomAlert.warningAlert("Add a name for the restaurant");
            return;
        }
        int buildingId = -1;
        int restaurantId = -1;
        try {
            restaurantId = Integer.parseInt(selectedRestaurantId.getText());
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Select restaurant from the table");
        }
        for (Building location : locations) {
            if (editLocation.getValue().equals(location.getName())) {
                buildingId = location.getId();
            }
        }
        String buildingIdString = "" + buildingId;
        String responseToUpdateName = ServerCommunication.updateRestaurant(restaurantId, "name", name);
        String responseToUpdateBuilding = ServerCommunication.updateRestaurant(restaurantId, "building", buildingIdString);
        if (responseToUpdateName.equals("Successfully added!") && responseToUpdateBuilding.equals("Successfully added!")) {
            CustomAlert.informationAlert("Successfully added!");
        } else if (!responseToUpdateName.equals("Successfully added!")) {
            CustomAlert.warningAlert(responseToUpdateName);
        } else {
            CustomAlert.warningAlert(responseToUpdateBuilding);
        }
        loadTable();
    }
}
