package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private Room room = null;
    private RoomCalendarView calendarView = new RoomCalendarView();

    @FXML
    SubScene calendarContainer;
    @FXML
    TextField fromTime;

    public void disableFromTime() {
        fromTime.setDisable(true);
    }

    @FXML
    TextField untilTime;

    public void disableUntilTime() {
        untilTime.setDisable(true);
    }

    @FXML
    Button reserveSlot;

    public void showCal() {
        disableFromTime();
        disableUntilTime();
        calendarContainer.setRoot(calendarView);
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

        Entry<RoomReservation> event = (Entry<RoomReservation>) e.getEntry();
        Date start = convertToDate(event.getStartTime(), event.getStartDate());
        Date end = convertToDate(event.getEndTime(), event.getStartDate());

        if (e.isEntryAdded()) {
            fromTime.setText(start.toString());
            untilTime.setText(end.toString());
        } else if (e.isEntryRemoved()) {
            fromTime.setText("");
            untilTime.setText("");
        } else {
            fromTime.setText(start.toString());
            untilTime.setText(end.toString());
        }
    }

    public void addReservation() {
        String dateFrom = fromTime.getText();
        String dateUntil = untilTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try {
            Date from = format.parse(dateFrom);
            Date until = format.parse(dateUntil);
            long milliseconds1 = from.getTime();
            long milliseconds2 = until.getTime();

            if (fromTime.getText().equals("") || untilTime.getText().equals("")) {
                reserveSlot.disarm();
            }

            if(ServerCommunication.addRoomReservation(2, milliseconds1, milliseconds2).equals(ErrorMessages.getErrorMessage(308))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Slot is already booked. Please make sure you do not overlay another reservation entry.");
                alert.showAndWait();
            }

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
