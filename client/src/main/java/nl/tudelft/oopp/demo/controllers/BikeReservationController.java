package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bikeReservations.fxml" file
 */
public class BikeReservationController implements Initializable {
    final ObservableList<String> listTime = FXCollections.observableArrayList();
    final ObservableList<String> listMinutes = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> pickUpTimeH;
    @FXML
    private ComboBox<String> pickUpTimeMin;
    @FXML
    private ComboBox<String> dropOffTimeH;
    @FXML
    private ComboBox<String> dropOffTimeMin;

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    public void bikeConfirmation() throws IOException {
        ApplicationDisplay.changeScene("/bikeConfirmation.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        listTime.clear();
        listMinutes.clear();
        for (int i = 0;i <= 45; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(((Integer)i).toString());
            }
        }
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                listTime.add("0" + i);
            } else {
                listTime.add("" + i);
            }
        }
        pickUpTimeH.getItems().addAll(listTime);
        dropOffTimeH.getItems().addAll(listTime);
        pickUpTimeMin.getItems().addAll(listMinutes);
        dropOffTimeMin.getItems().addAll(listMinutes);
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}
