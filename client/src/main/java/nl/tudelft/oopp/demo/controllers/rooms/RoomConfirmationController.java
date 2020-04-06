package nl.tudelft.oopp.demo.controllers.rooms;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.SelectedRoom;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Date;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "roomConfirmation.fxml" file
 */
public class RoomConfirmationController {
    private LocalDateTime start;
    private LocalDateTime end;
    @FXML
    private Text dayOfMonth;
    @FXML
    private Text month;
    @FXML
    private Text dayOfWeek;
    @FXML
    private Text startTime;
//    @FXML
//    private Text endTime;
    @FXML
    private Text roomName;
    @FXML
    private Text address;

    public RoomConfirmationController(Date start1, Date end1) {
        this.start = new java.sql.Timestamp(start1.getTime()).toLocalDateTime();
        this.end = new java.sql.Timestamp(end1.getTime()).toLocalDateTime();
        dayOfMonth.setText(String.valueOf(start.getDayOfMonth()));
        int intMonth = start.getMonthValue();
        switch (intMonth) {
            case 1:
                month.setText("January");
            case 2:
                month.setText("February");
            case 3:
                month.setText("March");
            case 4:
                month.setText("April");
            case 5:
                month.setText("May");
            case 6:
                month.setText("June");
            case 7:
                month.setText("July");
            case 8:
                month.setText("August");
            case 9:
                month.setText("September");
            case 10:
                month.setText("October");
            case 11:
                month.setText("November");
            case 12:
                month.setText("December");
            default:
                month.setText("No such month.");
        }
        int weekday = start.getDayOfWeek().getValue();
        switch (weekday) {
            case 1:
                dayOfWeek.setText("Mon");
            case 2:
                dayOfWeek.setText("Tue");
            case 3:
                dayOfWeek.setText("Wed");
            case 4:
                dayOfWeek.setText("Thu");
            case 5:
                dayOfWeek.setText("Fri");
            case 6:
                dayOfWeek.setText("Sat");
            case 7:
                dayOfWeek.setText("Sun");
            default:
                dayOfWeek.setText("No such day of the week");
        }
        startTime.setText(start.toLocalTime().toString() + "\nto\t" + end.toLocalTime().toString());
        roomName.setText(SelectedRoom.getSelectedRoom().getName());
        address.setText(SelectedRoom.getSelectedRoom().getBuilding().getName() + ", " + SelectedRoom.getSelectedRoom().getBuilding().getHouseNumber() + " "
                + SelectedRoom.getSelectedRoom().getBuilding().getStreet() + ", Delft, The Netherlands");
    }

    /**
     * will change to main menu when the background is clicked
     * @throws IOException should never throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
