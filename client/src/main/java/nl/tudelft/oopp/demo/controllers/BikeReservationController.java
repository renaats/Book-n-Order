package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * This class controls the functionality of the different buttons in bike reservations and creates the entries in the choice boxes
 */

public class BikeReservationController implements Initializable {
    final ObservableList listLocations = FXCollections.observableArrayList();

    final ObservableList listTime = FXCollections.observableArrayList();

    final ObservableList listMinutes = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> pick;
    @FXML
    private  ChoiceBox<String> drop;
    @FXML
    private TextField screen;
    @FXML
    private ChoiceBox<String> pickUpLocation;
    @FXML
    private ChoiceBox<String> pickUpTime;
    @FXML
    private ChoiceBox<String> dropOffTime;
    @FXML
    private ChoiceBox<String> dropOffLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * Is meant to connect to the back end
     * does: it prints the choices in the choice boxes
     * Will: send the choice box contents to database to be processed
     */
    @FXML
    private void reserveBike() {
        String bike = pick.getValue() + drop.getValue();
        if (bike == null) {
            screen.setText("No bike");
        } else {
            screen.setText("your bike is " + bike);
        }
    }

    /**
     * Adds the items to the choice boxes (the 1,2,3 must be changed to the different pick-up locations)
     */

    public void loadData() {
        listLocations.removeAll(listLocations);
        listTime.removeAll(listTime);
        String a = "1";
        String b = "2";
        String c = "3";
        listLocations.addAll(a,b,c);
        pickUpLocation.getItems().addAll(listLocations);
        dropOffLocation.getItems().addAll(listLocations);
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                listTime.add("0" + i);
            } else {
                listTime.add(i);
            }
        }
        pickUpTime.getItems().addAll(listTime);
        dropOffTime.getItems().addAll(listTime);
        listMinutes.removeAll(listMinutes);
        for (int i = 0; i < 60; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(i);
            }
        }
        pickUpTime.getItems().addAll(listMinutes);
        dropOffTime.getItems().addAll(listMinutes);
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to bikeReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void rentBike(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void bookRoom(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    /**
     * Changes to orderFood.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void orderFood(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFood.fxml");
    }

    /**
     * Changes to mainMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to bikeConfirmation.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void bikeConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeConfirmation.fxml");
    }
}
