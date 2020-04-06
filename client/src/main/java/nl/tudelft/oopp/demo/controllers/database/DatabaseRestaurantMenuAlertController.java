package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.errors.CustomAlert;

/**
 * Contains all of the update methods from DatabaseRestaurantMenuController.
 */
public class DatabaseRestaurantMenuAlertController {

    /**
     * Takes care of all the update values for a dish.
     */
    public static void updateDishAlert(TextField dishIdFieldRead, TextField dishNameFieldRead, TextField dishPriceFieldRead,
                                      TextField dishImageTextField, TextArea dishDescriptionFieldRead, DatabaseRestaurantMenuController controller) {
        int dishId = Integer.parseInt(dishIdFieldRead.getText());
        // Checks again if all fields and values are correct. And gives the user the appropriate error if not.
        try {
            double price = -1;
            if (dishNameFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a name.");
                return;
            } else {
                String name = dishNameFieldRead.getText();
                RestaurantServerCommunication.updateDish(dishId, "name", name);
            }
            if (dishPriceFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a price.");
                return;
            } else {
                try {
                    price = Double.parseDouble(dishPriceFieldRead.getText());
                    RestaurantServerCommunication.updateDish(dishId, "price", Integer.toString((int) (price * 100)));
                } catch (NumberFormatException e) {
                    CustomAlert.warningAlert("Price requires a number.");
                    return;
                }
            }

            // A image is optional, it doesn't have to be filled in, hence there are no checks regarding it.
            RestaurantServerCommunication.updateDish(dishId, "image", dishImageTextField.getText());

            if (dishDescriptionFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a description.");
                return;
            } else {
                String description = dishDescriptionFieldRead.getText();
                RestaurantServerCommunication.updateDish(dishId, "description", description);
            }
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
            return;
        }
        CustomAlert.informationAlert("Successfully executed.");
        controller.retrieveAllDishes();
    }

    /**
     * Takes care of updating restaurant
     */
    public static void updateRestaurantAlert(TextField idFieldRead, TextField locationFieldRead, TextField nameFieldRead,
                                             TextField ownerTextField, DatabaseRestaurantMenuController controller) {
        int buildingId;
        boolean buildingFound = false;

        if (idFieldRead.getText().isEmpty()) {
            CustomAlert.warningAlert("No selection detected.");
            return;
        }

        // Executes various checks to show the proper alerts to a user
        try {
            buildingId = Integer.parseInt(locationFieldRead.getText());
        } catch (NumberFormatException e) {
            Building building = null;
            if (!locationFieldRead.getText().equals("")) {
                try {
                    building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(locationFieldRead.getText()));
                } catch (JsonProcessingException ex) {
                    CustomAlert.errorAlert("Building not found.");
                }
            } else {
                CustomAlert.warningAlert("Please provide a building.");
                return;
            }
            if (building == null) {
                CustomAlert.errorAlert("Building not found");
                return;
            } else {
                buildingId = building.getId();
                buildingFound = true;
            }
        }

