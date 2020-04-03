package nl.tudelft.oopp.demo.controllers.restaurants;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "OrderFoodChooseRestaurant.fxml" file
 */
public class OrderFoodChooseRestaurantController implements Initializable {
    private final ObservableList<Restaurant> restaurantResult = FXCollections.observableArrayList();
    private final ObservableList<Dish> dishResult = FXCollections.observableArrayList();
    private final ObservableList<Dish> orderResult = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();
    List<Restaurant> restaurants;
    List<Dish> dishes;
    List<Dish> orders;

    @FXML
    public TableView<Restaurant> restaurantTable;
    @FXML
    public TableView<Dish> dishTable;
    @FXML
    public TableView<Dish> orderTable;
    @FXML
    public TableColumn<Restaurant, String> colRestaurantName;
    @FXML
    public TableColumn<Restaurant, Building> colRestaurantBuilding;
    @FXML
    public TableColumn<Dish, String> colDishName;
    @FXML
    public TableColumn<Dish, String> colDishPrice;
    @FXML
    public TableColumn<Dish, String> colOrderDishName;
    @FXML
    public TableColumn<Dish, String> colOrderPrice;
    @FXML
    public TableColumn<Dish, Integer> colOrderAmount;
    @FXML
    public ChoiceBox<String> buildingChoiceBox;
    @FXML
    public TextField restaurantName;
    @FXML
    public TextField dishName;
    @FXML
    public TextField fromPrice;
    @FXML
    public TextField toPrice;
    @FXML
    private Text pagesTextRestaurant;
    @FXML
    private Text pagesTextDish;
    @FXML
    private Text pagesTextOrder;
    @FXML
    private AnchorPane anchorPane;

    private int restaurantPageNumber;
    private double totalRestaurantPages;
    private int dishPageNumber;
    private double totalDishPages;
    private int orderPageNumber;
    private double totalOrderPages;
    private Button addButton;
    private ImageView imageView;
    private Image image;
    private Text description;
    private Restaurant selectedRestaurant;
    private Dish selectedDish;


    /**
     * Goes back to the main reservations menu when the back arrow button is clicked
     * @param mouseEvent the event is the clicking of the home icon in OrderFoodChooseRestaurant.fxml
     * @throws IOException the input is always the same, so it should never throw an exception
     */
    public void goToMainMenuReservations(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }

