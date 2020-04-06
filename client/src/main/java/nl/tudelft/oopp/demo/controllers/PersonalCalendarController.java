package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.PersonalCalendarView;

/**
 * Loads the user's Personal Calendar into the application template and handles events regarding it.
 */
public class PersonalCalendarController implements Initializable {

    PersonalCalendarView calendar = new PersonalCalendarView();

    Entry<RoomReservation> roomReservationEntry = null;

    @FXML
    SubScene personalCalContainer;

    /**
     * Renders the personal calendar into the scene.
     */
    public void showPersonalCal() {
        personalCalContainer.setRoot(calendar);
        calendar.setShowAddCalendarButton(false);
        calendar.loadRoomReservations();
        calendar.loadBikeReservations();
        calendar.loadFoodOrders();
        calendar.getCalendars().get(0).addEventHandler(this::removeEntriesAdded);
    }

    /**
     * Return to the main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Opens the calendar on a new window.
     * @throws IOException this should not throw an exception.
     */
    public void newWindow() throws IOException {
        ApplicationDisplay.showCalendarScene(calendar);
    }

    /**
     * Leads to the BookRoom scene.
     * @throws IOException this should not throw an exception.
     */
    public void goToBookRoom() throws IOException {
        ApplicationDisplay.changeScene("/BookRoom.fxml");
    }

    /**
     * Leads to the FoodOrder scene.
     * @throws IOException this should not throw an exception.
     */
    public void goToFoodOrder() throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodPickDate.fxml");
    }

    /**
     * Leads to the BikeReservation scene.
     * @throws IOException this should not throw an exception.
     */
    public void goToBikeReservation() throws IOException {
        ApplicationDisplay.changeScene("/BikeReservations.fxml");
    }

    /**
     * Disables new entries in the calendar.
     * @param event = the event that is being removed.
     */
    public void removeEntriesAdded(CalendarEvent event) {
        event.getEntry().removeFromCalendar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersonalCal();
    }
}
