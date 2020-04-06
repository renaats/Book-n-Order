package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseViewFoodOrders.fxml" file
 */
public class DatabaseViewFoodOrderController implements Initializable {

    private final ObservableList<FoodOrder> currentResult = FXCollections.observableArrayList();
    private final ObservableList<FoodOrder> pastResult = FXCollections.observableArrayList();
    private final ObservableList<DishOrder> dishResult = FXCollections.observableArrayList();
    private List<FoodOrder> currentOrders;
    private List<FoodOrder> pastOrders;
    private List<DishOrder> dishOrders;
    private Restaurant restaurant;
    private FoodOrder selectedFoodOrder;

    @FXML
    private TableView<FoodOrder> currentTable;
    @FXML
    private TableView<FoodOrder> pastTable;
    @FXML
    private TableView<DishOrder> dishTable;
    @FXML
    private TableColumn<FoodOrder, String> colCurrentLocation;
    @FXML
    private TableColumn<FoodOrder, String> colCurrentTime;
    @FXML
    private TableColumn<FoodOrder, String> colPastLocation;
    @FXML
    private TableColumn<FoodOrder, String> colPastTime;
    @FXML
    private TableColumn<DishOrder, String> colDishName;
    @FXML
    private TableColumn<DishOrder, Integer> colDishAmount;
    @FXML
    private Text currentPagesText;
    @FXML
    private Text pastPagesText;
    @FXML
    private Text dishPagesText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button previousDishPageButton;
    @FXML
    private Button nextDishPageButton;

    private Button dishesButton;

    private int currentPageNumber;
    private double totalCurrentPages;
    private int pastPageNumber;
    private double totalPastPages;
    private int dishPageNumber;
    private double totalDishPages;

