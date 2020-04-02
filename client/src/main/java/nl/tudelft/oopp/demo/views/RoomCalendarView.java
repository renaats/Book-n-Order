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

    private Room room;
    private AppUser user;
    private String fromTime;
    
    public Calendar unavailableSpots = new Calendar("Unavailable Spots");
    public Calendar myReservations = new Calendar("My Reservations");

    /**
     * Constuctor for the Personal Calendar View
     */
    public RoomCalendarView() {

        displayCalendars();
        loadRoomReservations();
    }


    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList;
        try {
            String roomReservationJson = ServerCommunication.getAllActiveRoomReservations();
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
                myReservations.addEntry(bookedEntry);
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

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(myReservations, unavailableSpots);
        this.getCalendarSources().add(myCalendarSource);
    }

//    public void entryHandler(CalendarEvent e) {
//        Entry<RoomReservation> entry = (Entry<RoomReservation>) e.getEntry();
//
//        Date start = convertToDate(entry.getStartTime(), entry.getStartDate());
//        Date end = convertToDate(entry.getEndTime(), entry.getStartDate());
//
//        if (e.isEntryAdded()) {
//            this.fromTime = entry.getStartTime().toString();
//
//        } else if (e.isEntryRemoved()) {
//
//                this.
//        } else {
//                //ServerCommunication.updateRoomReservation(reservationId, old value, new);
//            }
//        }
//    }

    public LocalTime convertToLocalTime(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDate convertToLocalDate(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }
}