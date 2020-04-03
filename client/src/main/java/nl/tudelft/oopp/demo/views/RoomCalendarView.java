package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
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

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class RoomCalendarView extends CalendarView {

    private int roomId;

    public Calendar unavailableSpots = new Calendar("Unavailable Spots");

    /**
     * Constuctor for the Personal Calendar View
     */
    public RoomCalendarView(int roomId) {
        this.roomId = roomId;
        displayCalendars();
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Method that displays the different calendars.
     */
    public void displayCalendars() {
        unavailableSpots.setStyle(Calendar.Style.STYLE5);
        unavailableSpots.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(unavailableSpots);
        this.getCalendarSources().add(myCalendarSource);
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList;
        try {
            String roomReservationJson = ServerCommunication.findReservationForRoom(roomId);
            roomReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(roomReservationJson)));
        } catch (NullPointerException e) {
            roomReservationList = new ArrayList<>();
            roomReservationList.add(null);
        }

        for (RoomReservation reservation : roomReservationList) {
            if (reservation != null) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Booking of Room: " + reservation.getRoom().getName());
                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getRoom().getBuilding().getName());
                bookedEntry.setInterval(date);
                bookedEntry.setInterval(startTime, endTime);
                unavailableSpots.addEntry(bookedEntry);
            }
        }
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