        // If there's actually a building, then start executing the update methods.
        // More if checks, to show the proper alert to the user so we are as clear as possible.
        if (buildingFound) {
            if (nameFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a name.");
                return;
            } else {
                RestaurantServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "name", nameFieldRead.getText());
            }
            if (locationFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a building.");
                return;
            } else {
                RestaurantServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "building", Integer.toString(buildingId));
            }
            if (ownerTextField.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a owner.");
                return;
            } else {
                RestaurantServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "email", ownerTextField.getText());
            }
        } else {
            CustomAlert.warningAlert("Building not found.");
            return;
        }
        controller.retrieveOwnedRestaurants();
        CustomAlert.informationAlert("Successfully executed.");
    }

    /**
     * Takes care of updating restaurant hours
     */
    public static void updateRestaurantHoursAlert(ChoiceBox<String> daysChoiceBox, DatePicker datePicker, TextField idFieldRead,
                                                  TextField hoursEndTime, TextField minutesEndTime, TextField hoursStartTime,
                                                  TextField minutesStartTime, int restaurantId) {
        int day = 0;
        // Long list of if statements checking for various things such as
        // If all values are there, if the values are not out of bounds and if the values are viable to use.
        try {
            if (daysChoiceBox.getValue() == null && datePicker.getValue() == null) {
                CustomAlert.errorAlert("Please select either a day or a date.");
                daysChoiceBox.setValue(null);
                datePicker.setValue(null);
            } else if (idFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("No selection detected.");
            } else if (minutesStartTime.getText().isEmpty()
                    || minutesEndTime.getText().isEmpty()
                    || hoursStartTime.getText().isEmpty()
                    || hoursEndTime.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide opening and closing time.");
            } else if (Integer.parseInt(hoursStartTime.getText()) > 23 || Integer.parseInt(hoursEndTime.getText()) > 23) {
                CustomAlert.warningAlert("Hours cannot be larger than 23.");
            } else if (Integer.parseInt(minutesStartTime.getText()) > 59 || Integer.parseInt(minutesEndTime.getText()) > 59) {
                CustomAlert.warningAlert("Minutes cannot be larger than 59.");
            } else if (datePicker.getValue() == null) {
                day = daySwitchCase(daysChoiceBox);
                try {
                    int startTime = Integer.parseInt(hoursStartTime.getText()) * 3600 + Integer.parseInt(minutesStartTime.getText()) * 60;
                    int endTime = Integer.parseInt(hoursEndTime.getText()) * 3600 + Integer.parseInt(minutesEndTime.getText()) * 60;
                    if (startTime > endTime) {
                        CustomAlert.errorAlert("Opening hours cannot be later than closing hours.");
                        return;
                    }
                    try {
                        // Checks if there are already building hours there, if there are not this generates a JsonProcessingException that
                        // that is then caught and instead of adding, we update the hours.
                        // This is put in place because there's no explicit add building hours button or page.
                        RestaurantHours restaurantHours = JsonMapper.restaurantHoursMapper(
                                RestaurantServerCommunication.findRestaurantHoursByDay(restaurantId, day));

                        String response = RestaurantServerCommunication.updateRestaurantHours(
                                restaurantHours.getId(), "starttimes", Integer.toString(startTime));
                        if (!response.equals("Successfully executed.")) {
                            CustomAlert.errorAlert(response);
                            return;
                        }
                        response = RestaurantServerCommunication.updateRestaurantHours(
                                restaurantHours.getId(), "endtimes", Integer.toString(endTime));
                        if (!response.equals("Successfully executed.")) {
                            CustomAlert.errorAlert(response);
                            return;
                        }
                        CustomAlert.informationAlert("Successfully executed.");
                        // If exception, update building
                    } catch (JsonProcessingException e) {
                        System.out.println(restaurantId);
                        e.printStackTrace();
                        CustomAlert.informationAlert(RestaurantServerCommunication.addRestaurantHours(restaurantId, day, startTime, endTime));
                    }
                } catch (NumberFormatException ex) {
                    CustomAlert.warningAlert("Restaurant hours have to be an integer.");
                }
                // If the day choice box is empty, take the date picker and update it that way.
            } else {
                LocalDate date = datePicker.getValue();
                Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
                long dateInMs = instant.toEpochMilli();
                try {
                    int startTime = Integer.parseInt(hoursStartTime.getText()) * 3600 + Integer.parseInt(minutesStartTime.getText()) * 60;
                    int endTime = Integer.parseInt(hoursEndTime.getText()) * 3600 + Integer.parseInt(minutesEndTime.getText()) * 60;
                    if (startTime > endTime) {
                        CustomAlert.errorAlert("Opening hours cannot be later than closing hours.");
                        return;
                    }
                    CustomAlert.informationAlert(RestaurantServerCommunication.addRestaurantHours(restaurantId, dateInMs, startTime, endTime));
                } catch (NumberFormatException e) {
                    CustomAlert.warningAlert("Restaurant hours have to be an integer.");
                }
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Restaurant hours have to be an integer.");
        }
    }

    /**
     * Turns the day string into a number.
     * @return the day as a number.
     */
    public static int daySwitchCase(ChoiceBox<String> daysChoiceBox) {
        switch (daysChoiceBox.getValue()) {
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            case "Sunday":
                return 7;
            default:
                CustomAlert.errorAlert("Day not recognized.");
                daysChoiceBox.setValue(null);
        }
        return 0;
    }

    public static void disable(TextField textField) {
        textField.setDisable(true);
        textField.setOpacity(0.75);
    }
}
