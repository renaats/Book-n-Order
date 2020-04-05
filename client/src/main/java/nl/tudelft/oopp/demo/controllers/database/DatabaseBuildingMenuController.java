package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseBuildingMenu.fxml" file
 */
public class DatabaseBuildingMenuController implements Initializable {

    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();
    private final ObservableList<String> facultyList = FXCollections.observableArrayList();
    private final ObservableList<String> daysList = FXCollections.observableArrayList();

    @FXML
    private TextField idFieldRead;
    @FXML
    private TextField nameFieldRead;
    @FXML
    private TextField streetFieldRead;
    @FXML
    private TextField houseNumberFieldRead;
    @FXML
    private TextField buildingFindTextField;
    @FXML
    private Text pagesText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Building> table;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colStreet;
    @FXML
    private TableColumn<Building, Integer> colHouseNumber;
    @FXML
    private TableColumn<Building, Integer> colFaculty;
    @FXML
    private ChoiceBox<String> facultyChoiceBox;
    @FXML
    private ChoiceBox<String> daysChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hoursStartTime;
    @FXML
    private TextField minutesStartTime;
    @FXML
    private TextField hoursEndTime;
    @FXML
    private TextField minutesEndTime;

    private int pageNumber;
    private double totalPages;
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        idFieldRead.setDisable(true);
        pageNumber = 1;

        listBuildingsButtonClicked();
        tableSelectMethod();
        loadFacultyChoiceBox();
        loadDaysChoiceBox();
        daysChoiceBoxListener();

