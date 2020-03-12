package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * This class controls the functionality of the different buttons in bike reservations and creates the entries in the choice boxes
 */

public class BikeReservationController implements Initializable {


    final ObservableList listLocations = FXCollections.observableArrayList();

    final ObservableList listTime = FXCollections.observableArrayList();

    final ObservableList listMinutes = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> pickUpTimeH;
    @FXML
    private ComboBox<String> pickUpTimeMin;
    @FXML
    private ComboBox<String> dropOffTimeH;
    @FXML
    private ComboBox<String> dropOffTimeMin;
    @FXML
    private TextField screen;

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void bikeConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/calendar.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        listTime.removeAll(listTime);
        listMinutes.removeAll(listMinutes);
        for (int i = 0;i <= 45; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(i);
            }
        }
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                listTime.add("0" + i);
            } else {
                listTime.add(i);
            }
        }
        pickUpTimeH.getItems().addAll(listTime);
        dropOffTimeH.getItems().addAll(listTime);
        pickUpTimeMin.getItems().addAll(listMinutes);
        dropOffTimeMin.getItems().addAll(listMinutes);
        screen.setText(ServerCommunication.getBuildings());
    }
}
