package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.calendarfx.model.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.PersonalCalendar;


public class PersonalCalendarViewController implements Initializable {

    PersonalCalendar calendar = new PersonalCalendar();

    @FXML
    SubScene personalCalContainer;

    public void showPersonalCal() {
        personalCalContainer.setRoot(calendar);
        calendar.setShowAddCalendarButton(false);
        calendar.loadRoomReservations();
        calendar.loadBikeReservations();
        calendar.loadFoodOrders();
        calendar.getCalendars().get(1).addEventHandler(this::onSelect);
    }

    /**
     * return to the database main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    public void onSelect(CalendarEvent event) {
        System.out.println(event.isConsumed());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersonalCal();
    }
}
