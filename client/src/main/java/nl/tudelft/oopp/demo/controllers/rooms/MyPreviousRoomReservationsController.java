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
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MyPreviousRoomBookings.fxml" file
 */
public class MyPreviousRoomReservationsController implements Initializable {

    private final ObservableList<RoomReservation> roomOrderResult = FXCollections.observableArrayList();

    @FXML
    private TableView<RoomReservation> table;
    @FXML
    private TableColumn<RoomReservation, String> colRoom;
    @FXML
    private TableColumn<RoomReservation, Integer> colFromTime;
    @FXML
    private TableColumn<RoomReservation, Long> colToTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            roomReservations = new ArrayList<>(JsonMapper.roomReservationsListMapper(RoomServerCommunication.getAllPreviousRoomReservations()));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            roomReservations = new ArrayList<>();
            roomReservations.add(null);
        }

        Collections.sort(roomReservations);
        Collections.reverse(roomReservations);

        if (roomReservations.size() > 10) {
            roomReservations = roomReservations.subList(0, 10);
            roomOrderResult.addAll(roomReservations);
        } else {
            roomOrderResult.addAll(roomReservations);
        }
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
     * Changes view to the my Previous bookings view when the back arrow is clicked.
     * @throws IOException should never be thrown as the input is always the same
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }
}
