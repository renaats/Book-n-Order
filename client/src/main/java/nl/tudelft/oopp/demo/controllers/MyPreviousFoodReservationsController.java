package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MyPreviousFoodReservationsController implements Initializable {

    final List<FoodOrder> foodOrders = JsonMapper.foodOrderList(ServerCommunication.getAllFoodOrders());

    private final ObservableList<FoodOrder> foodOrderResult = FXCollections.observableArrayList();

    @FXML
    private Text pagesText;
    @FXML
    private TableView<FoodOrder> table;
    @FXML
    private TableColumn<FoodOrder, String> colId;
    @FXML
    private TableColumn<FoodOrder, String> colRestaurant;
    @FXML
    private TableColumn<FoodOrder, Integer> colDate;

    private int pageNumber;
    private double totalPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRestaurant.setCellValueFactory(new PropertyValueFactory<>("getRestaurantName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("getDate"));
        loadDataIntoTable();
    }

    /**
     * Should load the previous reservations
     */
    public void loadDataIntoTable() {

        foodOrderResult.clear();
        List<FoodOrder> foodOrders;
        try {
            foodOrders = new ArrayList<>(Objects.requireNonNull(JsonMapper.foodOrderList((ServerCommunication.getAllPreviousFoodOrders()))));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            foodOrders = new ArrayList<>();
            foodOrders.add(null);
        }

        totalPages = Math.ceil(foodOrders.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (foodOrders.size() > 16) {
            for (int i = 1; i < 16; i++) {
                try {
                    foodOrderResult.add(foodOrders.get((i - 15) + pageNumber * 15));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            foodOrderResult.addAll(foodOrders);
        }
        table.setItems(foodOrderResult);
    }

    /**
     * change the view to the main menu when the home icon is clicked
     * @throws IOException should never be thrown as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * changes view to the my Previous bookings view when the back arrow is clicked
     * @throws IOException should never be thrown as the input is always the same
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    public void thumbsUp() {
        if (table.getSelectionModel().getSelectedItem().getFeedback()) {
            CustomAlert.warningAlert("You have already given feedback, but it has now been overwritten");
        }
        CustomAlert.informationAlert(ServerCommunication.addFoodFeedback(table.getSelectionModel().getSelectedItem().getRestaurant().getId(), true));
    }

    public void thumbsDown() {
        if (table.getSelectionModel().getSelectedItem().getFeedback()) {
            CustomAlert.warningAlert("You have already given feedback, but it has now been overwritten");
        }
        CustomAlert.informationAlert(ServerCommunication.addFoodFeedback(table.getSelectionModel().getSelectedItem().getRestaurant().getId(), false));
    }
}
