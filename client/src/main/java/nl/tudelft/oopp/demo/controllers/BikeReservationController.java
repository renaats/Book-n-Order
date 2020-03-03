package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class BikeReservationController implements Initializable {
    final ObservableList list = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> pick;
    @FXML
    private  ChoiceBox<String> drop;
    @FXML
    private TextField screen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    @FXML
    private void reserveBike() {
        String bike = pick.getValue() + drop.getValue();
        //noinspection ConstantConditions
        if (bike == null) {
            screen.setText("No bike");
        } else {
            screen.setText("your bike is " + bike);
        }
    }

    /**
     * Loads temporary data into ChoiceBox.
     */
    public  void loadData() {
        list.removeAll(list);
        String a = "1";
        String b = "2";
        String c = "3";
        list.addAll(a,b,c);
        pick.getItems().addAll(list);
        drop.getItems().addAll(list);
    }
}
