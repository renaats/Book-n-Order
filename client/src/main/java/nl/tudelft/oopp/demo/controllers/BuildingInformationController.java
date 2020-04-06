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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "BuildingInformation.fxml" file
 */
public class BuildingInformationController implements Initializable {

    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private TableView<Building> table;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colStreet;
    @FXML
    private TableColumn<Building, Integer> colHouseNumber;
    @FXML
    private Text buildingName;
    @FXML
    private Text buildingStreet;
    @FXML
    private Text buildingHouseNumber;
    @FXML
    private Text buildingFaculty;
    @FXML
    private Text buildingRoomBook;
    @FXML
    private Text buildingMondayFrom;
    @FXML
    private Text buildingMondayTo;
    @FXML
    private Text buildingTuesdayFrom;
    @FXML
    private Text buildingTuesdayTo;
    @FXML
    private Text buildingWednesdayFrom;
    @FXML
    private Text buildingWednesdayTo;
    @FXML
    private Text buildingThursdayFrom;
    @FXML
    private Text buildingThursdayTo;
    @FXML
    private Text buildingFridayFrom;
    @FXML
    private Text buildingFridayTo;
    @FXML
    private Text buildingSaturdayFrom;
    @FXML
    private Text buildingSaturdayTo;
    @FXML
    private Text buildingSundayFrom;
    @FXML
    private Text buildingSundayTo;
    @FXML
    private Text pagesText;

    private int pageNumber;
    private double totalPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));

        if (pageNumber == 0) {
            pageNumber++;
        }

        loadDataIntoTable();
        showInformation();
    }

    /**
     * Loads the building data into the table
     */
    public void loadDataIntoTable() {
        buildingResult.clear();
        List<Building> buildings;
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            buildings = new ArrayList<>();
            buildings.add(null);
        }

        totalPages = Math.ceil(buildings.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (buildings.size() > 16) {
            for (int i = 1; i < 16; i++) {
                try {
                    buildingResult.add(buildings.get((i - 15) + pageNumber * 15));
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
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        loadDataIntoTable();
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            loadDataIntoTable();
        }
    }

    /**
     * Listener that checks if a building is selected, and if so, fills in the JavaFX text with building information
     */
    public void showInformation() {
        try {
            Building chosenBuilding = table.getSelectionModel().getSelectedItem();
            if (chosenBuilding != null) {
                buildingName.setText(chosenBuilding.getName());
                buildingStreet.setText(chosenBuilding.getStreet());
                buildingHouseNumber.setText(Integer.toString(chosenBuilding.getHouseNumber()));
                buildingFaculty.setText(chosenBuilding.getFaculty());
                if (chosenBuilding.hasRooms()) {
                    buildingRoomBook.setText("Yes");
                } else {
                    buildingRoomBook.setText("No");
                }
                BuildingHours buildingHours = JsonMapper.buildingHoursMapper(BuildingServerCommunication.findBuilding(chosenBuilding.getId()));
                
            }
        } catch (NullPointerException e) {
            CustomAlert.warningAlert("Select a building to view its information.");
        }
    }

    /**
     * Changes to MainMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }
}
