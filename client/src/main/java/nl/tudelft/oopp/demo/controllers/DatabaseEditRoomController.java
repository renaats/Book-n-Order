package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseEditRoomController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();
    final ObservableList<Room> roomResult = FXCollections.observableArrayList();

    @FXML
    private TextField roomDeleteByIdTextField;
    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField roomFindByIdTextField;
    @FXML
    private TextField roomChangeToField;
    @FXML
    private TableView<Room> table;
    @FXML
    private TableColumn<Room, Integer> colId;
    @FXML
    private TableColumn<Room, String> colName;
    @FXML
    private TableColumn<Room, String> colBuilding;
    @FXML
    private TableColumn<Room, Integer> colFaculty;
    @FXML
    private TableColumn<Room, String> colFacultySpecific;
    @FXML
    private TableColumn<Room, String> colProjector;
    @FXML
    private TableColumn<Room, String> colScreen;
    @FXML
    private TableColumn<Room, Integer> colCapacity;
    @FXML
    private TableColumn<Room, String> colPlugs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        colFacultySpecific.setCellValueFactory(new PropertyValueFactory<>("FacultySpecific"));
        colProjector.setCellValueFactory(new PropertyValueFactory<>("Projector"));
        colScreen.setCellValueFactory(new PropertyValueFactory<>("Screen"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colPlugs.setCellValueFactory(new PropertyValueFactory<>("Plugs"));

        roomBuildingsButtonClicked();
    }

    /**
     * Handles clicking of the Find Room button.
     */
    public void roomFindById() {
        try {
            int id = Integer.parseInt(roomFindByIdTextField.getText());
            Room room = JsonMapper.roomMapper(ServerCommunication.findRoom(id));
            roomResult.clear();
            roomResult.add(room);
            table.setItems(roomResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
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
        if (roomResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("No buildings found.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Handles the sending of update values.
     */
    public void updateRoomButtonClicked() {
        try {
            int id = Integer.parseInt(roomFindByIdTextField.getText());
            String attribute = updateChoiceBox.getValue().replaceAll(" ", "").toLowerCase();
            String changeValue = roomChangeToField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateRoom(id, attribute, changeValue));
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();

            roomResult.clear();
            roomBuildingsButtonClicked();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
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
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * When the room icon is clicked it take you to the RoomEditOrAdd.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void goToRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/RoomsEditOrAdd.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseEditRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }

    /**
     * Handles clicking of the Remove Room button.
     */
    public void deleteRoomButtonClicked() {
        int id = Integer.parseInt(roomDeleteByIdTextField.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteRoom(id));
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/alertInformation.css").toExternalForm());
        alert.showAndWait();


        roomResult.removeIf(room -> room.getId() == id);
    }

    /**
     * Handles clicking of the edit room button through table.
     */
    public void editRoomByTable() {
        try {
            Room room = table.getSelectionModel().getSelectedItem();
            roomFindByIdTextField.setText(Integer.toString(room.getId()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking of the Remove Room button through the table.
     */
    public void deleteRoomByTable() {
        try {
            Room room = table.getSelectionModel().getSelectedItem();
            roomResult.removeIf(r -> r.getId().equals(room.getId()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.deleteRoom((room.getId())));
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertInformation.css").toExternalForm());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }
}
