package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseAddRestaurants implements Initializable {
    private final ObservableList<Building> buildings = FXCollections.observableArrayList();
    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private Text buildingPagesText;
    @FXML
    private TextField restaurantNameTextField;
    @FXML
    private TextField buildingNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TableView<Building> buildingTable;
    @FXML
    private TableColumn<Building, Integer> colBuildingId;
    @FXML
    private TableColumn<Building, String> colBuildingName;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button nextBuildingPageButton;
    @FXML
    private Button previousBuildingPageButton;
    @FXML
    private ToggleButton buildingsTableToggle;

    private boolean buildingsTableToggleFlag;
    private int buildingPageNumber;
    private double totalBuildingPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBuildingId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBuildingName.setCellValueFactory(new PropertyValueFactory<>("name"));

        anchorPane.getChildren().remove(buildingTable);
        anchorPane.getChildren().remove(nextBuildingPageButton);
        anchorPane.getChildren().remove(previousBuildingPageButton);
        anchorPane.getChildren().remove(buildingPagesText);

        buildingPageNumber = 1;

        retrieveAllBuildings();
        tableSelectMethod();
    }

    /**
     * Returns to the main database menu
     *
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Handles retrieving all buildings and loading them into the table.
     */
    public void retrieveAllBuildings() {
        buildingResult.clear();
        List<Building> buildings = new ArrayList<>();
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            buildingTable.setPlaceholder(new Label(""));
        }

        totalBuildingPages = Math.ceil(buildings.size() / 7.0);

        if (totalBuildingPages < buildingPageNumber) {
            buildingPageNumber--;
        }

        buildingPagesText.setText(buildingPageNumber + " / " + (int) totalBuildingPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 0; i < 7; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + buildingPageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            buildingResult.addAll(buildings);
        }
        buildingTable.setItems(buildingResult);
    }

    /**
     * Goes to the edit restaurant view
     *
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickBuildingsTable() {
        if (buildingsTableToggleFlag) {
            buildingsTableToggle.setText("Show");
            anchorPane.getChildren().removeAll(buildingTable, previousBuildingPageButton, nextBuildingPageButton, buildingPagesText);
        } else {
            buildingsTableToggle.setText(" Hide");
            anchorPane.getChildren().addAll(buildingTable, previousBuildingPageButton, nextBuildingPageButton, buildingPagesText);
        }
        buildingsTableToggleFlag = !buildingsTableToggleFlag;
    }

    /**
     * Handles the clicking to the next building page.
     */
    public void nextBuildingPage() {
        if (buildingPageNumber < (int) totalBuildingPages) {
            buildingPageNumber++;
            retrieveAllBuildings();
        }
    }

    /**
     * Handles the clicking to the previous building page
     */
    public void previousBuildingPage() {
        if (buildingPageNumber > 1) {
            buildingPageNumber--;
        }
        retrieveAllBuildings();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        buildingTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final Building building = buildingTable.getSelectionModel().getSelectedItem();
            if (building != null) {
                buildingNameTextField.setText(building.getName());
            }
        });
    }

    /**
     * Adds a restaurant to the database.
     */
    public void addRestaurant() {
        int buildingId = -1;
        boolean buildingFound = false;

        String restaurantName = restaurantNameTextField.getText();
        if (restaurantName.equals("")) {
            CustomAlert.warningAlert("Please provide a restaurant name.");
        } else {

            try {
                buildingId = Integer.parseInt(buildingNameTextField.getText());
                JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(buildingId));
                buildingFound = true;
            } catch (NumberFormatException | JsonProcessingException e) {
                Building building = null;
                if (!buildingNameTextField.getText().equals("")) {
                    try {
                        building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(buildingNameTextField.getText()));
                    } catch (JsonProcessingException ex) {
                        CustomAlert.errorAlert("Building not found.");
                        return;
                    }
                } else {
                    CustomAlert.warningAlert("Please provide a building.");
                    return;
                }
                if (building == null) {
                    CustomAlert.errorAlert("Building not found.");
                    return;
                } else {
                    buildingId = building.getId();
                    buildingFound = true;
                }
            }

            String ownerEmail = emailTextField.getText();
            if (ownerEmail.equals("")) {
                CustomAlert.warningAlert("Please provide a restaurant name.");
                return;
            }

            String response = RestaurantServerCommunication.addRestaurant(buildingId, restaurantName, ownerEmail);
            if (response.equals("Successfully added!") && buildingFound) {
                CustomAlert.informationAlert(response);
            } else if (buildingFound) {
                CustomAlert.errorAlert(response);
            }
        }
    }
}
