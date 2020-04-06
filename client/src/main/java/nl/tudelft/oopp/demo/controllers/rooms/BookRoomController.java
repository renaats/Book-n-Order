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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.SelectedRoom;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bookRoom.fxml" file
 */
public class BookRoomController implements Initializable {

    private final ObservableList<Room> roomResult = FXCollections.observableArrayList();
    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

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
    private TextField buildingNameTextField;
    @FXML
    private Text pagesText;
    @FXML
    private Text buildingPagesText;
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
    private TableColumn<Building, Integer> colBuildingId;
    @FXML
    private TableColumn<Building, String> colBuildingName;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button nextBuildingPageButton;
    @FXML
    private Button previousBuildingPageButton;
    @FXML
    private TableView<Building> buildingTable;
    @FXML
    private ToggleButton buildingsTableToggle;

    private int pageNumber;
    private double totalPages;
    private boolean buildingsTableToggleFlag;
    private int buildingPageNumber;
    private double totalBuildingPages;
    private Room selectedRoom;
    private List<Room> rooms;
    private List<Building> buildings;
    private Button reserveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colBeamer.setCellValueFactory(new PropertyValueFactory<>("projector"));
        colScreen.setCellValueFactory(new PropertyValueFactory<>("screen"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colPlugs.setCellValueFactory(new PropertyValueFactory<>("plugs"));

        colBuildingName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuildingId.setCellValueFactory(new PropertyValueFactory<>("id"));

        anchorPane.getChildren().remove(buildingTable);
        anchorPane.getChildren().remove(previousBuildingPageButton);
        anchorPane.getChildren().remove(nextBuildingPageButton);
        anchorPane.getChildren().remove(buildingPagesText);

        pageNumber = 1;
        buildingPageNumber = 1;

        applyFilters();
        buildingTableSelectListener();
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveAllBuildings() {
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            buildings = new ArrayList<>();
            buildingTable.setPlaceholder(new Label(""));
        }
        calculateBuildingPages();
    }

    /**
     * Calculates the right amount of building pages for proper viewing of the building table
     */
    public void calculateBuildingPages() {
        buildingResult.clear();
        totalBuildingPages = Math.ceil(buildings.size() / 7.0);

        if (totalBuildingPages < 1) {
            buildingPageNumber--;
        }

        buildingPagesText.setText(buildingPageNumber + " / " + (int) totalBuildingPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 0; i < 7; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + buildingPageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            buildingResult.addAll(buildings);
        }
        buildingTable.setItems(buildingResult);
    }

    /**
     * Changes to MainMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Changes to MyPreviousBookings.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     * Changes to MyCurrentBookings.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }

    /**
     * Return to the reservations menu when the back arrow button is clicked.
     *
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToRoomCalendar() throws IOException {
        SelectedRoom.setSelectedRoom(selectedRoom);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BookRoomCalendar.fxml"));
        Parent root = loader.load();

        ApplicationDisplay.getPrimaryStage().setScene(new Scene(root));
    }

    /**
     * Handles clicking the list button.
     */
    public void listRoomsButtonClicked(String filterString) {
        try {
            rooms = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper(RoomServerCommunication.filterRooms(filterString))));
        } catch (Exception e) {
            table.setPlaceholder(new Label(""));
            rooms = new ArrayList<>();
        }
        calculatePages();
        roomTableSelectListener();
    }

    /**
     * Calculates the amount of pages necessary to display all the entries.
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
        } else {
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
     * Handles the clicking to the next table page.
     */
    public void nextBuildingPage() {
        if (buildingPageNumber < (int) totalBuildingPages) {
            buildingPageNumber++;
            calculateBuildingPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousBuildingPage() {
        if (buildingPageNumber > 1) {
            buildingPageNumber--;
        }
        calculateBuildingPages();
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickBuildingsTable() {
        if (buildingsTableToggleFlag) {
            buildingsTableToggle.setText("Show");
            anchorPane.getChildren().remove(buildingTable);
            anchorPane.getChildren().remove(previousBuildingPageButton);
            anchorPane.getChildren().remove(nextBuildingPageButton);
            anchorPane.getChildren().remove(buildingPagesText);
        } else {
            buildingsTableToggle.setText(" Hide");
            anchorPane.getChildren().add(buildingTable);
            anchorPane.getChildren().add(previousBuildingPageButton);
            anchorPane.getChildren().add(nextBuildingPageButton);
            anchorPane.getChildren().add(buildingPagesText);
            retrieveAllBuildings();
        }
        buildingsTableToggleFlag = !buildingsTableToggleFlag;
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void roomTableSelectListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(reserveButton);

            final Room room = table.getSelectionModel().getSelectedItem();

            for (int i = 0; i < roomResult.size(); i++) {
                assert room != null;
                if (roomResult.get(i).getId().equals(room.getId())) {
                    reserveButton = new Button("Reserve");
                    reserveButton.setLayoutX(1190);
                    reserveButton.setLayoutY(168 + (24 * (i + 1)));
                    reserveButton.setMinWidth(75);
                    reserveButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    reserveButton.setMaxHeight(24);
                    reserveButton.setOnAction(event -> {
                        try {
                            if (room != null) {
                                setSelectedRoom(room);
                                goToRoomCalendar();
                            }
                        } catch (IOException e) {
                            //Left empty
                        }
                    });
                    anchorPane.getChildren().add(reserveButton);
                }
            }
        });
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void buildingTableSelectListener() {
        buildingTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final Building building = buildingTable.getSelectionModel().getSelectedItem();
            if (building != null) {
                buildingNameTextField.setText(building.getName());
            }
        });
    }

    /**
     * Applies the selected filters
     */
    public void applyFilters() {
        String filterString = "";
        if (!buildingNameTextField.getText().isEmpty()) {
            try {
                filterString += ",building:";
                filterString += JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(buildingNameTextField.getText())).getId();
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
}
