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

import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseEditRooms.fxml" file
 */
public class DatabaseRoomMenuController {

    final ObservableList<Room> roomResult = FXCollections.observableArrayList();

    @FXML
    private TableView<Room> table;

    /**
     * Handles clicking of the List Rooms button.
     */
    public void roomBuildingsButtonClicked() {
        List<Room> rooms = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper(ServerCommunication.getRooms())));
        roomResult.clear();
        roomResult.addAll(rooms);
        table.setItems(roomResult);
        if (roomResult.isEmpty()) {
            CustomAlert.warningAlert("No rooms found.");
        }
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    public void previousPage(ActionEvent actionEvent) {
    }

    public void nextPage(ActionEvent actionEvent) {
    }

    public void goToAddRooms(MouseEvent mouseEvent) {
    }

    public void findRoom(ActionEvent actionEvent) {
    }

    public void retrieveAllRooms(ActionEvent actionEvent) {
    }

    public void updateRoom(ActionEvent actionEvent) {
    }

    public void deleteRoom(ActionEvent actionEvent) {
    }
}
