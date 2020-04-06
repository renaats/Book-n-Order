package nl.tudelft.oopp.demo.controllers.rooms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MyCurrentRoomReservations.fxml" file
 */
public class MyCurrentRoomReservationsController implements Initializable {

    private final ObservableList<RoomReservation> roomOrderResult = FXCollections.observableArrayList();

    @FXML
    private TableView<RoomReservation> table;
    @FXML
    private TableColumn<RoomReservation, String> colRoom;
    @FXML
    private TableColumn<RoomReservation,String> colBuilding;
    @FXML
    private TableColumn<RoomReservation, Integer> colFromTime;
    @FXML
    private TableColumn<RoomReservation, Long> colToTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("getRoomName"));
        colToTime.setCellValueFactory(new PropertyValueFactory<>("getToTime"));
        colFromTime.setCellValueFactory(new PropertyValueFactory<>("getFromTime"));
        loadDataIntoTable();
    }

    /**
     * Loads the previous room reservations.
     */
    public void loadDataIntoTable() {

        roomOrderResult.clear();
        List<RoomReservation> roomReservations;
        try {
            String json = RoomServerCommunication.getAllFutureRoomReservations();
            List<RoomReservation> roomReservations1 = JsonMapper.roomReservationsListMapper(json);
            roomReservations = new ArrayList<>(roomReservations1);

            Collections.sort(roomReservations);
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            roomReservations = new ArrayList<>();
            roomReservations.add(null);
        }
        roomOrderResult.addAll(roomReservations);
        table.setItems(roomOrderResult);
    }

    /**
     * Change the view to the main menu when the home icon is clicked.
     * @throws IOException should never be thrown as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * changes view to the user's current bookings view when the back arrow is clicked
     * @throws IOException should never throw an exception
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }
}
