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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "myPreviousTenBikeReservations.fxml" file.
 */
public class MyCurrentBikeReservationsController implements Initializable {

    private final ObservableList<BikeReservation> bikeOrderResult = FXCollections.observableArrayList();

    @FXML
    private TableView<BikeReservation> table;
    @FXML
    private TableColumn<BikeReservation, String> colFromTime;
    @FXML
    private TableColumn<BikeReservation, Integer> colToTime;
    @FXML
    private TableColumn<BikeReservation, Integer> colFromLoc;
    @FXML
    private TableColumn<BikeReservation, Long> colToLoc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFromTime.setCellValueFactory(new PropertyValueFactory<>("getFromTime"));
        colToTime.setCellValueFactory(new PropertyValueFactory<>("getToTime"));
        colFromLoc.setCellValueFactory(new PropertyValueFactory<>("getFromBuildingName"));
        colToLoc.setCellValueFactory(new PropertyValueFactory<>("getToBuildingName"));
        loadDataIntoTable();
    }

    /**
     * Loads the previous bike reservations.
     */
    public void loadDataIntoTable() {

        bikeOrderResult.clear();
        List<BikeReservation> bikeReservations;
        try {
            String json = BikeServerCommunication.getAllPreviousBikeReservations();
            List<BikeReservation> bikeReservations1 = JsonMapper.bikeReservationsListMapper(json);
            bikeReservations = new ArrayList<>(bikeReservations1);
            for (int i = 0; i < bikeReservations.size(); i++) {
                if (!(bikeReservations.get(i).getAppUser().getEmail()
                        .equals(JsonMapper.appUserMapper(UserServerCommunication.getOwnUserInformation()).getEmail()))) {
                    bikeReservations.remove(bikeReservations.get(i));
                }
            }
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            bikeReservations = new ArrayList<>();
            bikeReservations.add(null);
        }

        if (bikeReservations.size() > 10) {
            bikeReservations = bikeReservations.subList(0, 15);
            bikeOrderResult.addAll(bikeReservations);
        } else {
            bikeOrderResult.addAll(bikeReservations);
        }
        table.setItems(bikeOrderResult);
    }

    /**
     * Change the view to the main menu when the home icon is clicked.
     * @throws IOException should never be thrown as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes view to the my Previous bookings view when the back arrow is clicked.
     * @throws IOException should never be thrown as the input is always the same
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }
}
