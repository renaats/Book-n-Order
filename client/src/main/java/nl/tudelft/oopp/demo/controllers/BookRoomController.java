package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "BookRoom.fxml" file
 */
public class BookRoomController {

    public static class Search {
        private final boolean screen;
        private final boolean beamer;
        private final int capacity;
        private final String building;
        private final int nuOfPlugs;

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
    private TextField nuOfPlugs;
    private TextArea rooms;

    /**
     * applies the selected filters
     * @return returns an object of search with the proper properties
     */
    public Search applyFilters() {
        if (building.getValue() == null) {
            CustomAlert.warningAlert("Please select a building.");
            return null;
        }
        if (capacity.getCharacters() == null) {
            CustomAlert.warningAlert("Please select a capacity.");
            return null;
        }
        if (nuOfPlugs.getCharacters() == null) {
            CustomAlert.warningAlert("Please select the amount of plugs.");
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
        return new Search(isScreen, isBeamer, intCapacity, building.getValue(), intPlugs);
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }
    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */

    public void goToRoomConfirmation() throws IOException {
        ApplicationDisplay.changeScene("/RoomConfirmation.fxml");
    }

    /**
     * Changes to MyCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }

    /**
     * Changes to MyPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     * Changes to MainMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

}
