package nl.tudelft.oopp.demo.views;

import java.rmi.ServerError;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class MyCalendarView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        CalendarView weekView = new CalendarView ();
        Calendar birthdays = new Calendar("Birthdays");
        weekView.setShowSearchField(false);
        weekView.setShowPrintButton(false);
        weekView.setShowPageToolBarControls(false);
        weekView.setShowPageSwitcher(true);
        weekView.setShowAddCalendarButton(false);
        weekView.setShowSourceTray(false);


        EventHandler<CalendarEvent> loadEntries = e -> addEntries (e);
        //weekView.addEventHandler(loadEntries);

        birthdays.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays);

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
            };
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
        launch(args);
    }

    private static void addEntries (CalendarEvent e){
        Entry entry = e.getEntry();
        LocalTime startTime = entry.getStartTime();
    }
}

