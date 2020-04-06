package nl.tudelft.oopp.demo.controllers.bikes;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "BikeReservations.fxml" file
 */
public class BikeReservationController implements Initializable {
    private final ObservableList<String> listHours = FXCollections.observableArrayList();
    private final ObservableList<String> listMinutes = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();

    @FXML
    private DatePicker pickUpDate;
    @FXML
    private ComboBox<String> pickUpTimeH;
    @FXML
    private ComboBox<String> pickUpTimeMin;
    @FXML
    private ChoiceBox<String> pickUpLocation;
    @FXML
    private DatePicker dropOffDate;
    @FXML
    private ComboBox<String> dropOffTimeH;
    @FXML
    private ComboBox<String> dropOffTimeMin;
    @FXML
    private ChoiceBox<String> dropOffLocation;

    /**
     * Changes to MainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * Go to the confirmation screen when the reserve button is clicked.
     */
    public void bikeConfirmation() {
        if (pickUpDate.getValue() == null) {
            CustomAlert.warningAlert("Pick up date is required.");
        } else if (pickUpTimeH.getValue() == null || pickUpTimeMin.getValue() == null) {
            CustomAlert.warningAlert("Pick up time is required.");
        } else if (pickUpLocation.getValue() == null) {
            CustomAlert.warningAlert("Pick up location is required.");
        } else if (dropOffDate.getValue() == null) {
            CustomAlert.warningAlert("Drop off date is required.");
        } else if (dropOffTimeH.getValue() == null || dropOffTimeMin.getValue() == null) {
            CustomAlert.warningAlert("Drop off time is required.");
        } else if (dropOffLocation.getValue() == null) {
            CustomAlert.warningAlert("Drop off location is required.");
        } else {
            long fromDateLong = Date.from(pickUpDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()
                    + Long.parseLong(pickUpTimeH.getValue()) * 3600000 + Long.parseLong(pickUpTimeMin.getValue()) * 60000;
            long toDateLong = Date.from(dropOffDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()
                    + Long.parseLong(dropOffTimeH.getValue()) * 3600000 + Long.parseLong(dropOffTimeMin.getValue()) * 60000;
            if (fromDateLong > toDateLong) {
                CustomAlert.warningAlert("Pick up time must be before the drop off time.");
                return;
            } else if (fromDateLong < new Date().getTime()) {
                CustomAlert.warningAlert("Pick up time cannot be in the past.");
                return;
            }
            try {
                int fromBuildingId = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(pickUpLocation.getValue())).getId();
                int toBuildingId = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(dropOffLocation.getValue())).getId();
                String response = BikeServerCommunication.addBikeReservation(fromBuildingId, toBuildingId, fromDateLong, toDateLong);
                if (response.equals(ErrorMessages.getErrorMessage(201))) {
                    int[] ints = {fromBuildingId, toBuildingId};
                    long[] longs = {fromDateLong, toDateLong};
                    ApplicationDisplay.changeSceneWithVariables("/BikeConfirmation.fxml", ints, longs);
                    return;
                }
                CustomAlert.errorAlert(response);
            } catch (Exception e) {
                CustomAlert.errorAlert("Something went wrong.");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        loadLocationChoiceBox();
    }

    private void loadData() {
        listHours.clear();
        listMinutes.clear();
        for (int i = 0;i <= 45; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(((Integer)i).toString());
            }
        }
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                listHours.add("0" + i);
            } else {
                listHours.add("" + i);
            }
        }
        pickUpTimeH.getItems().addAll(listHours);
        dropOffTimeH.getItems().addAll(listHours);
        pickUpTimeMin.getItems().addAll(listMinutes);
        dropOffTimeMin.getItems().addAll(listMinutes);
    }

    /**
     * Takes care of the options for the locationChoiceBox in the GUI
     */
    private void loadLocationChoiceBox() {
        buildingNameList.clear();
        try {
            for (Building building: Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings()))) {
                buildingNameList.add(building.getName());
            }
            buildingNameList.add(null);
        } catch (Exception e) {
            buildingNameList.add(null);
        }
        pickUpLocation.getItems().addAll(buildingNameList);
        dropOffLocation.getItems().addAll(buildingNameList);
    }
}
