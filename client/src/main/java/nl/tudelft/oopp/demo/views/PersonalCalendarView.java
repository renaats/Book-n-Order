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
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class PersonalCalendarView extends Application {

    private AppUser currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Calendar bookedRooms = new Calendar("Room Bookings");   //calendar that stores reserved slot entries
        bookedRooms.setStyle(Calendar.Style.STYLE2);                   //sets color of calendar to blue

        Calendar orderedFood = new Calendar("Food Orders");     //calendar that stores food orders
        orderedFood.setStyle(Calendar.Style.STYLE1);                   //sets color of calendar to green

        Calendar rentedBikes = new Calendar("Bike Rent");       //calendar that stores reserved bikes
        rentedBikes.setStyle(Calendar.Style.STYLE3);                   //sets color of calendar to red

        CalendarSource myCalendarSource = new CalendarSource("Calendars"); //source that saves all calendars
        myCalendarSource.getCalendars().removeAll();
        myCalendarSource.getCalendars().addAll(bookedRooms, orderedFood, rentedBikes);

        CalendarView personalCalendar = new CalendarView();  //calendar view
        personalCalendar.getCalendarSources().addAll(myCalendarSource);
        personalCalendar.setRequestedTime(LocalTime.now());  //sets time to current time

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
        //Placeholder for loop to load food orders

        //Placeholder for loop to load bike rentals

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        personalCalendar.setToday(LocalDate.now());
                        personalCalendar.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene = new Scene(personalCalendar);
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