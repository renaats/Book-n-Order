package nl.tudelft.oopp.demo.controllers.rooms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.controllers.BookRoomCalendarController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.SelectedRoom;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bookRoom.fxml" file
 */
public class BookRoomController implements Initializable {
    private final ObservableList<Room> roomResult = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();

    @FXML
    private CheckBox screen;
    @FXML
    private CheckBox beamer;
    @FXML
    private TextField capacityFrom;
    @FXML
    private TextField capacityTo;
    @FXML
    private TextField plugsFrom;
    @FXML
    private TextField plugsTo;
    @FXML
    private TextField name;
    @FXML
    private Text pagesText;
    @FXML
    private TableView<Room> table;
    @FXML
    private TableColumn<Room, String> colName;
    @FXML
    private TableColumn<Room, Building> colBuilding;
    @FXML
    private TableColumn<Room, Boolean> colBeamer;
    @FXML
    private TableColumn<Room, Boolean> colScreen;
    @FXML
    private TableColumn<Room, Integer> colCapacity;
    @FXML
    private TableColumn<Room, Integer> colPlugs;
    @FXML
    private ChoiceBox<String> buildingChoiceBox;
    @FXML
    private AnchorPane anchorPane;

    private int pageNumber;
    private double totalPages;

    private Room selectedRoom;
    private List<Room> rooms;
    private Button reserveButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colBeamer.setCellValueFactory(new PropertyValueFactory<>("projector"));
        colScreen.setCellValueFactory(new PropertyValueFactory<>("screen"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colPlugs.setCellValueFactory(new PropertyValueFactory<>("plugs"));

        if (pageNumber == 0) {
            pageNumber++;
        }

        applyFilters();
        loadBuildingChoiceBox();
    }

    /**
     * Changes to mainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToRoomCalendar() throws IOException {
        SelectedRoom.setSelectedRoom(selectedRoom.getId());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/book_room_Calendar.fxml"));
        Parent root = loader.load();

        ApplicationDisplay.getPrimaryStage().setScene(new Scene(root));
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    /**
     * Handles clicking the list button.
     */
    public void listRoomsButtonClicked(String filterString) {
        try {
            rooms = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper(ServerCommunication.filterRooms(filterString))));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            rooms = new ArrayList<>();
            rooms.add(null);
        }
        calculatePages();
        tableSelectMethod();
    }

    /**
     * Calculates the number of pages.
     */
    public void calculatePages() {
        roomResult.clear();
        totalPages = Math.ceil(rooms.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (rooms.size() > 15) {
            for (int i = 0; i < 15; i++) {
                try {
                    roomResult.add(rooms.get((i - 15) + pageNumber * 15));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            roomResult.addAll(rooms);
        }
        table.setItems(roomResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            calculatePages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        calculatePages();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(reserveButton);

            final Room room = table.getSelectionModel().getSelectedItem();

            for (int i = 0; i < roomResult.size(); i++) {
                assert room != null;
                if (roomResult.get(i).getId().equals(room.getId())) {
                    reserveButton = new Button("Reserve");
                    reserveButton.setLayoutX(1180);
                    reserveButton.setLayoutY(186 + (24  * (i + 1)));
                    reserveButton.setMinWidth(75);
                    reserveButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    reserveButton.setMaxHeight(24);
                    reserveButton.setOnAction(event -> {
                        try {
                            setSelectedRoom(room);
                            System.out.println(room.getId() + "in tableSetMethod");
                            goToRoomCalendar();
                        } catch (IOException | IllegalStateException e) {
                            e.printStackTrace();
                        }
                    });
                    anchorPane.getChildren().add(reserveButton);
                }
            }
        });
    }

    /**
     * Applies the selected filters
     */
    public void applyFilters() {
        String filterString = "";
        if (buildingChoiceBox.getValue() != null) {
            try {
                filterString += ",building:"
                       + JsonMapper.buildingMapper(ServerCommunication.findBuildingByName(buildingChoiceBox.getValue())).getId();
            } catch (Exception e) {
                CustomAlert.errorAlert("Building not found!");
            }
        }
        if (screen.isSelected()) {
            filterString += ",screen:true";
        }
        if (beamer.isSelected()) {
            filterString += ",projector:true";
        }
        if (!capacityFrom.getText().equals("")) {
            filterString += ",capacity>" + capacityFrom.getText();
        }
        if (!capacityTo.getText().equals("")) {
            filterString += ",capacity<" + capacityTo.getText();
        }
        if (!plugsFrom.getText().equals("")) {
            filterString += ",plugs>" + plugsFrom.getText();
        }
        if (!plugsTo.getText().equals("")) {
            filterString += ",plugs<" + plugsTo.getText();
        }
        if (!name.getText().equals("")) {
            filterString += ",name:" + name.getText();
        }
        if (filterString.equals("")) {
            filterString = ",";
        }
        listRoomsButtonClicked(filterString.substring(1));
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    private void loadBuildingChoiceBox() {
        buildingNameList.clear();
        try {
            for (Building building: Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings()))) {
                buildingNameList.add(building.getName());
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        buildingNameList.add(null);
        buildingChoiceBox.getItems().addAll(buildingNameList);
    }
}
