package nl.tudelft.oopp.demo.controllers.rooms;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.SelectedRoom;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.RoomCalendarView;

public class BookRoomCalendarController implements Initializable {

    private int roomId;
    private RoomCalendarView calendarView;
    private Entry<RoomReservation> storedEntry = null;
    private BuildingHours buildingHours;

    @FXML
    Button reserveSlot;
    @FXML
    SubScene calendarContainer;
    @FXML
    TextField fromTime;
    @FXML
    TextField untilTime;
    @FXML
    Text scheduleText;
    @FXML
    Text plugNumber;
    @FXML
    Text buildingName;
    @FXML
    Text capacity;
    @FXML
    Text projector;
    @FXML
    Text screen;

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Renders calendar view with all already existing reservations.
     */
    public void showCal() {
        calendarView   = new RoomCalendarView(SelectedRoom.getSelectedRoom().getId());
        calendarView.loadRoomReservations();
        calendarView.getCalendars().get(0).addEventHandler(this::entryHandler);
        calendarView.setShowToolBar(false);
        calendarContainer.setRoot(calendarView);
    }

    /**
     * Fills the text with room information.
     */
    public void displayRoomInformation() {
        scheduleText.setText("Schedule for Room: " + SelectedRoom.getSelectedRoom().getName());
        buildingName.setText(SelectedRoom.getSelectedRoom().getBuilding().getName());
        plugNumber.setText(String.valueOf(SelectedRoom.getSelectedRoom().getPlugs()));
        capacity.setText(String.valueOf(SelectedRoom.getSelectedRoom().getCapacity()));
        projector.setText(String.valueOf(SelectedRoom.getSelectedRoom().isProjector()));
        screen.setText(String.valueOf(SelectedRoom.getSelectedRoom().isScreen()));
    }

    /**
     * Makes text fields read only.
     */
    public void disableFields() {
        fromTime.setDisable(true);
        untilTime.setDisable(true);
    }

    /**
     * Changes to mainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void goToRoomBook() throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    private void entryHandler(CalendarEvent e) {
        Entry<RoomReservation> entry = (Entry<RoomReservation>) e.getEntry();

        Date start = convertToDate(entry.getStartTime(), entry.getStartDate());
        Date end = convertToDate(entry.getEndTime(), entry.getStartDate());
        try {
            String jsonBuildingHours = BuildingServerCommunication.findBuildingHours(SelectedRoom.getSelectedRoom()
                    .getBuilding().getId(), start.getTime());
            buildingHours = JsonMapper.buildingHoursMapper(jsonBuildingHours);
        } catch (Exception exception) {
            buildingHours = null;
        }

        Date startBuildingHours = null;
        Date endBuildingHours = null;

        if (buildingHours != null) {
            startBuildingHours = convertToDate(buildingHours.getStartTime(), entry.getStartDate());
            endBuildingHours = convertToDate(buildingHours.getEndTime(), entry.getStartDate());
        }
        if (e.isEntryAdded()) {
            if (buildingHours == null) {
                e.getEntry().removeFromCalendar();
                CustomAlert.errorAlert("Building is out of service on this day!");
                return;
            } else if (storedEntry != null) {
                e.getEntry().removeFromCalendar();
                CustomAlert.warningAlert("You can make only one reservation at a time.");
                fromTime.setText(convertToDate(storedEntry.getStartTime(), storedEntry.getStartDate()).toString());
                untilTime.setText(convertToDate(storedEntry.getEndTime(), storedEntry.getEndDate()).toString());
            } else if (entry.getStartAsLocalDateTime().isBefore(LocalDateTime.now())) {
                e.getEntry().removeFromCalendar();
                CustomAlert.warningAlert("Unfortunately booking a room in the past is impossible.");
            } else if (start.compareTo(startBuildingHours) < 0 || start.compareTo(endBuildingHours) > 0 || end.compareTo(endBuildingHours) > 0) {
                e.getEntry().removeFromCalendar();
                CustomAlert.warningAlert("Building is closed at this hour.");
            } else {
                entry.setTitle("New Booking");
                fromTime.setText(start.toString());
                untilTime.setText(end.toString());
                reserveSlot.arm();
                storedEntry = (Entry<RoomReservation>) e.getEntry();
            }
        } else if (e.isEntryRemoved()) {
            if (e.getEntry().equals(storedEntry)) {
                fromTime.setText("");
                untilTime.setText("");
                reserveSlot.disarm();
                storedEntry = null;
            } else {
                fromTime.setText("");
                untilTime.setText("");
                reserveSlot.disarm();
            }

        } else {
            fromTime.setText(start.toString());
            untilTime.setText(end.toString());
        }
    }

    /**
     * Handles communicating the new entry to the server.
     */
    public void addReservation() {
        String dateFrom = fromTime.getText();
        String dateUntil = untilTime.getText();

        if (dateFrom.equals("") || dateUntil.equals("")) {
            CustomAlert.warningAlert("No slot selected");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date from = format.parse(dateFrom);
                Date until = format.parse(dateUntil);
                long milliseconds1 = from.getTime();
                long milliseconds2 = until.getTime();

                if (RoomServerCommunication.addRoomReservation(SelectedRoom.getSelectedRoom().getId(), milliseconds1, milliseconds2)
                        .equals(ErrorMessages.getErrorMessage(308))) {
                    CustomAlert.warningAlert("Slot is already booked. Please make sure you do not overlay another reservation.");
                } else {
                    ApplicationDisplay.changeSceneWithVariables("/roomConfirmation.fxml", milliseconds1, milliseconds2);
                }
            } catch (ParseException | IOException e) {
                CustomAlert.errorAlert("Something went wrong! Could not reserve slot.");
            }
        }
    }

    /**
     * Converts LocalTime and LocalDate into Date object.
     * @param time = the hour, minute and seconds of the slot.
     * @param date = the date of the slot.
     * @return Date object that holds both components time and date.
     */
    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reserveSlot.disarm();
        showCal();
        disableFields();
        displayRoomInformation();
    }
}
