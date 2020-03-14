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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class RoomCalendarView extends Application {

    private Room room;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CalendarView roomCal = new CalendarView();
        roomCal.setShowSearchField(false);
        roomCal.setShowPrintButton(false);
        roomCal.setShowPageToolBarControls(false);
        roomCal.setShowPageSwitcher(true);
        roomCal.setShowAddCalendarButton(false);
        roomCal.setShowSourceTray(false);
        roomCal.setFocusTraversable(false);

        Calendar bookedSlotsCalendar = new Calendar("Unavailable Slots"); //calendar that stores reserved slot entries
        bookedSlotsCalendar.setStyle(Style.STYLE2); //sets color of calendar to blue
        bookedSlotsCalendar.setReadOnly(true); //disables any user modification to the already reserved slot entries

        Calendar myBookingCalendar = new Calendar("My Bookings");
        myBookingCalendar.setStyle(Style.STYLE1);

        List<RoomReservation> roomReservationList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations())));

        for (RoomReservation reservation : roomReservationList) {
            //if (reservation.getRoom().equals(this.room)) {
            Entry<RoomReservation> bookedEntry = new Entry<>("Room is booked or unavailable");

            LocalTime startTime = convertToLocalTime(reservation.getFromTime());
            LocalTime endTime = convertToLocalTime(reservation.getToTime());
            LocalDate date = convertToLocalDate(reservation.getFromTime());
            bookedEntry.setInterval(date);
            bookedEntry.setInterval(startTime, endTime);
            bookedSlotsCalendar.addEntry(bookedEntry);
        }


        CalendarSource myCalendarSource = new CalendarSource("Calendars");
        myCalendarSource.getCalendars().removeAll();
        myCalendarSource.getCalendars().addAll(bookedSlotsCalendar, myBookingCalendar);

        roomCal.getCalendarSources().addAll(myCalendarSource);
        roomCal.setRequestedTime(LocalTime.now());


        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {

                        roomCal.setToday(LocalDate.now());
                        roomCal.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene = new Scene(roomCal);
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

    public LocalTime convertToLocalTime(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDate convertToLocalDate(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }
}

