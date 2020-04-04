package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import com.calendarfx.model.Interval;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.communication.SelectedRoom;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private int roomId;
    private RoomCalendarView calendarView;
    URL location;
    ResourceBundle resourceBundle;
    private boolean isThereAnotherEntry = false;
    private Entry<RoomReservation> storedEntry = null;

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
        calendarView   = new RoomCalendarView(SelectedRoom.getSelectedRoom().getId());
        EventHandler<CalendarEvent> handler = this::entryHandler;
        calendarView.getCalendars().get(0).addEventHandler(handler);
        calendarView.loadRoomReservations();
        calendarView.setShowToolBar(false);
        calendarContainer.setRoot(calendarView);
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
        Entry<RoomReservation> entry = (Entry<RoomReservation>) e.getEntry();
        Date start = convertToDate(entry.getStartTime(), entry.getStartDate());
        Date end = convertToDate(entry.getEndTime(), entry.getStartDate());


        if (e.isEntryAdded()) {
            if (this.storedEntry != null || entry.getEndAsLocalDateTime().isBefore(LocalDateTime.now())) {
                e.getEntry().removeFromCalendar();
                fromTime.setText(convertToDate(storedEntry.getStartTime(), storedEntry.getStartDate()).toString());
                untilTime.setText(convertToDate(storedEntry.getEndTime(), storedEntry.getEndDate()).toString());
            } else {
                entry.setTitle("New Booking");
                fromTime.setText(start.toString());
                untilTime.setText(end.toString());
                reserveSlot.arm();
                storedEntry = (Entry<RoomReservation>) e.getEntry();
                this.isThereAnotherEntry = true;
            }
        } else if (e.isEntryRemoved()) {
            fromTime.setText("");
            untilTime.setText("");
            reserveSlot.disarm();
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

        if (dateFrom.equals("") || dateUntil.equals("")) {
            CustomAlert.warningAlert("No slot selected");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date from = format.parse(dateFrom);
                Date until = format.parse(dateUntil);
                long milliseconds1 = from.getTime();
                long milliseconds2 = until.getTime();

                if (ServerCommunication.addRoomReservation(SelectedRoom.getSelectedRoom().getId(), milliseconds1, milliseconds2)
                        .equals(ErrorMessages.getErrorMessage(308))) {
                    CustomAlert.warningAlert("Slot is already booked. Please make sure you do not overlay another reservation entry.");
                } else {
                    CustomAlert.informationAlert("Successfully Added!");
                    ApplicationDisplay.changeScene("/bookRoom.fxml");
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        resourceBundle = resources;
        reserveSlot.disarm();
        showCal();
        disableFields();
    }
}
