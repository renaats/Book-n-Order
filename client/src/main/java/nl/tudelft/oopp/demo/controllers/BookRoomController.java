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

public class BookRoomController implements Initializable {

    final ObservableList listOfRooms = FXCollections.observableArrayList();
    final ObservableList listOfTimeSlots = FXCollections.observableArrayList();
    final ObservableList listOfBuildings = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> roomDropDown;
    @FXML
    private  ChoiceBox<String> from;
    @FXML
    private ChoiceBox<String> until;
    @FXML
    private ChoiceBox<String> buildingList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadRoomData();
    }

    @FXML
    private void reservedRoomSlot() {
        if (from.getValue() == null || until.getValue() == null) {
            System.out.println("Nothing selected");
        } else {
            System.out.println("Your selection was from:" + from.getValue() + " ,until: " + until.getValue());
        }
    }

    /**
     * Adds the items to the choice boxes
     */
    public void loadRoomData() {
        listOfRooms.removeAll(listOfRooms);
        listOfTimeSlots.removeAll(listOfTimeSlots);
        listOfBuildings.removeAll(listOfBuildings);
        String none = "-";
        String a = "1";
        String b = "2";
        String c = "3";
        listOfRooms.addAll(none,a,b,c);
        roomDropDown.getItems().addAll(listOfRooms);

        String building1 = "building1";
        String building2 = "building2";
        String building3 = "building3";
        listOfBuildings.addAll(none,building1,building2,building3);
        buildingList.getItems().addAll(listOfBuildings);

        listOfTimeSlots.add(none);

        for (int i = 0; i < 24; i++) {
            for (int u = 0; u <= 45; u = u + 15) {
                if (i == 0) {
                    if (!listOfTimeSlots.contains("00:00")) {
                        listOfTimeSlots.add("00:00");
                    }
                } else if (u == 0) {
                    listOfTimeSlots.add(i + ":00");
                } else if (i == 0) {
                    listOfTimeSlots.add("00:" + u);
                } else {
                    listOfTimeSlots.add(i + ":" + u);
                }
            }
        }
        from.getItems().addAll(listOfTimeSlots);
        until.getItems().addAll(listOfTimeSlots);
    }

    /**
     * Handles clicking the food button.
     */
    public void foodsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
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
     * Changes to roomConfirmation.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void roomConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/roomConfirmation.fxml");
    }
}
