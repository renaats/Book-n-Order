package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
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
    private List<Building> buildings;

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
    private int buildingId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        table.setPlaceholder(new Label(""));

        idFieldRead.setDisable(true);
        pageNumber = 1;

        listBuildingsButtonClicked();
        tableSelectMethod();
        loadFacultyChoiceBox();
        loadDaysChoiceBox();
        daysChoiceBoxListener();
        datePickerListener();

        facultyChoiceBox.setValue("");
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
     * Retrieves all buttons on the database.
     */
    public void listBuildingsButtonClicked() {
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            buildings = new ArrayList<>();
        }
        calculatesTablePages();
    }

    /**
     * Calculates the right amount of results per page and how much pages there should be for table navigation
     */
    public void calculatesTablePages() {
        buildingResult.clear();

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

            // Takes care of filling in the fields
            final Building building = table.getSelectionModel().getSelectedItem();
            if (building != null) {
                buildingId = building.getId();
                idFieldRead.setText(Integer.toString(building.getId()));
                nameFieldRead.setText(building.getName());
                streetFieldRead.setText(building.getStreet());
                houseNumberFieldRead.setText(Integer.toString(building.getHouseNumber()));
                facultyChoiceBox.setValue(building.getFaculty());
            }

            // Takes care of showing where the delete button should be
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
        try {
            Building building = null;
            // Checks if the id provided actually exists
            try {
                building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(buildingId));
            } catch (JsonProcessingException e) {
                CustomAlert.errorAlert("Building not found!");
                return;
            }
            // Various checks if the other fields are in order and the values are able to be used
            assert building != null;
            if (nameFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a building name.");
                return;
            }
            if (!building.getName().equals(nameFieldRead.getText())) {
                String response = BuildingServerCommunication.updateBuilding(buildingId, "name", nameFieldRead.getText());
                if (response.equals("Name already exists.")) {
                    CustomAlert.warningAlert("Name already exists.");
                    return;
                }
            }
            if (streetFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a street name.");
                return;
            }
            if (!building.getStreet().equals(streetFieldRead.getText())) {
                BuildingServerCommunication.updateBuilding(buildingId, "street", streetFieldRead.getText());
            }
            try {
                if (!(building.getHouseNumber() == Integer.parseInt(houseNumberFieldRead.getText()))) {
                    BuildingServerCommunication.updateBuilding(buildingId, "houseNumber", houseNumberFieldRead.getText());
                }
            } catch (NumberFormatException e) {
                CustomAlert.warningAlert("House number has to be an integer.");
                return;
            }
            if ((building.getFaculty() == null) || !building.getFaculty().equals(facultyChoiceBox.getValue())) {
                BuildingServerCommunication.updateBuilding(buildingId, "faculty", facultyChoiceBox.getValue());
            }
            // Corresponds to the first try block, if it fails to parse the number it means there's no selection in the table
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
            CustomAlert.informationAlert(BuildingServerCommunication.deleteBuilding(buildingId));
            BuildingServerCommunication.deleteBuilding(buildingId);
            buildingResult.removeIf(b -> b.getId() == buildingId);
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

    /**
     * Loads the days into the choice box.
     */
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

    /**
     * Sets all fields to 0 to indicate that that means closed.
     */
    public void setClosedTextFields() {
        hoursStartTime.setText("0");
        minutesStartTime.setText("0");
        hoursEndTime.setText("0");
        minutesEndTime.setText("0");
    }

    /**
     * Sets the start and end time text fields.
     * @param buildingId building id.
     * @param day the day of the week represented in int (1 - 7)
     */
    public void setStartAndEndTimeTextFields(int buildingId, int day) {
        try {
            BuildingHours buildingHours = JsonMapper.buildingHoursMapper(BuildingServerCommunication.findBuildingHoursByDay(buildingId, day));
            LocalTime startTime = buildingHours.getStartTime();
            hoursStartTime.setText(Integer.toString(startTime.getHour()));
            minutesStartTime.setText(Integer.toString(startTime.getMinute()));
            LocalTime endTime = buildingHours.getEndTime();
            hoursEndTime.setText(Integer.toString(endTime.getHour()));
            minutesEndTime.setText(Integer.toString(endTime.getMinute()));
        } catch (JsonProcessingException e) {
            hoursStartTime.clear();
            minutesStartTime.clear();
            hoursEndTime.clear();
            minutesEndTime.clear();
        }
    }

    /**
     * Automatically updates the fields when the user interacts with the choicebox.
     */
    public void daysChoiceBoxListener() {
        daysChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final String dayName = daysChoiceBox.getSelectionModel().getSelectedItem();
            if (dayName != null) {
                datePicker.setValue(null);
            }
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

    /**
     * Sets the choice box to null if the user interacts with the date picker to prevent duplicate dates.
     */
    public void datePickerListener() {
        datePicker.valueProperty().addListener((obs) -> daysChoiceBox.setValue(null));
    }

    /**
     * Takes care of updating building hours
     */
    public void updateBuildingHours() {
        int day = 0;
        // Long list of if statements checking for various things such as
        // If all values are there, if the values are not out of bounds and if the values are viable to use.
        try {
            if (daysChoiceBox.getValue() == null && datePicker.getValue() == null) {
                CustomAlert.errorAlert("Please select either a day or a date.");
                daysChoiceBox.setValue(null);
                datePicker.setValue(null);
            } else if (idFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("No selection detected.");
            } else if (hoursEndTime.getText().isEmpty()
                    || minutesEndTime.getText().isEmpty()
                    || hoursStartTime.getText().isEmpty()
                    || hoursEndTime.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide opening and closing time.");
            } else if (Integer.parseInt(hoursStartTime.getText()) > 23 || Integer.parseInt(hoursEndTime.getText()) > 23) {
                CustomAlert.warningAlert("Hours cannot be larger than 23.");
            } else if (Integer.parseInt(minutesStartTime.getText()) > 59 || Integer.parseInt(minutesEndTime.getText()) > 59) {
                CustomAlert.warningAlert("Minutes cannot be larger than 59.");
            } else if (datePicker.getValue() == null) {
                // Switch case that turns the day string into a number
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
                try {
                    int startTime = Integer.parseInt(hoursStartTime.getText()) * 3600 + Integer.parseInt(minutesStartTime.getText()) * 60;
                    int endTime = Integer.parseInt(hoursEndTime.getText()) * 3600 + Integer.parseInt(minutesEndTime.getText()) * 60;
                    if (startTime > endTime) {
                        CustomAlert.errorAlert("Opening hours cannot be later than closing hours.");
                        return;
                    }
                    try {
                        // Checks if there are already building hours there, if there are not this generates a JsonProcessingException that
                        // that is then caught and instead of adding, we update the hours.
                        // This is put in place because there's no explicit add building hours button or page.
                        BuildingHours buildingHours = JsonMapper.buildingHoursMapper(
                                BuildingServerCommunication.findBuildingHoursByDay(buildingId, day));

                        String response = BuildingServerCommunication.updateBuildingHours(
                                buildingHours.getId(), "starttimes", Integer.toString(startTime));
                        if (!response.equals("Successfully executed.")) {
                            CustomAlert.errorAlert(response);
                            return;
                        }
                        response = BuildingServerCommunication.updateBuildingHours(buildingHours.getId(), "endtimes", Integer.toString(endTime));
                        if (!response.equals("Successfully executed.")) {
                            CustomAlert.errorAlert(response);
                            return;
                        }
                        CustomAlert.informationAlert("Successfully executed.");
                        // If exception, update building
                    } catch (JsonProcessingException e) {
                        CustomAlert.informationAlert(BuildingServerCommunication.addBuildingHours(buildingId, day, startTime, endTime));
                    }
                } catch (NumberFormatException ex) {
                    CustomAlert.warningAlert("Buildings hours have to be an integer.");
                }
                // If the day choice box is empty, take the date picker and update it that way.
            } else {
                LocalDate date = datePicker.getValue();
                Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
                long dateInMs = instant.toEpochMilli();
                try {
                    int startTime = Integer.parseInt(hoursStartTime.getText()) * 3600 + Integer.parseInt(minutesStartTime.getText()) * 60;
                    int endTime = Integer.parseInt(hoursEndTime.getText()) * 3600 + Integer.parseInt(minutesEndTime.getText()) * 60;
                    if (startTime > endTime) {
                        CustomAlert.errorAlert("Opening hours cannot be later than closing hours.");
                        return;
                    }
                    CustomAlert.informationAlert(BuildingServerCommunication.addBuildingHours(buildingId, dateInMs, startTime, endTime));
                } catch (NumberFormatException e) {
                    CustomAlert.warningAlert("Building hours have to be an integer.");
                }
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Building hours have to be an integer.");
        }
    }
}
