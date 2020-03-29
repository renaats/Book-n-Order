package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.calendarfx.view.WeekView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class RoomCalendarView extends CalendarView {

    private Room room;
    private AppUser user;

    Calendar unavailableSpots = new Calendar("Unavailable Spots");
    Calendar myReservations = new Calendar("My Reservations");

    /**
     * Constuctor for the Personal Calendar View
     */
    public RoomCalendarView() {
//        this.room = room;
//        this.user = user;
        displayCalendars();
//        loadRoomReservations();
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations())));
        for (RoomReservation reservation : roomReservationList) {
            if (reservation.getRoom().equals(this.room)) {
                Entry<RoomReservation> bookedEntry = new Entry<>("This slot is Reserved/Unavailable");

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getRoom().getBuilding().getName());
                bookedEntry.setInterval(date);
                bookedEntry.setInterval(startTime, endTime);

                if (reservation.getAppUser().equals(this.user)) {
                    bookedEntry.setTitle("Reservation #" + reservation.getId());
                    myReservations.addEntry(bookedEntry);
                }
                else {
                    unavailableSpots.addEntry(bookedEntry);
                }
            }
        }
    }

    /**
     * Method that displays the different calendars.
     */
    public void displayCalendars() {
        unavailableSpots.setStyle(Calendar.Style.STYLE5);
        myReservations.setStyle(Calendar.Style.STYLE4);

        unavailableSpots.setReadOnly(true);
//        myReservations.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(myReservations, unavailableSpots);
        this.getCalendarSources().add(myCalendarSource);
    }

    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalTime convertToLocalTime(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDate convertToLocalDate(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }
}