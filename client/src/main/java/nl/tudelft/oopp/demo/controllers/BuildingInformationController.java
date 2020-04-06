package nl.tudelft.oopp.demo.controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
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
    private Text buildingMonday;
    @FXML
    private Text buildingTuesday;
    @FXML
    private Text buildingWednesday;
    @FXML
    private Text buildingThursday;
    @FXML
    private Text buildingFriday;
    @FXML
    private Text buildingSaturday;
    @FXML
    private Text buildingSunday;
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
            table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
                final Building chosenBuilding = table.getSelectionModel().getSelectedItem();
                // Building chosenBuilding = table.getSelectionModel().getSelectedItem();
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

                    BuildingHours buildingHoursMonday = null;
                    try {
                        buildingHoursMonday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 1));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingMonday.setText("");
                    buildingMonday.setText(buildingHoursMonday.getStartTime().toString() + " - " + buildingHoursMonday.getEndTime().toString());
                    BuildingHours buildingHoursTuesday = null;
                    try {
                        buildingHoursTuesday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 2));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingTuesday.setText("");
                    buildingTuesday.setText(buildingHoursTuesday.getStartTime().toString() + " - " + buildingHoursTuesday.getEndTime().toString());
                    BuildingHours buildingHoursWednesday = null;
                    try {
                        buildingHoursWednesday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 3));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingWednesday.setText("");
                    buildingWednesday.setText(buildingHoursWednesday.getStartTime().toString() + " - " + buildingHoursWednesday.getEndTime()
                            .toString());
                    BuildingHours buildingHoursThursday = null;
                    try {
                        buildingHoursThursday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 4));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingThursday.setText("");
                    buildingThursday.setText(buildingHoursThursday.getStartTime().toString() + " - " + buildingHoursThursday.getEndTime().toString());
                    BuildingHours buildingHoursFriday = null;
                    try {
                        buildingHoursFriday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 5));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingFriday.setText("");
                    buildingFriday.setText(buildingHoursFriday.getStartTime().toString() + " - " + buildingHoursFriday.getEndTime().toString());
                    BuildingHours buildingHoursSaturday = null;
                    try {
                        buildingHoursSaturday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 6));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingSaturday.setText("");
                    buildingSaturday.setText(buildingHoursSaturday.getStartTime().toString() + " - " + buildingHoursSaturday.getEndTime().toString());
                    BuildingHours buildingHoursSunday = null;
                    try {
                        buildingHoursSunday = JsonMapper.buildingHoursMapper(BuildingServerCommunication
                                .findBuildingHoursByDay(chosenBuilding.getId(), 7));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    buildingSunday.setText("");
                    buildingSunday.setText(buildingHoursSunday.getStartTime().toString() + " - " + buildingHoursSunday.getEndTime().toString());
                }
            });
        } catch (NullPointerException e) {
            if (buildingMonday.getText().equals("") || buildingTuesday.getText().equals("") || buildingWednesday.getText().equals("")
                    || buildingThursday.getText().equals("") || buildingFriday.getText().equals("")
                    || buildingSaturday.getText().equals("") || buildingSunday.getText().equals("")) {
                CustomAlert.warningAlert("(Some) building hours are unknown.");
                return;
            }
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