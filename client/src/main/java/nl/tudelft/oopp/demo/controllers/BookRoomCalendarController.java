package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private Room room = null;
    private RoomCalendarView calendarView = new RoomCalendarView();

    private void setRoom(Room room) {
        this.room = room;
    }

    @FXML
    SubScene calendarContainer;

    public void showCal() {
        calendarContainer.setRoot(calendarView.getWeekPage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCal();
    }
}
