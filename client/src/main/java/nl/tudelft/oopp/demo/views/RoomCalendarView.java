package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RoomCalendarView extends Application {

    private Room room;
    private int buildingId;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CalendarView weekView = new CalendarView();
        weekView.setShowSearchField(false);
        weekView.setShowPrintButton(false);
        weekView.setShowPageToolBarControls(false);
        weekView.setShowPageSwitcher(true);
        weekView.setShowAddCalendarButton(false);
        weekView.setShowSourceTray(false);
        weekView.setFocusTraversable(false);

        Calendar bookedSlotsCalendar = new Calendar("Unavailable Slots"); //calendar that stores reserved slot entries
        bookedSlotsCalendar.setStyle(Style.STYLE2); //sets color of calendar to blue
        bookedSlotsCalendar.setReadOnly(true); //disables any user modification to the already reserved slot entries

        Calendar myBookingCalendar = new Calendar("My Bookings");
        myBookingCalendar.setStyle(Style.STYLE1);
        EventHandler<CalendarEvent> handler = e -> entryHandler(e);
        myBookingCalendar.addEventHandler(handler);

        List<RoomReservation> roomReservationList = JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations());
                if (roomReservationList != null && !roomReservationList.isEmpty()) {
                    for (RoomReservation reservation : roomReservationList) {
                        if (reservation.getRoom().equals(this.room)) {
                            Entry<RoomReservation> bookedEntry = new Entry<>("Room is booked or unavailable");

                            LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                            LocalTime endTime = convertToLocalTime(reservation.getToTime());
                            LocalDate date = convertToLocalDate(reservation.getFromTime());

                            bookedEntry.setInterval(startTime, endTime);
                            bookedEntry.setInterval(date);
                            bookedSlotsCalendar.addEntry(bookedEntry);
                        }
                    }
                }

        CalendarSource myCalendarSource = new CalendarSource("Calendars");
        myCalendarSource.getCalendars().removeAll();
        myCalendarSource.getCalendars().addAll(bookedSlotsCalendar, myBookingCalendar);


        weekView.getCalendarSources().addAll(myCalendarSource);
        weekView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while(true) {
                    Platform.runLater(() -> {
                        weekView.setToday(LocalDate.now());
                        weekView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            ;
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();


        Scene scene = new Scene(weekView);
        primaryStage.setTitle("Room Bookings");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void entryHandler(CalendarEvent e) {
        Entry<RoomReservation> newEntry = (Entry<RoomReservation>)e.getEntry();

        Date start = convertToDate(newEntry.getStartTime(), newEntry.getStartDate());
        Date end = convertToDate(newEntry.getEndTime(), newEntry.getStartDate());

        if (e.isEntryAdded()) {
            //ServerCommunication.addRoomReservation(this.room.getName(), this.room.getBuilding().getId(), userId, start, end);
        }
        else if (e.isEntryRemoved()){ System.out.println("entry removed");
            //ServerCommunication.deleteRoomReservation(e.getEntry().ge);
        } else {
            //ServerCommunication.updateRoomReservation(reservationId, old value, new);
        }
    }

    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalTime convertToLocalTime (Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDate convertToLocalDate (Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }
}



