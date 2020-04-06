package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseViewRoomReservations.fxml" file
 */
public class DatabaseRoomReservationController implements Initializable {

    private final ObservableList<RoomReservation> currentResult = FXCollections.observableArrayList();
    private final ObservableList<RoomReservation> pastResult = FXCollections.observableArrayList();
    private final ObservableList<Room> roomResult = FXCollections.observableArrayList();
    private List<RoomReservation> currentOrders;
    private List<RoomReservation> pastOrders;
    private List<Room> rooms;
    private Room selectedRoom;

    @FXML
    private TableView<RoomReservation> currentTable;
    @FXML
    private TableView<RoomReservation> pastTable;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<RoomReservation, String> colCurrentFrom;
    @FXML
    private TableColumn<RoomReservation, String> colCurrentTo;
    @FXML
    private TableColumn<RoomReservation, String> colPastFrom;
    @FXML
    private TableColumn<RoomReservation, String> colPastTo;
    @FXML
    private TableColumn<Room, String> colRoomName;
    @FXML
    private TableColumn<Room, String> colRoomBuilding;
    @FXML
    private Text currentPagesText;
    @FXML
    private Text pastPagesText;
    @FXML
    private Text roomPagesText;
    @FXML
    private AnchorPane anchorPane;

    private int currentPageNumber;
    private double totalCurrentPages;
    private int pastPageNumber;
    private double totalPastPages;
    private int roomPageNumber;
    private double totalRoomPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCurrentFrom.setCellValueFactory(new PropertyValueFactory<>("fromTimeString"));
        colCurrentTo.setCellValueFactory(new PropertyValueFactory<>("toTimeString"));
        colPastFrom.setCellValueFactory(new PropertyValueFactory<>("fromTimeString"));
        colPastTo.setCellValueFactory(new PropertyValueFactory<>("toTimeString"));
        colRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRoomBuilding.setCellValueFactory(new PropertyValueFactory<>("buildingName"));

        currentTable.setPlaceholder(new Label(""));
        pastTable.setPlaceholder(new Label(""));
        roomTable.setPlaceholder(new Label(""));

        if (currentPageNumber == 0) {
            currentPageNumber++;
        }
        if (pastPageNumber == 0) {
            pastPageNumber++;
        }
        if (roomPageNumber == 0) {
            roomPageNumber++;
        }
        addListeners();
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Returns to the room database menu
     * @throws IOException Should never throw the exception
     */
    public void goToRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Loads all orders from the database.
     */
    public void loadAllOrders() {
        try {
            currentOrders = JsonMapper.roomReservationsListMapper(RoomServerCommunication.getAllFutureRoomReservationsForRoom(selectedRoom.getId()));
        } catch (Exception e) {
            currentOrders = new ArrayList<>();
        }
        currentOrders.sort(Comparator.comparing(RoomReservation::getFromTimeString));
        try {
            pastOrders = JsonMapper.roomReservationsListMapper(RoomServerCommunication.getAllPreviousRoomReservationsForRoom(selectedRoom.getId()));
        } catch (Exception e) {
            pastOrders = new ArrayList<>();
        }
        pastOrders.sort(Comparator.comparing(RoomReservation::getFromTimeString).reversed());
        calculateCurrentPages();
        calculatePastPages();
    }

    /**
     * Calculates how many current pages there should be for browsing the table
     */
    public void calculateCurrentPages() {
        currentResult.clear();
        totalCurrentPages = Math.ceil(currentOrders.size() / 10.0);
        if (totalCurrentPages < currentPageNumber) {
            currentPageNumber--;
        }
        if (totalCurrentPages > 0) {
            currentPageNumber = Math.max(currentPageNumber, 1);
        }
        currentPagesText.setText(currentPageNumber + " / " + (int) totalCurrentPages + " pages");
        if (currentOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    currentResult.add(currentOrders.get((i - 10) + currentPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            currentResult.addAll(currentOrders);
        }
        currentTable.setItems(currentResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextCurrentPage() {
        if (currentPageNumber < (int) totalCurrentPages) {
            currentPageNumber++;
            calculateCurrentPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousCurrentPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
        }
        calculateCurrentPages();
    }

    /**
     * Calculates how many past pages there should be for browsing the table
     */
    public void calculatePastPages() {
        pastResult.clear();
        totalPastPages = Math.ceil(pastOrders.size() / 10.0);
        if (totalPastPages < pastPageNumber) {
            pastPageNumber--;
        }
        if (totalPastPages > 0) {
            pastPageNumber = Math.max(pastPageNumber, 1);
        }
        pastPagesText.setText(pastPageNumber + " / " + (int) totalPastPages + " pages");
        if (pastOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    pastResult.add(pastOrders.get((i - 10) + pastPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            pastResult.addAll(pastOrders);
        }
        pastTable.setItems(pastResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPastPage() {
        if (pastPageNumber < (int) totalPastPages) {
            pastPageNumber++;
            calculatePastPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPastPage() {
        if (pastPageNumber > 1) {
            pastPageNumber--;
        }
        calculatePastPages();
    }

    /**
     * Adds listeners to the order tables.
     */
    public void addListeners() {
        try {
            rooms = JsonMapper.roomListMapper(RoomServerCommunication.filterRooms(""));
        } catch (Exception e) {
            rooms = new ArrayList<>();
        }
        calculateRoomPages();
        roomTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (roomTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            selectedRoom = roomTable.getSelectionModel().getSelectedItem();
            loadAllOrders();
        });
    }

    /**
     * Calculates how many room pages there should be for browsing the table
     */
    public void calculateRoomPages() {
        totalRoomPages = Math.ceil(rooms.size() / 10.0);
        if (totalRoomPages < roomPageNumber) {
            roomPageNumber--;
        }
        if (totalRoomPages > 0) {
            roomPageNumber = Math.max(roomPageNumber, 1);
        }
        roomPagesText.setText(roomPageNumber + " / " + (int) totalRoomPages + " pages");
        if (rooms.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    roomResult.add(rooms.get((i - 10) + roomPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            roomResult.addAll(rooms);
        }
        roomTable.setItems(roomResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextRoomPage() {
        if (roomPageNumber < (int) totalRoomPages) {
            roomPageNumber++;
            calculateRoomPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousRoomPage() {
        if (roomPageNumber > 1) {
            roomPageNumber--;
        }
        calculateRoomPages();
    }
}
