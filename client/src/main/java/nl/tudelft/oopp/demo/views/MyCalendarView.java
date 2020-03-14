package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

import java.time.*;
import java.util.Date;
import java.util.List;

public class MyCalendarView extends Application {

    private static Room room;

    public MyCalendarView(Room room){
        this.room = room;
    }

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

        Calendar booked_rooms = new Calendar("Booked Rooms");

//        EventHandler<CalendarEvent> loadEntries = e -> addEntries (e);
//        //weekView.addEventHandler(loadEntries);

        booked_rooms.setStyle(Style.STYLE3);

        CalendarSource myCalendarSource = new CalendarSource("Calendars");
        myCalendarSource.getCalendars().addAll(booked_rooms);

        weekView.getCalendarSources().addAll(myCalendarSource);
        weekView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
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

    public void addEntries() {
        List<RoomReservation> roomReservationList = JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations());

        for (RoomReservation reservation : roomReservationList) {
            if (reservation.getRoom().equals(this.room)) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Room is booked or unavailable");

                Date start = reservation.getFromTime();
                Instant instant1 = Instant.ofEpochMilli(start.getTime());
                LocalTime startTime = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();

                Date end = reservation.getToTime();
                Instant instant2 = Instant.ofEpochMilli(end.getTime());
                LocalTime endTime = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()).toLocalTime();

                Instant instant3 = Instant.ofEpochMilli(start.getDate());
                LocalDate date = LocalDateTime.ofInstant(instant3, ZoneId.systemDefault()).toLocalDate();

                bookedEntry.setInterval(startTime, endTime);
                bookedEntry.setInterval(date);
            }
        }

    }
}

