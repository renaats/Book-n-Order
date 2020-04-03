package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.SelectedRoom;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private int roomId;
    private RoomCalendarView calendarView;
    URL location;
    ResourceBundle resourceBundle;

    @FXML
    Button reserveSlot;
    @FXML
    SubScene calendarContainer;
    @FXML
    TextField fromTime;
    @FXML
    TextField untilTime;

    public BookRoomCalendarController() {
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Renders calendar view with all already existing reservations.
     */
    public void showCal() {
        calendarView   = new RoomCalendarView(SelectedRoom.getSelectedRoom());
        calendarContainer.setRoot(calendarView);
        EventHandler<CalendarEvent> handler = e -> entryHandler(e);
        calendarView.getCalendars().get(0).addEventHandler(handler);
        calendarView.loadRoomReservations();
    }

    /**
     * Makes text fields read only.
     */
    public void disableFields() {
        fromTime.setDisable(true);
        untilTime.setDisable(true);
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

    private void entryHandler(CalendarEvent e) {

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

    /**
     * Handles communicating the new entry to the server.
     */
    public void addReservation() {
        String dateFrom = fromTime.getText();
        String dateUntil = untilTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        System.out.println(this.roomId);
        try {
            Date from = format.parse(dateFrom);
            Date until = format.parse(dateUntil);
            long milliseconds1 = from.getTime();
            long milliseconds2 = until.getTime();

            if (ServerCommunication.addRoomReservation(SelectedRoom.getSelectedRoom(), milliseconds1, milliseconds2)
                    .equals(ErrorMessages.getErrorMessage(308))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Slot is already booked. Please make sure you do not overlay another reservation entry.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successfully Added!");
                alert.setHeaderText(null);
                alert.setContentText(ErrorMessages.getErrorMessage(200));
                alert.showAndWait();
                ApplicationDisplay.changeScene("/bookRoom.fxml");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts LocalTime and LocalDate into Date object.
     * @param time = the hour, minute and seconds of the slot.
     * @param date = the date of the slot.
     * @return Date object that holds both components time and date.
     */
    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Extracts time in Date object and converts it into LocalTime object.
     * @param date = the full date of the slot.
     * @return LocalTime object that holds the time component of date input.
     */
    public LocalTime convertToLocalTime(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Extracts date in Date object and converts it into LocalDate object.
     * @param date = the full date of the slot.
     * @return LocalDate object that holds the date component of date input.
     */
    public LocalDate convertToLocalDate(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        resourceBundle = resources;
        showCal();
        disableFields();
    }
}
