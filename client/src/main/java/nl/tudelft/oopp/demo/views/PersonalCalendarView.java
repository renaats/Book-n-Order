package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import java.beans.EventHandler;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class PersonalCalendarView extends CalendarView {

    private AppUser currentUser;

    Calendar bookedRooms = new Calendar("Room Bookings");
    Calendar orderedFood = new Calendar("Food Orders");
    Calendar rentedBikes = new Calendar("Bike Rent");

    /**
     * Constuctor for the Personal Calendar View
     */
    public PersonalCalendarView() {

        displayCalendars();
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations())));
        for (RoomReservation reservation : roomReservationList) {
            //if (reservation.getAppUser().equals(this.currentUser)) {
            Entry<RoomReservation> bookedEntry = new Entry<>("Booking of " + reservation.getRoom().getName());

            LocalTime startTime = convertToLocalTime(reservation.getFromTime());
            LocalTime endTime = convertToLocalTime(reservation.getToTime());
            LocalDate date = convertToLocalDate(reservation.getFromTime());

            bookedEntry.setInterval(startTime, endTime);
            bookedEntry.setInterval(date);
            bookedRooms.addEntry(bookedEntry);
            //}
        }
    }

    //method to load room reservations

    //method to load bike rentals

    /**
     * Method that displays the different calendars.
     */
    public void displayCalendars() {
        bookedRooms.setStyle(Calendar.Style.STYLE2);
        orderedFood.setStyle(Calendar.Style.STYLE3);
        rentedBikes.setStyle(Calendar.Style.STYLE4);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(bookedRooms, orderedFood, rentedBikes);
        this.getCalendarSources().add(myCalendarSource);

        CalendarView view = new CalendarView();
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