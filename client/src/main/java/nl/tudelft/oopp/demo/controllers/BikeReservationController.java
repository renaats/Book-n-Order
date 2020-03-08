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
    final ObservableList list = FXCollections.observableArrayList();

    final ObservableList lisT = FXCollections.observableArrayList();

    final ObservableList listM = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> pick;
    @FXML
    private  ChoiceBox<String> drop;
    @FXML
    private TextField screen;
    @FXML
    private ChoiceBox<String> dropOffTime;
    @FXML
    private ChoiceBox<String> pickUpTime;
    @FXML
    private ChoiceBox<String> dropOffTimeM;
    @FXML
    private ChoiceBox<String> pickUpTimeM;

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
        list.removeAll(list);
        lisT.removeAll(lisT);
        String a = "1";
        String b = "2";
        String c = "3";
        list.addAll(a,b,c);
        pick.getItems().addAll(list);
        drop.getItems().addAll(list);
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                lisT.add("0" + i);
            } else {
                lisT.add(i);
            }
        }
        pickUpTime.getItems().addAll(lisT);
        dropOffTime.getItems().addAll(lisT);
        listM.removeAll(listM);
        for (int i = 0; i < 60; i = i + 15) {
            if (i == 0) {
                listM.add("00");
            } else {
                listM.add(i);
            }
        }
        pickUpTimeM.getItems().addAll(listM);
        dropOffTimeM.getItems().addAll(listM);
    }

    /**
     * Changes the view to the main menu when the button is clicked
     */

    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainMenu.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the food button.
     */
    public void foodsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/orderFood.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the bikes button.
     */
    public void bikesSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bikeReservations.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void roomsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bookRoom.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
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

     /** Goes to bookRoom view
     */
    public void roomsSelected1(ActionEvent actionEvent) throws IOException {

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
        /** changes view to food reservations
     */

    public void foodsSelected1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * Goes back to mainMenu scene
     */
    public void gobacktoMainMenu(ActionEvent actionEvent) throws IOException {
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