        daysChoiceBox.setValue("Monday");
    }

    /**
     * return to the database main menu when the home icon is clicked
     *
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     *
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        buildingResult.clear();
        List<Building> buildings = new ArrayList<>();
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            table.setPlaceholder(new Label(""));
        }

        totalPages = Math.ceil(buildings.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (buildings.size() > 15) {
            for (int i = 0; i < 15; i++) {
                try {
                    buildingResult.add(buildings.get((i - 15) + pageNumber * 15));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
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
            listBuildingsButtonClicked();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        listBuildingsButtonClicked();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Building building = table.getSelectionModel().getSelectedItem();
            if (building != null) {
                idFieldRead.setText(Integer.toString(building.getId()));
                nameFieldRead.setText(building.getName());
                streetFieldRead.setText(building.getStreet());
                houseNumberFieldRead.setText(Integer.toString(building.getHouseNumber()));
                facultyChoiceBox.setValue(building.getFaculty());
            }

            for (int i = 0; i < buildingResult.size(); i++) {
                assert building != null;
                if (buildingResult.get(i).getId().equals(building.getId())) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(1200);
                    deleteButton.setLayoutY(179 + (24 * (i + 1)));
                    deleteButton.setMinWidth(60);
                    deleteButton.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButton.setMinHeight(20);
                    deleteButton.setOnAction(event -> {
                        for (int i1 = 0; i1 < buildingResult.size(); i1++) {
                            if (buildingResult.get(i1).getId().equals(building.getId())) {
                                buildingResult.remove(buildingResult.get(i1));
                                anchorPane.getChildren().remove(deleteButton);
                            }
                        }
                        String response = BuildingServerCommunication.deleteBuilding(building.getId());
                        listBuildingsButtonClicked();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }

    /**
     * Takes care of finding a building by name or ID.
     */
    public void findBuilding() {
        try {
            int id = Integer.parseInt(buildingFindTextField.getText());
            Building building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(id));
            if (building != null) {
                buildingResult.clear();
                buildingResult.add(building);
                table.setItems(buildingResult);
                pagesText.setText("1 / 1 pages");
            }
        } catch (Exception e) {
            String name = buildingFindTextField.getText();
            Building building = null;
            try {
                building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(name));
            } catch (JsonProcessingException ex) {
                CustomAlert.errorAlert("Building not found!");
            }
            if (building != null) {
                buildingResult.clear();
                buildingResult.add(building);
                table.setItems(buildingResult);
                pagesText.setText("1 / 1 pages");
            }
        }
    }

    /**
     * Updates a building directly from the fields.
     */
    public void updateBuilding() {
        int id;
        try {
            id = Integer.parseInt(idFieldRead.getText());
            Building building = null;
            try {
                building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(id));
            } catch (JsonProcessingException e) {
                CustomAlert.errorAlert("Building not found!");
            }
            assert building != null;
            if (!building.getName().equals(nameFieldRead.getText())) {
                String response = BuildingServerCommunication.updateBuilding(id, "name", nameFieldRead.getText());
                if (response.equals("Name already exists.")) {
                    CustomAlert.warningAlert("Name already exists.");
                    return;
                }
            }
            if (!building.getStreet().equals(streetFieldRead.getText())) {
                BuildingServerCommunication.updateBuilding(id, "street", streetFieldRead.getText());
            }
            try {
                if (!(building.getHouseNumber() == Integer.parseInt(houseNumberFieldRead.getText()))) {
                    BuildingServerCommunication.updateBuilding(id, "houseNumber", houseNumberFieldRead.getText());
                }
            } catch (NumberFormatException e) {
                CustomAlert.warningAlert("House number has to be an integer.");
                return;
            }
            if ((building.getFaculty() == null) || !building.getFaculty().equals(facultyChoiceBox.getValue())) {
                BuildingServerCommunication.updateBuilding(id, "faculty", facultyChoiceBox.getValue());
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("No selection detected.");
            return;
        }
        CustomAlert.informationAlert("Successfully Executed.");
        listBuildingsButtonClicked();
    }

    /**
     * Handles clicking of the Remove Building button.
     */
    public void deleteBuilding() {
        try {
            int id = Integer.parseInt(idFieldRead.getText());
            CustomAlert.informationAlert(BuildingServerCommunication.deleteBuilding(id));
            BuildingServerCommunication.deleteBuilding(id);
            buildingResult.removeIf(b -> b.getId() == id);
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    private void loadFacultyChoiceBox() {
        facultyList.clear();
        String a = "Architecture and the Built Environment";
        String b = "Civil Engineering and Geosciences";
        String c = "Electrical Engineering, Mathematics & Computer Science";
        String d = "Industrial Design Engineering";
        String e = "Aerospace Engineering";
        String f = "Technology, Policy and Management";
        String g = "Applied Sciences";
        String h = "Mechanical, Maritime and Materials Engineering";
        String i = "";
        facultyList.addAll(a, b, c, d, e, f, g, h, i);
        facultyChoiceBox.getItems().addAll(facultyList);
    }

    private void loadDaysChoiceBox() {
        daysList.clear();
        String a = "Monday";
        String b = "Tuesday";
        String c = "Wednesday";
        String d = "Thursday";
        String e = "Friday";
        String f = "Saturday";
        String g = "Sunday";
        daysList.addAll(a, b, c, d, e, f, g);
        daysChoiceBox.getItems().addAll(daysList);
    }

    public void setStartAndEndTimeTextFields(int buildingId, int day) {
        System.out.println(buildingId + "  " + day);
        try {
            BuildingHours buildingHours = JsonMapper.buildingHoursMapper(BuildingServerCommunication.findBuildingHours(buildingId, day));
            LocalTime startime = buildingHours.getStartTime();
            hoursStartTime.setText(Integer.toString(startime.getHour()));
            minutesStartTime.setText(Integer.toString(startime.getMinute()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            hoursStartTime.clear();
            minutesStartTime.clear();
        }
    }

    public void daysChoiceBoxListener () {
        daysChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final String dayName = daysChoiceBox.getSelectionModel().getSelectedItem();
            if (!idFieldRead.getText().isEmpty()) {
                int buildingId = Integer.parseInt(idFieldRead.getText());
                int day = 0;
                if (dayName != null) {
                    switch (dayName) {
                        case "Monday":
                            day = 1;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Tuesday":
                            day = 2;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Wednesday":
                            day = 3;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Thursday":
                            day = 4;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Friday":
                            day = 5;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Saturday":
                            day = 6;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Sunday":
                            day = 7;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        default:
                            CustomAlert.errorAlert("Day not recognized.");
                            daysChoiceBox.setValue(null);
                    }
                }
            }
        });
    }


    public void updateBuildingHours() {
        int day = 0;
        int buildingId = 0;
        if (daysChoiceBox.getValue() != null && datePicker.getValue() != null || daysChoiceBox.getValue() == null && datePicker.getValue() == null) {
            CustomAlert.errorAlert("Please select either a day or a date.");
            daysChoiceBox.setValue(null);
            datePicker.setValue(null);
        } else if (idFieldRead.getText().isEmpty()) {
            CustomAlert.warningAlert("No selection detected.");
        } else if (hoursEndTime.getText().isEmpty() || minutesEndTime.getText().isEmpty() || hoursStartTime.getText().isEmpty() || hoursEndTime.getText().isEmpty()) {
            CustomAlert.warningAlert("Please provide opening and closing time.");
        } else {
            buildingId = Integer.parseInt(idFieldRead.getText());
            switch (daysChoiceBox.getValue()) {
                case "Monday":
                    day = 1;
                    break;
                case "Tuesday":
                    day = 2;
                    break;
                case "Wednesday":
                    day = 3;
                    break;
                case "Thursday":
                    day = 4;
                    break;
                case "Friday":
                    day = 5;
                    break;
                case "Saturday":
                    day = 6;
                    break;
                case "Sunday":
                    day = 7;
                    break;
                default:
                    CustomAlert.errorAlert("Day not recognized.");
                    daysChoiceBox.setValue(null);
            }
            int startTime = Integer.parseInt(hoursStartTime.getText()) * 3600 + Integer.parseInt(minutesStartTime.getText()) * 60;
            int endTime = Integer.parseInt(hoursEndTime.getText()) * 3600 + Integer.parseInt(minutesEndTime.getText()) * 60;
            try {
                BuildingHours buildingHours = JsonMapper.buildingHoursMapper(
                        BuildingServerCommunication.findBuildingHours(buildingId, day));
                if (startTime < endTime) {
                   CustomAlert.errorAlert("Opening hours cannot be later than closing hours.");
                   return;
                }
                String response = BuildingServerCommunication.updateBuildingHours(buildingId, "starttimes", Integer.toString(startTime));
                if (!response.equals("Successfully executed.")) {
                    CustomAlert.errorAlert(response);
                    return;
                }
                response = BuildingServerCommunication.updateBuildingHours(buildingId, "endtimes", Integer.toString(endTime));
                if (!response.equals("Successfully executed.")) {
                    CustomAlert.errorAlert(response);
                    return;
                }
                CustomAlert.informationAlert("Successfully executed.");
            } catch (JsonProcessingException e) {
                CustomAlert.informationAlert(BuildingServerCommunication.addBuildingHours(buildingId, day, startTime, endTime));
            }
        }
    }
}
