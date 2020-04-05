package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.PersonalCalendar;


public class PersonalCalendarViewController implements Initializable {

    PersonalCalendar calendar = new PersonalCalendar();

    Entry<RoomReservation> roomReservationEntry = null;

    @FXML
    SubScene personalCalContainer;

    public void showPersonalCal() {
        personalCalContainer.setRoot(calendar);
        calendar.setShowAddCalendarButton(false);
        calendar.loadRoomReservations();
        calendar.loadBikeReservations();
        calendar.loadFoodOrders();
        calendar.getCalendars().get(0).addEventHandler(this::removeEntriesAdded);
    }

    /**
     * return to the database main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    public void newWindow() throws IOException {
        ApplicationDisplay.showCalendarScene(calendar);
    }

    public void removeEntriesAdded(CalendarEvent event) {
        event.getEntry().removeFromCalendar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersonalCal();
    }
}
