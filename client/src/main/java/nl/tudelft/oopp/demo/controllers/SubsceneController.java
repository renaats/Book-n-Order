package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

import java.net.URL;
import java.util.ResourceBundle;

public class SubsceneController implements Initializable {

    @FXML
    SubScene calendarContainer;

    public void showCal() {
        calendarContainer.setRoot(new RoomCalendarView());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCal();
    }
}
