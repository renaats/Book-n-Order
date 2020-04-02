package nl.tudelft.oopp.demo.controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private Room room = null;
    private RoomCalendarView calendarView = new RoomCalendarView();

    @FXML
    SubScene calendarContainer;
    @FXML
    TextField fromTime;
    @FXML
    TextField untilTime;
    @FXML
    Button reserveSlot;

    public void showCal() {

        calendarContainer.setRoot(calendarView.getWeekPage());
        calendarView.setLayout(DateControl.Layout.SWIMLANE);
        EventHandler<CalendarEvent> handler = e -> entryHandler(e);
        calendarView.myReservations.addEventHandler(handler);
    }

    /**
     * Changes to mainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void goToRoomBook() throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    private void entryHandler (CalendarEvent e) {
        Entry<RoomReservation> newEntry = (Entry<RoomReservation>) e.getEntry();

        Date start = convertToDate(newEntry.getStartTime(), newEntry.getStartDate());
        Date end = convertToDate(newEntry.getEndTime(), newEntry.getStartDate());

        if (e.isEntryAdded()) {
            fromTime.setText(start.toString());
            untilTime.setText(end.toString());
            System.out.println(start.toString());
        } else if (e.isEntryRemoved()) {
            System.out.println("entry removed");
            fromTime.setText("");
            untilTime.setText("");
            System.out.println(start.toString());
        }
    }

    public void addReservation() {
        String dateFrom = fromTime.getText();
        String dateUntil = untilTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddThh:mm:ss.000+0000");

        try {
            Date from = format.parse(dateFrom);
            Date until = format.parse(dateUntil);
            long milliseconds1 = from.getTime();
            long milliseconds2 = until.getTime();
            ServerCommunication.addRoomReservation(this.room.getId(), milliseconds1, milliseconds2);
        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCal();
    }
}
