package nl.tudelft.oopp.demo.controllers.bikes;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "bikeConfirmations.fxml" file
 */
public class BikeReservationConfirmationController implements Initializable {
    private LocalDateTime pickUp;
    private LocalDateTime dropOff;
    private Building building1;
    private Building building2;
    @FXML
    private Text dayOfMonthPickUp;
    @FXML
    private Text monthPickUp;
    @FXML
    private Text dayOfWeekPickUp;
    @FXML
    private Text pickUpTime;
    @FXML
    private Text dayOfMonthDropOff;
    @FXML
    private Text monthDropOff;
    @FXML
    private Text dayOfWeekDropOff;
    @FXML
    private Text dropOffTime;
    @FXML
    private Text buildingNameOne;
    @FXML
    private Text buildingNameTwo;

    public BikeReservationConfirmationController(int[] ints, long[] longs) throws JsonProcessingException {
        pickUp = LocalDateTime.ofInstant(new Date(longs[0]).toInstant(), ZoneId.systemDefault());
        dropOff = LocalDateTime.ofInstant(new Date(longs[1]).toInstant(), ZoneId.systemDefault());
        building1 = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(ints[0]));
        building2 = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(ints[1]));
    }

    /**
     * when you click on the background it takes you back to the main menu
     * @throws IOException the input is always the same, so it should not throw an IOException
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    public void loadInformation() {
        dayOfMonthPickUp.setText(String.valueOf(pickUp.getDayOfMonth()));
        int intMonthPickUp = pickUp.getMonthValue();
        switch (intMonthPickUp) {
            case 1:
                monthPickUp.setText("January");
                break;
            case 2:
                monthPickUp.setText("February");
                break;
            case 3:
                monthPickUp.setText("March");
                break;
            case 4:
                monthPickUp.setText("April");
                break;
            case 5:
                monthPickUp.setText("May");
                break;
            case 6:
                monthPickUp.setText("June");
                break;
            case 7:
                monthPickUp.setText("July");
                break;
            case 8:
                monthPickUp.setText("August");
                break;
            case 9:
                monthPickUp.setText("September");
                break;
            case 10:
                monthPickUp.setText("October");
                break;
            case 11:
                monthPickUp.setText("November");
                break;
            case 12:
                monthPickUp.setText("December");
                break;
            default:
                monthPickUp.setText("No such month.");
        }
        int weekdayPickUp = pickUp.getDayOfWeek().getValue();
        switch (weekdayPickUp) {
            case 1:
                dayOfWeekPickUp.setText("Mon");
                break;
            case 2:
                dayOfWeekPickUp.setText("Tue");
                break;
            case 3:
                dayOfWeekPickUp.setText("Wed");
                break;
            case 4:
                dayOfWeekPickUp.setText("Thu");
                break;
            case 5:
                dayOfWeekPickUp.setText("Fri");
                break;
            case 6:
                dayOfWeekPickUp.setText("Sat");
                break;
            case 7:
                dayOfWeekPickUp.setText("Sun");
                break;
            default:
                dayOfWeekPickUp.setText("No such day of the week");
        }
        dropOffTime.setText(dropOff.toLocalTime().toString());
        dayOfMonthDropOff.setText(String.valueOf(dropOff.getDayOfMonth()));
        int intMonthDropOff = dropOff.getMonthValue();
        switch (intMonthDropOff) {
            case 1:
                monthDropOff.setText("January");
                break;
            case 2:
                monthDropOff.setText("February");
                break;
            case 3:
                monthDropOff.setText("March");
                break;
            case 4:
                monthDropOff.setText("April");
                break;
            case 5:
                monthDropOff.setText("May");
                break;
            case 6:
                monthDropOff.setText("June");
                break;
            case 7:
                monthDropOff.setText("July");
                break;
            case 8:
                monthDropOff.setText("August");
                break;
            case 9:
                monthDropOff.setText("September");
                break;
            case 10:
                monthDropOff.setText("October");
                break;
            case 11:
                monthDropOff.setText("November");
                break;
            case 12:
                monthDropOff.setText("December");
                break;
            default:
                monthDropOff.setText("No such month.");
        }
        int weekdayDropOff = dropOff.getDayOfWeek().getValue();
        switch (weekdayDropOff) {
            case 1:
                dayOfWeekDropOff.setText("Mon");
                break;
            case 2:
                dayOfWeekDropOff.setText("Tue");
                break;
            case 3:
                dayOfWeekDropOff.setText("Wed");
                break;
            case 4:
                dayOfWeekDropOff.setText("Thu");
                break;
            case 5:
                dayOfWeekDropOff.setText("Fri");
                break;
            case 6:
                dayOfWeekDropOff.setText("Sat");
                break;
            case 7:
                dayOfWeekDropOff.setText("Sun");
                break;
            default:
                dayOfWeekDropOff.setText("No such day of the week");
        }
        dropOffTime.setText(dropOff.toLocalTime().toString());
        buildingNameOne.setText(building1.getName() + "\n" + building1.getStreet() + " " + building1.getHouseNumber() + "\nDelft, The Netherlands");
        buildingNameTwo.setText(building2.getName() + "\n" + building2.getStreet() + " " + building2.getHouseNumber() + "\nDelft, The Netherlands");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInformation();
    }
}
