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

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseAddBikes.fxml" file
 */
public class DatabaseAddBikeController implements Initializable {

    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private ToggleButton availableToggle;
    @FXML
    private ToggleButton buildingsTableToggle;
    @FXML
    private Button previousPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField buildingIdTextField;
    @FXML
    private Text pagesText;
    @FXML
    private TableView<Building> table;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<Building, Integer> colId;
    @FXML
    private TableColumn<Building, String> colName;

    private boolean availableToggleFlag;
    private boolean buildingsTableToggleFlag;
    private int pageNumber;
    private double totalPages;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  {@code null} if the resource is not found.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        pageNumber = 1;

        anchorPane.getChildren().removeAll(table, previousPageButton, nextPageButton, pagesText);

        retrieveAllBuildings();
        tableSelectMethod();
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickAvailable() {
        if (availableToggleFlag) {
            availableToggle.setText("false");

        } else {
            availableToggle.setText("true");
        }
        availableToggleFlag = !availableToggleFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickBuildingsTable() {
        if (buildingsTableToggleFlag) {
            buildingsTableToggle.setText("Show");
            anchorPane.getChildren().removeAll(table, previousPageButton, nextPageButton, pagesText);
        } else {
            buildingsTableToggle.setText(" Hide");
            anchorPane.getChildren().addAll(table, previousPageButton, nextPageButton, pagesText);
        }
        buildingsTableToggleFlag = !buildingsTableToggleFlag;
    }

    /**
     * Adds a bike to the database
     */
    public void databaseAddBike() {
        int buildingId = -1;
        boolean buildingFound = false;
        Building building = null;

        try {
            buildingId = Integer.parseInt(buildingIdTextField.getText());
            building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(buildingId));
            buildingFound = true;
        } catch (NumberFormatException | JsonProcessingException e) {
            if (!buildingIdTextField.getText().equals("")) {
                try {
                    building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(buildingIdTextField.getText()));
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

        boolean available = Boolean.parseBoolean(availableToggle.getText());
        String response = BikeServerCommunication.addBike(buildingId, available);
        if (response.equals("Successfully added!") && buildingFound) {
            CustomAlert.informationAlert(response);
        } else if (buildingFound) {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseBikeMenu.fxml view
     * @throws IOException the input will always be correct, so it should never throw an exception.
     */
    public void goToBikeMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBikeMenu.fxml");
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveAllBuildings() {
        buildingResult.clear();
        List<Building> buildings = new ArrayList<>();
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            table.setPlaceholder(new Label(""));
        }

        totalPages = Math.ceil(buildings.size() / 7.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 0; i < 7; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + pageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            buildingResult.addAll(buildings);
        }
        table.setItems(buildingResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            retrieveAllBuildings();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllBuildings();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final Building building = table.getSelectionModel().getSelectedItem();
            if (building != null) {
                buildingIdTextField.setText(building.getName());
            }
        });
    }
}
