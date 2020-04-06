package nl.tudelft.oopp.demo.controllers.restaurants;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "OrderFoodPickDate.fxml" file
 */
public class OrderFoodController implements Initializable {
    final ObservableList<String> listTime = FXCollections.observableArrayList();
    final ObservableList<String> listMinutes = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();
    List<Dish> dishes;
    Restaurant restaurant;

    @FXML
    public ComboBox<String> pickUpTimeMin;
    @FXML
    public ComboBox<String> pickUpTimeH;
    @FXML
    public ComboBox<String> buildingChoiceBox;
    @FXML
    public DatePicker orderFoodDate;
    public Label explanationOfTheRestaurantText;

    public OrderFoodController(List<Dish> dishes, Restaurant restaurant) {
        this.dishes = dishes;
        this.restaurant = restaurant;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        loadBuildingChoiceBox();
    }

    /**
     * Takes care of the options for the buildingChoiceBox in the GUI
     */
    private void loadBuildingChoiceBox() {
        buildingNameList.clear();
        try {
            for (Building building: Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings()))) {
                buildingNameList.add(building.getName());
            }
            buildingNameList.add("Pick Up");
        } catch (Exception e) {
            buildingNameList.add("Pick Up");
        }
        buildingChoiceBox.getItems().addAll(buildingNameList);
        buildingChoiceBox.setValue("Pick Up");
    }

    /**
     * loads the content of the label depending on the restaurant
     * and loads the times into the ComboBoxes
     */
    public void loadData() {
        loadTime();
    }

    /**
     * loads the time to the choice boxes
     */
    public void loadTime() {
        listTime.clear();
        listMinutes.clear();
        for (int i = 0;i <= 45; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(((Integer) i).toString());
            }
        }
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                listTime.add("0" + i);
            } else {
                listTime.add(((Integer) i).toString());
            }
        }
        pickUpTimeH.getItems().addAll(listTime);
        pickUpTimeMin.getItems().addAll(listMinutes);
    }

    /**
     * Changes to MainMenuReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @throws IOException the input will always be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * Changes to orderFoodConfirmation.fxml.
     */
    public void goToFoodOrderConfirmation() {
        if (orderFoodDate.getValue() == null) {
            CustomAlert.warningAlert("Choose a date.");
        } else if (pickUpTimeH.getValue() == null || pickUpTimeMin.getValue() == null) {
            CustomAlert.warningAlert("Choose a time.");
        } else {
            try {
                long dateLong = Date.from(orderFoodDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()
                        + Long.parseLong(pickUpTimeH.getValue()) * 3600000 + Long.parseLong(pickUpTimeMin.getValue()) * 60000;

                if (dateLong <= new Date().getTime()) {
                    CustomAlert.warningAlert("The pick up time cannot be in the past.");
                } else {
                    int buildingId = 0;
                    if (!buildingChoiceBox.getValue().equals("Pick Up")) {
                        buildingId = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(buildingChoiceBox.getValue())).getId();
                    }
                    String response = RestaurantServerCommunication.addFoodOrder(restaurant.getId(), buildingId, dateLong);
                    int foodOrderId = Integer.parseInt(response) - 1000;
                    for (Dish dish: dishes) {
                        RestaurantServerCommunication.addDishToFoodOrder(foodOrderId, dish.getName(), dish.getAmount());
                    }
                    String[] strings = {buildingChoiceBox.getValue(), restaurant.getName()};
                    ApplicationDisplay.changeSceneWithVariables("/FoodConfirmation.fxml", dateLong, strings);
                }
            } catch (Exception e) {
                CustomAlert.errorAlert("Something went wrong.");
            }
        }
    }
}