    /**
     * Changes the view to the orderFood view
     * @param actionEvent the event is the clicking of the "place order" button in OrderFoodChooseRestaurant.fxml
     * @throws IOException the input is always the same, so it should never throw an exception.
     */
    public void goToOrderFood(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/OrderFoodPickDate.fxml");
    }
    /**
     * Goes back to the main reservations menu when the home icon is clicked
     * @param mouseEvent the event is the clicking of the home icon in mainMenu.fxml
     * @throws IOException the input is always the same, so it should never throw an exception
     */

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRestaurantName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRestaurantBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDishPrice.setCellValueFactory(new PropertyValueFactory<>("priceInEuros"));
        colOrderDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("priceInEuros"));
        colOrderAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        orders = new ArrayList<>();

        if (restaurantPageNumber == 0) {
            restaurantPageNumber++;
        }
        if (dishPageNumber == 0) {
            dishPageNumber++;
        }
        if (orderPageNumber == 0) {
            orderPageNumber++;
        }

        applyRestaurantFilters();
        loadBuildingChoiceBox();
        restaurantTableSelectMethod();
    }

    public void loadRestaurants(String name, String buildingName) {
        try {
            if (name != null) {
                restaurants = new ArrayList<>(
                        Objects.requireNonNull(JsonMapper.restaurantListMapper(ServerCommunication.findRestaurantByName(name))));
            } else {
                restaurants = new ArrayList<>(
                        Objects.requireNonNull(JsonMapper.restaurantListMapper(ServerCommunication.getRestaurants())));
            }
            if (buildingName != null) {
                List<Restaurant> newRestaurants = new ArrayList<>();
                for (Restaurant restaurant : restaurants) {
                    if (restaurant.getBuilding().getName().equals(buildingName)) {
                        newRestaurants.add(restaurant);
                    }
                }
                restaurants = newRestaurants;
            }
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            restaurants = new ArrayList<>();
            restaurants.add(null);
        }
        calculateRestaurantPages();
    }

    public void calculateRestaurantPages() {
        restaurantResult.clear();
        totalRestaurantPages = Math.ceil(restaurants.size() / 10.0);
        if (totalRestaurantPages < restaurantPageNumber) {
            restaurantPageNumber--;
        }
        pagesTextRestaurant.setText(restaurantPageNumber + " / " + (int) totalRestaurantPages + " pages");
        if (restaurants.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    restaurantResult.add(restaurants.get((i - 10) + restaurantPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            restaurantResult.addAll(restaurants);
        }
        restaurantTable.setItems(restaurantResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextRestaurantPage() {
        if (restaurantPageNumber < (int) totalRestaurantPages) {
            restaurantPageNumber++;
            calculateRestaurantPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousRestaurantPage() {
        if (restaurantPageNumber > 1) {
            restaurantPageNumber--;
        }
        calculateRestaurantPages();
    }

    public void applyRestaurantFilters() {
        String buildingName = null;
        String name = null;
        if (buildingChoiceBox.getValue() != null) {
            buildingName = buildingChoiceBox.getValue();
        }
        if (!restaurantName.getText().equals("")) {
            name = restaurantName.getText();
        }
        loadRestaurants(name, buildingName);
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void restaurantTableSelectMethod() {
        restaurantTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            applyDishFilters();
            dishTableSelectMethod();
        });
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void dishTableSelectMethod() {
        dishTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            selectedDish = dishTable.getSelectionModel().getSelectedItem();
            anchorPane.getChildren().remove(addButton);

            for (int i = 0; i < dishResult.size(); i++) {
                assert selectedDish != null;
                if (dishResult.get(i).getId() == selectedDish.getId()) {
                    addButton = new Button("Add");
                    addButton.setLayoutX(775);
                    addButton.setLayoutY(320 + (24  * (i + 1)));
                    addButton.setMinWidth(50);
                    addButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    addButton.setMaxHeight(24);
                    addButton.setOnAction(event -> {
                        addToOrder();
                    });
                    anchorPane.getChildren().add(addButton);
                }
            }
            displayDescriptionAndImage();
        });
    }

    private void loadDishes(String filterString) {
        try {
            dishes = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.dishListMapper(ServerCommunication.filterDishes(filterString))));
        } catch (Exception e) {
            dishes = new ArrayList<>();
            dishes.add(null);
        }
        calculateDishPages();
    }

    public void calculateDishPages() {
        dishResult.clear();
        totalDishPages = Math.ceil(dishes.size() / 10.0);
        if (totalDishPages < dishPageNumber) {
            dishPageNumber--;
        }
        pagesTextDish.setText(dishPageNumber + " / " + (int) totalDishPages + " pages");
        if (dishes.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    dishResult.add(dishes.get((i - 10) + dishPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            dishResult.addAll(dishes);
        }
        dishTable.setItems(dishResult);
    }

    /**
     * Handles the clicking to the next table page.
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

    public void applyDishFilters() {
        try {
            String filterString = "menu:" + JsonMapper.menuMapper(ServerCommunication.findMenuByRestaurant(selectedRestaurant.getId())).getId();
            if (!fromPrice.getText().equals("")) {
                filterString += ",price>" + fromPrice.getText();
            }
            if (!toPrice.getText().equals("")) {
                filterString += ",price<" + toPrice.getText();
            }
            if (!dishName.getText().equals("")) {
                filterString += ",name:" + dishName.getText();
            }
            loadDishes(filterString);
        } catch (Exception e) {
            loadDishes("menu:0");
        }
    }

    public void displayDescriptionAndImage() {
        anchorPane.getChildren().remove(imageView);
        image = new Image(selectedDish.getImage());
        imageView = new ImageView(image);
        imageView.setLayoutX(993);
        imageView.setLayoutY(57);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        anchorPane.getChildren().add(imageView);

        anchorPane.getChildren().remove(description);
        description = new Text();
        description.setText(selectedDish.getDescription());
        description.setLayoutX(993);
        description.setLayoutY(57);
        anchorPane.getChildren().add(description);
    }

    //================= ORDER:

    public void addToOrder() {
        if (orders.contains(selectedDish)) {
            selectedDish.setAmount(selectedDish.getAmount() + 1);
        } else {
            selectedDish.setAmount(1);
            orders.add(selectedDish);
        }
        calculateOrderPages();
    }

    public void calculateOrderPages() {
        orderResult.clear();
        totalOrderPages = Math.ceil(orders.size() / 10.0);
        if (totalOrderPages < orderPageNumber) {
            orderPageNumber--;
        }
        pagesTextOrder.setText(orderPageNumber + " / " + (int) totalOrderPages + " pages");
        if (orders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    orderResult.add(orders.get((i - 10) + orderPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            orderResult.addAll(orders);
        }
        orderTable.setItems(orderResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextOrderPage() {
        if (orderPageNumber < (int) totalOrderPages) {
            orderPageNumber++;
            calculateOrderPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousOrderPage() {
        if (orderPageNumber > 1) {
            orderPageNumber--;
        }
        calculateOrderPages();
    }

    /**
     * Takes care of the options for the buildingChoiceBox in the GUI
     */
    private void loadBuildingChoiceBox() {
        buildingNameList.clear();
        try {
            for (Building building: Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings()))) {
                buildingNameList.add(building.getName());
            }
            buildingNameList.add(null);
        } catch (Exception e) {
            buildingNameList.add(null);
        }
        buildingChoiceBox.getItems().addAll(buildingNameList);
    }
}
