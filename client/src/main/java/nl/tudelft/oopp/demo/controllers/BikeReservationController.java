package nl.tudelft.oopp.demo.controllers;
import java.net.URL;

import java.io.IOException;

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


@SuppressWarnings({"unchecked", "CollectionAddedToSelf", "ConstantConditions"})
public class BikeReservationController implements Initializable {
    @SuppressWarnings("rawtypes")
    final ObservableList list = FXCollections.observableArrayList();
    @SuppressWarnings("rawtypes")
    final ObservableList lisT = FXCollections.observableArrayList();
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

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        loadData();
    }

    @FXML
    private void reserveBike () {
        String bike = pick.getValue()+ drop.getValue();
        //noinspection ConstantConditions
        if (bike == null) {
            screen.setText("No bike");
        }
        else {
            screen.setText("your bike is "+bike);
        }
    }

    public void loadData () {
        list.removeAll(list);
        lisT.removeAll(lisT);
        String a= "1";
        String b = "2";
        String c = "3";
        list.addAll(a,b,c);
        pick.getItems().addAll(list);
        drop.getItems().addAll(list);
        for (int i = 0; i < 24; i++) {
            for(int u = 0; u <= 45; u = u + 15) {
                if(i == 0 && u == u){
                    if (!lisT.contains("00:00")) {
                        lisT.add("00:00");
                    }
                }
                else if (u == 0){
                    lisT.add(i+":00");
                }
                else if (i == 0){
                    lisT.add("00:" + u);
                }
                else {
                    lisT.add(i + ":" + u);
                }
            }
        }
        pickUpTime.getItems().addAll(lisT);
        dropOffTime.getItems().addAll(lisT);
    }

    public void goBackToMenu (ActionEvent actionEvent) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainMenu.fxml"));
        Scene roomSelectScene = new Scene (roomSelectParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }
}
