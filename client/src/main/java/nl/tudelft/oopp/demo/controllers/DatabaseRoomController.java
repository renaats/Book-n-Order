package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseRoomController implements Initializable {

    final ObservableList<String> updateChoiceBoxList = FXCollections.observableArrayList();
    final ObservableList<Room> roomResult = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField roomFindByIdTextField;
    @FXML
    private TextField roomDeleteByIdTextField;
    @FXML
    private TextField roomFindByIdUpdateField;
    @FXML
    private TextField roomChangeToField;
    @FXML
    private TableView<Room> table;
    @FXML
    private TableColumn<Building, String> colId;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colBuilding;
    @FXML
    private TableColumn<Building, Integer> colFaculty;
    @FXML
    private TableColumn<Building, String> colFacultySpecific;
    @FXML
    private TableColumn<Building, String> colProjector;
    @FXML
    private TableColumn<Building, String> colScreen;
    @FXML
    private TableColumn<Building, Integer> colNrPeople;
    @FXML
    private TableColumn<Building, String> colPlugs;
    @FXML
    private TableColumn<Building, String> colRoomReservations;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("Building"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        colFacultySpecific.setCellValueFactory(new PropertyValueFactory<>("FacultySpecific"));
        colProjector.setCellValueFactory(new PropertyValueFactory<>("Projector"));
        colScreen.setCellValueFactory(new PropertyValueFactory<>("Screen"));
        colNrPeople.setCellValueFactory(new PropertyValueFactory<>("nrPeople"));
        colPlugs.setCellValueFactory(new PropertyValueFactory<>("Plugs"));
        colRoomReservations.setCellValueFactory((new PropertyValueFactory<>("RoomReservations")));
    }

    /**
     * Handles clicking of the Find Room button.
     */
    public void roomIdButtonClicked() {
        try {
            int id = Integer.parseInt(roomFindByIdTextField.getText());
            Room room = JsonMapper.roomMapper(ServerCommunication.findRoom(id));
            roomResult.clear();
            roomResult.add(room);
            System.out.println("test1");
            table.setItems(roomResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking of the List Rooms button.
     */
    public void roomBuildingsButtonClicked() {
        List<Room> rooms = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper(ServerCommunication.getRooms())));
        roomResult.clear();
        roomResult.addAll(rooms);
        table.setItems(roomResult);
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Handles clicking of the Remove Room button.
     */
    public void deleteRoomButtonClicked() {
        int id = Integer.parseInt(roomDeleteByIdTextField.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room remover");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteRoom(id));
        alert.showAndWait();

        roomResult.removeIf(room -> room.getId() == id);
    }

    /**
     * Handles the sending of update values.
     */
    public void updateRoomButtonClicked() {
        try {
            int id = Integer.parseInt(roomFindByIdUpdateField.getText());
            String attribute = updateChoiceBox.getValue().replaceAll(" ", "").toLowerCase();
            String changeValue = roomChangeToField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building remover");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateRoom(id, attribute, changeValue));
            alert.showAndWait();
            roomResult.clear();
            roomBuildingsButtonClicked();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Adds options to the updateChoiceBox.
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.clear();
        String a = "Name";
        String b = "Faculty";
        String c = "Building ID";
        String d = "Faculty Specific";
        String e = "Screen";
        String f = "Projector";
        String g = "Amount of people";
        String h = "Plugs";
        updateChoiceBoxList.addAll(a, b, c, d, e, f, g, h);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

    /**
     * Switches scene to DatabaseAddBuildings.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * returns to the main menu
     * @param actionEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