    public DatabaseViewFoodOrderController(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCurrentLocation.setCellValueFactory(new PropertyValueFactory<>("locationNameString"));
        colCurrentTime.setCellValueFactory(new PropertyValueFactory<>("deliveryTimeString"));
        colPastLocation.setCellValueFactory(new PropertyValueFactory<>("locationNameString"));
        colPastTime.setCellValueFactory(new PropertyValueFactory<>("deliveryTimeString"));
        colDishName.setCellValueFactory(new PropertyValueFactory<>("dishName"));
        colDishAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        currentTable.setPlaceholder(new Label(""));
        pastTable.setPlaceholder(new Label(""));
        dishTable.setPlaceholder(new Label(""));

        if (currentPageNumber == 0) {
            currentPageNumber++;
        }
        if (pastPageNumber == 0) {
            pastPageNumber++;
        }
        if (dishPageNumber == 0) {
            dishPageNumber++;
        }

        dishTable.setVisible(false);
        dishPagesText.setVisible(false);
        previousDishPageButton.setVisible(false);
        nextDishPageButton.setVisible(false);
        loadAllOrders();
        addListeners();
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Returns to the restaurant database menu
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    /**
     * Loads all orders from the database.
     */
    public void loadAllOrders() {
        try {
            currentOrders = JsonMapper.foodOrdersListMapper(RestaurantServerCommunication.getAllFutureFoodOrdersForRestaurant(restaurant.getId()));
        } catch (Exception e) {
            currentOrders = new ArrayList<>();
        }
        currentOrders.sort(Comparator.comparing(FoodOrder::getDeliveryTimeString));
        try {
            pastOrders = JsonMapper.foodOrdersListMapper(RestaurantServerCommunication.getAllPreviousFoodOrdersForRestaurant(restaurant.getId()));
        } catch (Exception e) {
            pastOrders = new ArrayList<>();
        }
        pastOrders.sort(Comparator.comparing(FoodOrder::getDeliveryTimeString).reversed());
        calculateCurrentPages();
        calculatePastPages();
    }

    /**
     * Calculates how many current pages there should be for browsing the table
     */
    public void calculateCurrentPages() {
        currentResult.clear();
        totalCurrentPages = Math.ceil(currentOrders.size() / 10.0);
        if (totalCurrentPages < currentPageNumber) {
            currentPageNumber--;
        }
        if (totalCurrentPages > 0) {
            currentPageNumber = Math.max(currentPageNumber, 1);
        }
        currentPagesText.setText(currentPageNumber + " / " + (int) totalCurrentPages + " pages");
        if (currentOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    currentResult.add(currentOrders.get((i - 10) + currentPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            currentResult.addAll(currentOrders);
        }
        currentTable.setItems(currentResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextCurrentPage() {
        if (currentPageNumber < (int) totalCurrentPages) {
            currentPageNumber++;
            calculateCurrentPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousCurrentPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
        }
        calculateCurrentPages();
    }

    /**
     * Calculates how many past pages there should be for browsing the table
     */
    public void calculatePastPages() {
        pastResult.clear();
        totalPastPages = Math.ceil(pastOrders.size() / 10.0);
        if (totalPastPages < pastPageNumber) {
            pastPageNumber--;
        }
        if (totalPastPages > 0) {
            pastPageNumber = Math.max(pastPageNumber, 1);
        }
        pastPagesText.setText(pastPageNumber + " / " + (int) totalPastPages + " pages");
        if (pastOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    pastResult.add(pastOrders.get((i - 10) + pastPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            pastResult.addAll(pastOrders);
        }
        pastTable.setItems(pastResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPastPage() {
        if (pastPageNumber < (int) totalPastPages) {
            pastPageNumber++;
            calculatePastPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPastPage() {
        if (pastPageNumber > 1) {
            pastPageNumber--;
        }
        calculatePastPages();
    }

    /**
     * Adds listeners to the order tables.
     */
    public void addListeners() {
        currentTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (currentTable.getSelectionModel().getSelectedItem() == null) {
                if (selectedFoodOrder == null) {
                    dishTable.setVisible(false);
                    dishPagesText.setVisible(false);
                    previousDishPageButton.setVisible(false);
                    nextDishPageButton.setVisible(false);
                }
                return;
            }
            pastTable.getSelectionModel().clearSelection();
            selectedFoodOrder = currentTable.getSelectionModel().getSelectedItem();
            viewDishes();
        });
        pastTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (pastTable.getSelectionModel().getSelectedItem() == null) {
                if (selectedFoodOrder == null) {
                    dishTable.setVisible(false);
                    dishPagesText.setVisible(false);
                    previousDishPageButton.setVisible(false);
                    nextDishPageButton.setVisible(false);
                }
                return;
            }
            currentTable.getSelectionModel().clearSelection();
            selectedFoodOrder = pastTable.getSelectionModel().getSelectedItem();

            viewDishes();
        });
    }

    /**
     * Loads the dishes for the selected food order.
     */
    public void viewDishes() {
        try {
            dishOrders = JsonMapper.dishOrderListMapper(RestaurantServerCommunication.getDishOrders(selectedFoodOrder.getId()));
        } catch (Exception e) {
            dishOrders = new ArrayList<>();
        }
        calculateDishPages();
    }

    /**
     * Calculates how many dish pages there should be for browsing the table
     */
    public void calculateDishPages() {
        dishTable.setVisible(true);
        dishPagesText.setVisible(true);
        previousDishPageButton.setVisible(true);
        nextDishPageButton.setVisible(true);
        dishResult.clear();
        totalDishPages = Math.ceil(dishOrders.size() / 10.0);
        if (totalDishPages < dishPageNumber) {
            dishPageNumber--;
        }
        if (totalDishPages > 0) {
            dishPageNumber = Math.max(dishPageNumber, 1);
        }
        dishPagesText.setText(dishPageNumber + " / " + (int) totalDishPages + " pages");
        if (dishOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    dishResult.add(dishOrders.get((i - 10) + dishPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            dishResult.addAll(dishOrders);
        }
        dishTable.setItems(dishResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextDishPage() {
        if (dishPageNumber < (int) totalDishPages) {
            dishPageNumber++;
            calculateDishPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousDishPage() {
        if (dishPageNumber > 1) {
            dishPageNumber--;
        }
        calculateDishPages();
    }
}
