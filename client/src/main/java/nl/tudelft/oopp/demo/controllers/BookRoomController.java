package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * This class controls the functionality of the different buttons in bike reservations and creates the entries in the choice boxes
 */
public class BookRoomController implements Initializable {

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    final ObservableList<String> listOfTimeSlots = FXCollections.observableArrayList();
    final ObservableList<String> listOfBuildings = FXCollections.observableArrayList();




    public class Search {
        private boolean screen;
        private boolean beamer;
        private int capacity;
        private String building;
        private int nuOfPlugs;

        /**
         * constructor for the search object
         * @param screen is there a screen
         * @param beamer is there a beamer
         * @param capacity the capacity of the room
         * @param building what building is it in
         * @param nuOfPlugs number of plug
         */
        public Search(boolean screen, boolean beamer, int capacity, String building, int nuOfPlugs) {
            this.screen = screen;
            this.beamer = beamer;
            this.building = building;
            this.capacity = capacity;
            this.nuOfPlugs = nuOfPlugs;
        }

    }

    @FXML
    private CheckBox screen;
    @FXML
    private CheckBox beamer;
    @FXML
    private ChoiceBox<String> building;
    @FXML
    private Button submitButton;
    @FXML
    private TextField capacity;
    @FXML
    private TextField nuOfPlugs;
    @FXML
    private TextArea rooms;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadRoomData();
    }

    /**
     * applies the selected filters
     * @return returns an object of search with the proper properties
     */
    public Search applyFilters() {
        if (building.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("Please select a building");
            alert.showAndWait();
            return null;
        }
        if (capacity.getCharacters() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("Please select a capacity");
            alert.showAndWait();
            return null;
        }
        if (nuOfPlugs.getCharacters() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("Please select a number of plugs");
            alert.showAndWait();
            return null;
        }
        boolean isScreen = false;
        boolean isBeamer = false;
        if (screen.isSelected()) {
            isScreen = true;
        }
        if (beamer.isSelected()) {
            isBeamer = true;
        }
        int intCapacity;
        String stringCapacity = (String) capacity.getCharacters();
        intCapacity = Integer.parseInt(stringCapacity);
        int intPlugs;
        String stringPlugs = (String) nuOfPlugs.getCharacters();
        intPlugs = Integer.parseInt(stringPlugs);
        Search search = new Search(isScreen, isBeamer, intCapacity, building.getValue(), intPlugs);
        return search;
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will allways be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will allways be the same, so it should never throw an IO exception
     */
    public void goToRoomConfirmation() throws IOException {
        ApplicationDisplay.changeScene("/RooomConfirmation.fxml");
    }
    /**
     * Adds the items to the choice boxes
     */
    public void loadRoomData() {
        // rooms.setText("rooms=" + "ServerCommunication.getRooms()");
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
     * @throws IOException when it fails
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to mainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

}
