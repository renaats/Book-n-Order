package nl.tudelft.oopp.demo.controllers.rooms;

import java.io.IOException;

import java.net.URL;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;

import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.SelectedRoom;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "roomConfirmation.fxml" file
 */
public class RoomConfirmationController implements Initializable {
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
    @FXML
    private Text buildingName;
    @FXML
    private Text roomName;
    @FXML
    private Text address;

    public RoomConfirmationController(Date start1, Date end1) {
        this.start = LocalDateTime.ofInstant(start1.toInstant(), ZoneId.systemDefault());
        this.end = LocalDateTime.ofInstant(end1.toInstant(), ZoneId.systemDefault());
    }



    /**
     * will change to main menu when the background is clicked
     * @throws IOException should never throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Loads the information of the room reservation.
     */
    public void loadInformation() {
        dayOfMonth.setText(String.valueOf(start.getDayOfMonth()));
        int intMonth = start.getMonthValue();
        switch (intMonth) {
            case 1:
                month.setText("January");
                break;
            case 2:
                month.setText("February");
                break;
            case 3:
                month.setText("March");
                break;
            case 4:
                month.setText("April");
                break;
            case 5:
                month.setText("May");
                break;
            case 6:
                month.setText("June");
                break;
            case 7:
                month.setText("July");
                break;
            case 8:
                month.setText("August");
                break;
            case 9:
                month.setText("September");
                break;
            case 10:
                month.setText("October");
                break;
            case 11:
                month.setText("November");
                break;
            case 12:
                month.setText("December");
                break;
            default:
                month.setText("No such month.");
        }
        int weekday = start.getDayOfWeek().getValue();
        switch (weekday) {
            case 1:
                dayOfWeek.setText("Mon");
                break;
            case 2:
                dayOfWeek.setText("Tue");
                break;
            case 3:
                dayOfWeek.setText("Wed");
                break;
            case 4:
                dayOfWeek.setText("Thu");
                break;
            case 5:
                dayOfWeek.setText("Fri");
                break;
            case 6:
                dayOfWeek.setText("Sat");
                break;
            case 7:
                dayOfWeek.setText("Sun");
                break;
            default:
                dayOfWeek.setText("No such day of the week");
        }
        startTime.setText(start.toLocalTime().toString() + "\n   to\t" + end.toLocalTime().toString());
        roomName.setText(SelectedRoom.getSelectedRoom().getName());
        buildingName.setText(SelectedRoom.getSelectedRoom().getBuilding().getName());
        address.setText(SelectedRoom.getSelectedRoom().getBuilding().getHouseNumber() + " "
                + SelectedRoom.getSelectedRoom().getBuilding().getStreet() + ", Delft, The Netherlands");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInformation();
    }
}