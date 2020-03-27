package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.StageStyle;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the user input from the database bike menu view.
 */
public class BikeDatabaseMenuController implements Initializable {

    final ObservableList<Bike> bikeSearchResult = FXCollections.observableArrayList();

    @FXML
    private TableView table;
    @FXML
    private TableColumn<Bike, Integer> colId;
    @FXML
    private TableColumn<Bike, String> colLocation;
    @FXML
    private TableColumn<Bike, Boolean> colAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        loadBikesIntoTable();
    }

    /**
     * Loads all bike of the database into the table.
     */
    private void loadBikesIntoTable() {
        if (JsonMapper.bikeListMapper(ServerCommunication.getBikes()) != null) {
            List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBikes())));
            bikeSearchResult.clear();
            bikeSearchResult.addAll(bikes);
            table.setItems(bikeSearchResult);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("There are currently no bikes in the database");
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Changes view to the database main menu when the home icon is clicked.
     * @throws IOException should never throw an IOException as the input is always the same.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Changes view to the database bike add when the add bikes button is clicked.
     * @throws IOException should never throw an IOException as the input is always the same.
     */
    public void goToAddBikes() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    /**
     * Changes view to the database bike edit when the add bikes button is clicked.
     * @throws IOException should never throw an IOException as the input is always the same.
     */
    public void goToEditBikes() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }
}
