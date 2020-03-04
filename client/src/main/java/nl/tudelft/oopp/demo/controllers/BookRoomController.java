package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class BookRoomController implements Initializable {

    final ObservableList list = FXCollections.observableArrayList();
    final ObservableList lisT = FXCollections.observableArrayList();
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
        list.removeAll(list);
        lisT.removeAll(lisT);
        String a = "1";
        String b = "2";
        String c = "3";
        list.addAll(a,b,c);
        roomDropDown.getItems().addAll(list);

        String none = "-";
        String building1 = "building1";
        String building2 = "building2";
        String building3 = "building3";
        listOfBuildings.addAll(none,building1,building2,building3);
        buildingList.getItems().addAll(listOfBuildings);

        for (int i = 0; i < 24; i++) {
            for (int u = 0; u <= 45; u = u + 15) {
                if (i == 0 && u == u) {
                    if (!lisT.contains("00:00")) {
                        lisT.add("00:00");
                    }
                } else if (u == 0) {
                    lisT.add(i + ":00");
                } else if (i == 0) {
                    lisT.add("00:" + u);
                } else {
                    lisT.add(i + ":" + u);
                }
            }
        }
        from.getItems().addAll(lisT);
        until.getItems().addAll(lisT);

    }
}
