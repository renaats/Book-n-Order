package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MyPreviousFoodReservationsController implements Initializable {

    private final ObservableList<FoodOrder> foodOrderResult = FXCollections.observableArrayList();

    @FXML
    private TableView<FoodOrder> table;
    @FXML
    private TableColumn<FoodOrder, String> colRestaurant;
    @FXML
    private TableColumn<FoodOrder, Integer> colDeliveryLoc;
    @FXML
    private TableColumn<FoodOrder, Integer> colDate;
    @FXML
    public TableColumn<FoodOrder,Boolean> colYourFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRestaurant.setCellValueFactory(new PropertyValueFactory<>("getRestaurantName"));
        colDeliveryLoc.setCellValueFactory(new PropertyValueFactory<>("getDeliveryLocationName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("getDeliveryDay"));
        colYourFeedback.setCellValueFactory(new PropertyValueFactory<>("getYourFeedback"));
        loadDataIntoTable();
    }

    /**
     * Loads the previous reservations.
     */
    public void loadDataIntoTable() {

        foodOrderResult.clear();
        List<FoodOrder> foodOrders;
        try {
            String json = RestaurantServerCommunication.getAllPreviousFoodOrders();
            List<FoodOrder> foodOrders1 = JsonMapper.foodOrdersListMapper(json);
            foodOrders = new ArrayList<>(foodOrders1);
            for (int i = 0; i < foodOrders.size(); i++) {
                if (!(foodOrders.get(i).getAppUser().getEmail()
                        .equals(JsonMapper.appUserMapper(UserServerCommunication.getOwnUserInformation()).getEmail()))) {
                    foodOrders.remove(foodOrders.get(i));
                }
            }
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            foodOrders = new ArrayList<>();
            foodOrders.add(null);
        }

        Collections.sort(foodOrders);

        if (foodOrders.size() > 10) {
            foodOrders = foodOrders.subList(0, 15);
            foodOrderResult.addAll(foodOrders);
        } else {
            foodOrderResult.addAll(foodOrders);
        }
        table.setItems(foodOrderResult);
    }

    /**
     * Change the view to the main menu when the home icon is clicked.
     * @throws IOException should never be thrown as the input is always the same.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes view to the user's Previous bookings view when the back arrow is clicked.
     * @throws IOException should never be thrown as the input is always the same.
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Adds a positive review to the restaurant the order came from.
     */
    public void thumbsUp() {
        try {
            if (table.getSelectionModel().getSelectedItem().isFeedbackHasBeenGiven()) {
                CustomAlert.warningAlert("You have already given feedback");
                return;
            }
            String response1 = RestaurantServerCommunication
                    .addFeedbackFoodOrder(table.getSelectionModel().getSelectedItem().getId(), true);
            String response2 = RestaurantServerCommunication
                    .addFoodFeedbackRestaurant(table.getSelectionModel().getSelectedItem().getRestaurant().getId(), true);
            if (!(response1.equals("Successfully executed."))) {
                CustomAlert.informationAlert(response1);
                return;
            }
            if (!(response2.equals("Successfully executed."))) {
                CustomAlert.informationAlert(response1);
                return;
            }
            CustomAlert.informationAlert("Feedback has been received");
            loadDataIntoTable();

        } catch (NullPointerException e) {
            CustomAlert.warningAlert("Select an order to rate");
        }
    }

    /**
     * Adds a negative review to the restaurant the order came from.
     */
    public void thumbsDown() {
        try {
            if (table.getSelectionModel().getSelectedItem().isFeedbackHasBeenGiven()) {
                CustomAlert.warningAlert("You have already given feedback");
                return;
            }
            String response1 =
                    RestaurantServerCommunication.addFeedbackFoodOrder(table.getSelectionModel().getSelectedItem().getId(), false);
            String response2 = RestaurantServerCommunication
                    .addFoodFeedbackRestaurant(table.getSelectionModel().getSelectedItem().getRestaurant().getId(), false);
            if (!(response1.equals("Successfully executed."))) {
                CustomAlert.informationAlert(response1);
                return;
            }
            if (!(response2.equals("Successfully executed."))) {
                CustomAlert.informationAlert(response1);
                return;
            }
            CustomAlert.informationAlert("Feedback has been received");
            loadDataIntoTable();

        } catch (NullPointerException e) {
            CustomAlert.warningAlert("Select an order to rate");
        }
    }
}
