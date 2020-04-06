package nl.tudelft.oopp.demo.controllers.bikes;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

import java.net.URL;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

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
    private Text dayOfWeekPickUp;
    @FXML
    private Text pickUpTime;
    @FXML
    private Text dayOfMonthDropOff;
    @FXML
    private Text dayOfWeekDropOff;
    @FXML
    private Text dropOffTime;
    @FXML
    private Text buildingNameOne;
    @FXML
    private Text buildingNameTwo;

    /**
     * Instantiates a BikeReservationController
     * @param ints array holding the id's of the buildings
     * @param longs array holding the two dates in longs
     * @throws JsonProcessingException we know these id's are valid thus we throw
     */
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

    /**
     * Loads the information of the bike reservation
     */
    public void loadInformation() {
        dayOfMonthPickUp.setText(String.valueOf(pickUp.getDayOfMonth()));
        String month = parseMonth(pickUp.getMonthValue());
        dayOfMonthPickUp.setText(dayOfMonthPickUp.getText() + " " + month);
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
                dayOfWeekDropOff.setText("No such day of the week");
        }
        dayOfWeekPickUp.setText(dayOfWeekPickUp.getText() + " " + pickUp.toLocalTime().toString());
        dayOfMonthDropOff.setText(String.valueOf(dropOff.getDayOfMonth()));
        month = parseMonth(dropOff.getMonthValue());
        dayOfMonthDropOff.setText(dayOfMonthDropOff.getText() + " " + month);
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
        dayOfWeekDropOff.setText(dayOfWeekDropOff.getText() + " " + dropOff.toLocalTime().toString());
        buildingNameOne.setText(building1.getName() + ",\n" + building1.getStreet() + " " + building1.getHouseNumber() + ",\nDelft, The Netherlands");
        buildingNameTwo.setText(building2.getName() + ",\n" + building2.getStreet() + " " + building2.getHouseNumber() + ",\nDelft, The Netherlands");
    }

    /**
     * Parses a 1-12 to month in String
     * @param monthByInt the number to parse
     * @return String representation of the month
     */
    public String parseMonth(int monthByInt) {
        String month;
        switch (monthByInt) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "No such month";
        }
        return month;
    }

    /**
     * Initialises the fields upon loading the fxml file
     * @param location the location from which the fxml is called
     * @param resources holding specific information about the user such as locale
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInformation();
    }
}
