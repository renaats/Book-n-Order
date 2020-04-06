package nl.tudelft.oopp.demo.controllers.restaurants;

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
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "FoodConfirmation.fxml" file
 */
public class FoodReservationConfirmationController implements Initializable {
    private LocalDateTime dateOfOrder;
    private String[] choice;
    @FXML
    private Text dayOfMonth;
    @FXML
    private Text month;
    @FXML
    private Text dayOfWeek;
    @FXML
    private Text deliveryTime;
    @FXML
    private Text restaurantName;
    @FXML
    private Text address;

    public FoodReservationConfirmationController(long dateInLong, String[] strings) {
        dateOfOrder = LocalDateTime.ofInstant(new Date(dateInLong).toInstant(), ZoneId.systemDefault());
        choice = strings;
    }

    /**
     * Changes the scene to the main mane when the background image is clicked
     * @throws IOException the input is always the same, so it should not throw and exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Loads the information of the food order
     * @throws JsonProcessingException We know this is a valid id, thus we throw
     */
    public void loadInformation() throws JsonProcessingException {
        dayOfMonth.setText(String.valueOf(dateOfOrder.getDayOfMonth()));
        int monthInt = dateOfOrder.getMonthValue();
        switch (monthInt) {
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
        int weekday = dateOfOrder.getDayOfWeek().getValue();
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
        deliveryTime.setText(dateOfOrder.toLocalTime().toString());
        Restaurant restaurant = JsonMapper.restaurantMapper(RestaurantServerCommunication.findRestaurantByName(choice[1]));
        restaurantName.setText(restaurant.getName());
        if (choice[0].equals("Pick Up")) {
            address.setText(restaurant.getName() + ", " + restaurant.getBuilding().getName() + ",\n" + restaurant.getBuilding().getStreet() + " "
                    + restaurant.getBuilding().getHouseNumber() + ",\nDelft, The Netherlands");
        } else {
            int buildingId = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(choice[0])).getId();
            Building building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(buildingId));
            address.setText(building.getName() + ",\n" + building.getStreet() + " " + building.getHouseNumber()
                    + ",\nDelft, The Netherlands");
        }
    }

    /**
     * Initialises the fields upon loading the fxml file
     * @param location the location from which the fxml is called
     * @param resources holding specific information about the user such as locale
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadInformation();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
