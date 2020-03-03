package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;


public class OrderFoodController implements Initializable {
    final ObservableList foodLocations = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> pickFoodLocations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        foodLocations.removeAll(foodLocations);
        foodLocations.add("Aula Canteen");
        foodLocations.add("Pulse Bar");
        foodLocations.add("IO Canteen");

        pickFoodLocations.getItems().addAll(foodLocations);
    }

    public void placeOrder(ActionEvent actionEvent) {
    }
}
