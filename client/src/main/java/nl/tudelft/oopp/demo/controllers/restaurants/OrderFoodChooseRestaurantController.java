package nl.tudelft.oopp.demo.controllers.restaurants;

import static nl.tudelft.oopp.demo.controllers.generic.CalculatePages.calculatePages;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "OrderFoodChooseRestaurant.fxml" file
 */
public class OrderFoodChooseRestaurantController implements Initializable {
    private final ObservableList<Restaurant> restaurantResult = FXCollections.observableArrayList();
    private final ObservableList<Dish> dishResult = FXCollections.observableArrayList();
    private final ObservableList<Dish> orderResult = FXCollections.observableArrayList();
    private final ObservableList<Allergy> allergyResult = FXCollections.observableArrayList();
    private final ObservableList<Allergy> allDietResult = FXCollections.observableArrayList();
    private final ObservableList<Allergy> selectedDietResult = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();
    private List<Restaurant> restaurants;
    private List<Dish> dishes;
    private List<Dish> orders;
    private List<Allergy> allergies;
    private List<Allergy> currentAllergies;
    private List<Allergy> selectedDiets;

    @FXML public TableView<Restaurant> restaurantTable;
    @FXML public TableView<Dish> dishTable;
    @FXML public TableView<Dish> orderTable;
    @FXML public TableView<Allergy> allergyTable;
    @FXML public TableView<Allergy> allDietTable;
    @FXML public TableView<Allergy> selectedDietTable;
    @FXML public TableColumn<Restaurant, String> colRestaurantName;
    @FXML public TableColumn<Restaurant, Integer> colRestaurantScore;
    @FXML public TableColumn<Restaurant, Building> colRestaurantBuilding;
    @FXML public TableColumn<Dish, String> colDishName;
    @FXML public TableColumn<Dish, String> colDishPrice;
    @FXML public TableColumn<Dish, String> colOrderDishName;
    @FXML public TableColumn<Dish, String> colOrderPrice;
    @FXML public TableColumn<Dish, Integer> colOrderAmount;
    @FXML public TableColumn<Allergy, String> colAllergy;
    @FXML public TableColumn<Allergy, String> colAllDiet;
    @FXML public TableColumn<Allergy, String> colSelectedDiet;
    @FXML public ChoiceBox<String> buildingChoiceBox;
    @FXML public TextField restaurantName;
    @FXML public TextField dishName;
    @FXML public TextField fromPrice;
    @FXML public TextField toPrice;
    @FXML private Text pagesTextRestaurant;
    @FXML private Text pagesTextDish;
    @FXML private Text pagesTextOrder;
    @FXML private Text pagesTextAllergy;
    @FXML private Text pagesTextAllDiet;
    @FXML private Text pagesTextSelectedDiet;
    @FXML private AnchorPane anchorPane;
    @FXML private Text totalCost;
    @FXML private Button viewAllergyButton;
    @FXML private Button nextPageAllergyButton;
    @FXML private Button previousPageAllergyButton;
    @FXML private Button nextPageOrderButton;
    @FXML private Button previousPageOrderButton;
    @FXML private Button nextPageAllDietButton;
    @FXML private Button previousPageAllDietButton;
    @FXML private Button nextPageSelectedDietButton;
    @FXML private Button previousPageSelectedDietButton;
    @FXML private Button filterDietsButton;

    private int restaurantPageNumber;
    private int totalRestaurantPages;
    private int dishPageNumber;
    private int totalDishPages;
    private int orderPageNumber;
    private int totalOrderPages;
    private int allergyPageNumber;
    private int totalAllergyPages;
    private int allDietPageNumber;
    private int totalAllDietPages;
    private int selectedDietPageNumber;
    private int totalSelectedDietPages;
    private Button addButton;
    private Button removeButton;
    private Button removeDietButton;
    private Button selectDietButton;
    private ImageView imageView;
    private Text description;
    private Restaurant selectedRestaurant;
    private Dish selectedDish;
    private Allergy selectedAllergy;
    int price;

    /**
     * Goes back to the main reservations menu when the back arrow button is clicked
     * @throws IOException the input is always the same, so it should never throw an exception
     */
    public void goToMainMenuReservations() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * Changes the view to the orderFood view
     * @throws IOException the input is always the same, so it should never throw an exception.
     */
    public void goToOrderFood() throws IOException {
        if (selectedRestaurant != null && orders != null && !orders.isEmpty()) {
            ApplicationDisplay.changeSceneWithVariables("/OrderFoodPickDate.fxml", orders, selectedRestaurant);
        } else {
            CustomAlert.warningAlert("You must select dishes first.");
        }
    }

    /**
     * Goes back to the main reservations menu when the home icon is clicked
     * @throws IOException the input is always the same, so it should never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRestaurantScore.setCellValueFactory(new PropertyValueFactory<>("feedbackCounter"));
        colRestaurantName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRestaurantBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDishPrice.setCellValueFactory(new PropertyValueFactory<>("priceInEuros"));
        colOrderDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("priceInEuros"));
        colOrderAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAllergy.setCellValueFactory(new PropertyValueFactory<>("allergyName"));
        colAllDiet.setCellValueFactory(new PropertyValueFactory<>("allergyName"));
        colSelectedDiet.setCellValueFactory(new PropertyValueFactory<>("allergyName"));

        restaurantTable.setPlaceholder(new Label(""));
        dishTable.setPlaceholder(new Label(""));
        orderTable.setPlaceholder(new Label(""));
        allergyTable.setPlaceholder(new Label(""));
        allDietTable.setPlaceholder(new Label(""));
        selectedDietTable.setPlaceholder(new Label(""));

        orders = new ArrayList<>();

        restaurantPageNumber = 1;
        dishPageNumber = 1;
        orderPageNumber = 1;
        allergyPageNumber = 1;
        allDietPageNumber = 1;
        selectedDietPageNumber = 1;
        price = 0;

        applyRestaurantFilters();
        loadBuildingChoiceBox();
        restaurantTableSelectMethod();
        applyDishFilters();
        calculateOrderPages();
        dishTableSelectMethod();
        orderTableSelectMethod();
        displayCurrentAllergies();
        displayRemovedAllergies();
        addAllergyTableListeners();
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
            buildingNameList.add(null);
        } catch (Exception e) {
            buildingNameList.add(null);
        }
        buildingChoiceBox.getItems().addAll(buildingNameList);
    }

    /**
     * Takes care of loading all restaurants in a specific building
     * @param name restaurant name
     * @param buildingName building name
     */
    public void loadRestaurants(String name, String buildingName) {
        try {
            if (name != null) {
                restaurants = new ArrayList<>();
                restaurants.add(Objects.requireNonNull(JsonMapper.restaurantMapper(RestaurantServerCommunication.findRestaurantByName(name))));
            } else {
                restaurants = new ArrayList<>(
                        Objects.requireNonNull(JsonMapper.restaurantListMapper(RestaurantServerCommunication.getRestaurants())));
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
            restaurants = new ArrayList<>();
        }
        calculateRestaurantPages();
    }

    /**
     * Calculates how many restaurant pages there should be for browsing the table
     */
    public void calculateRestaurantPages() {
        Pair<Integer, Integer> pair = calculatePages(restaurantResult, totalRestaurantPages, restaurantPageNumber, restaurants, 10);
        totalRestaurantPages = pair.getKey();
        restaurantPageNumber = pair.getValue();
        pagesTextRestaurant.setText(restaurantPageNumber + " / " + totalRestaurantPages + " pages");
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

    /**
     * Handles applying of restaurant filters
     */
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
            if (restaurantTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            orderTable.getSelectionModel().clearSelection();
            dishTable.getSelectionModel().clearSelection();
            allergyTable.getSelectionModel().clearSelection();
            selectedDietTable.getSelectionModel().clearSelection();
            allDietTable.getSelectionModel().clearSelection();
            anchorPane.getChildren().remove(addButton);
            anchorPane.getChildren().remove(removeButton);
            anchorPane.getChildren().remove(imageView);
            anchorPane.getChildren().remove(description);
            anchorPane.getChildren().remove(removeDietButton);
            anchorPane.getChildren().remove(selectDietButton);
            orders.clear();
            price = 0;
            calculateOrderPages();
            totalCost.setText("Total Cost: " + new DecimalFormat("#0.00").format((double) Math.round(price * 100) / 10000) + "\u20ac"); // Unicode = €
            selectedDish = null;
            loadAllergies();
            selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            applyDishFilters();
        });
    }

    // DISHES

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void dishTableSelectMethod() {
        dishTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (dishTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            orderTable.getSelectionModel().clearSelection();
            restaurantTable.getSelectionModel().clearSelection();
            allergyTable.getSelectionModel().clearSelection();
            selectedDietTable.getSelectionModel().clearSelection();
            allDietTable.getSelectionModel().clearSelection();
            selectedDish = dishTable.getSelectionModel().getSelectedItem();
            anchorPane.getChildren().remove(addButton);
            anchorPane.getChildren().remove(removeButton);
            anchorPane.getChildren().remove(selectDietButton);
            anchorPane.getChildren().remove(removeDietButton);

            for (int i = 0; i < dishResult.size(); i++) {
                assert selectedDish != null;
                if (dishResult.get(i).getId() == selectedDish.getId()) {
                    addButton = new Button("Add");
                    addButton.setLayoutX(640);
                    addButton.setLayoutY(320 + (24  * (i + 1)));
                    addButton.setMinWidth(50);
                    addButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    addButton.setMaxHeight(24);
                    addButton.setOnAction(event -> addToOrder());
                    anchorPane.getChildren().add(addButton);
                }
            }
            loadAllergies();
            displayDescriptionAndImage();
        });
    }

    private void loadDishes(String filterString) {
        try {
            dishes = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.dishListMapper(RestaurantServerCommunication.filterDishes(filterString))));
            if (selectedDiets != null && !selectedDiets.isEmpty()) {
                List<Dish> filteredDishes = new ArrayList<>();
                for (Dish dish: dishes) {
                    boolean isValid = true;
                    List<Allergy> dishAllergies;
                    try {
                        dishAllergies = JsonMapper.allergiesListMapper(RestaurantServerCommunication.getAllergiesFromDish(dish.getId()));
                    } catch (Exception e) {
                        dishAllergies = new ArrayList<>();
                    }
                    if (dishAllergies != null) {
                        for (Allergy allergy: selectedDiets) {
                            if (!dishAllergies.contains(allergy)) {
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid) {
                            filteredDishes.add(dish);
                        }
                    }
                }
                dishes = filteredDishes;
            }
        } catch (Exception e) {
            dishes = new ArrayList<>();
        }
        calculateDishPages();
    }

    /**
     * Calculates the amount of pages there should be to browse the table properly
     */
    public void calculateDishPages() {
        Pair<Integer, Integer> pair = calculatePages(dishResult, totalDishPages, dishPageNumber, dishes, 10);
        totalDishPages = pair.getKey();
        dishPageNumber = pair.getValue();
        pagesTextDish.setText(dishPageNumber + " / " + totalDishPages + " pages");
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

    /**
     * Takes care of applying filters to a dish
     */
    public void applyDishFilters() {
        try {
            String filterString = "menu:" + JsonMapper.menuMapper(
                    RestaurantServerCommunication.findMenuByRestaurant(selectedRestaurant.getId())).getId();
            if (!fromPrice.getText().equals("")) {
                long filterPrice = Math.round((Double.parseDouble(fromPrice.getText())) * 100);
                filterString += ",price>" + filterPrice;
            }
            if (!toPrice.getText().equals("")) {
                long filterPrice = Math.round((Double.parseDouble(toPrice.getText())) * 100);
                filterString += ",price<" + filterPrice;
            }
            if (!dishName.getText().equals("")) {
                filterString += ",name:" + dishName.getText();
            }
            loadDishes(filterString);
        } catch (Exception e) {
            loadDishes("menu:0");
        }
    }

    /**
     * Takes care of displaying the description and image of a dish
     */
    public void displayDescriptionAndImage() {
        try {
            anchorPane.getChildren().remove(description);
            description = new Text(selectedDish.getDescription());
            description.setLayoutX(705);
            description.setLayoutY(544);
            description.setWrappingWidth(200);
            description.setVisible(!allergyTable.isVisible() && orderTable.isVisible());
            anchorPane.getChildren().add(description);

            anchorPane.getChildren().remove(imageView);
            Image image = new Image(selectedDish.getImage());
            imageView = new ImageView(image);
            imageView.setLayoutX(705);
            imageView.setLayoutY(320);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setVisible(!allergyTable.isVisible() && orderTable.isVisible());
            anchorPane.getChildren().add(imageView);
        } catch (Exception e) {
            anchorPane.getChildren().remove(imageView);
        }
    }

    /**
     * Takes care of adding a dish to an order
     */
    public void addToOrder() {
        if (orders.contains(selectedDish)) {
            selectedDish.setAmount(selectedDish.getAmount() + 1);
        } else {
            selectedDish.setAmount(1);
            orders.add(selectedDish);
        }
        price += selectedDish.getPrice();
        totalCost.setText("Total Cost: " + new DecimalFormat("#0.00").format((double) Math.round(price * 100) / 10000) + "\u20ac"); // Unicode = €
        calculateOrderPages();
    }

    /**
     * Takes care of removing a dish from order
     */
    public void removeFromOrder() {
        if (orders.contains(selectedDish)) {
            selectedDish.setAmount(selectedDish.getAmount() - 1);
            price -= selectedDish.getPrice();
        }
        if (selectedDish.getAmount() == 0) {
            orders.remove(selectedDish);
            selectedDish = null;
            anchorPane.getChildren().remove(removeButton);
            anchorPane.getChildren().remove(addButton);
        }
        totalCost.setText("Total Cost: " + new DecimalFormat("#0.00").format((double) Math.round(price * 100) / 10000) + "\u20ac"); // Unicode = €
        calculateOrderPages();
    }

    /**
     * Calculates the amount of pages there should be to browse the table properly
     */
    public void calculateOrderPages() {
        Pair<Integer, Integer> pair = calculatePages(orderResult, totalOrderPages, orderPageNumber, orders, 10);
        totalOrderPages = pair.getKey();
        orderPageNumber = pair.getValue();
        pagesTextOrder.setText(orderPageNumber + " / " + totalOrderPages + " pages");
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
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void orderTableSelectMethod() {
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (orderTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            dishTable.getSelectionModel().clearSelection();
            restaurantTable.getSelectionModel().clearSelection();
            allergyTable.getSelectionModel().clearSelection();
            if (orderTable.getSelectionModel().getSelectedItem() != null
                    || !orders.contains(selectedDish)
                    || !anchorPane.getChildren().contains(removeButton)) {
                anchorPane.getChildren().remove(removeButton);
                selectedDish = orderTable.getSelectionModel().getSelectedItem();
            }
            for (int i = 0; i < orderResult.size(); i++) {
                if (selectedDish != null && orderResult.get(i).getId() == selectedDish.getId()) {
                    removeButton = new Button("Remove");
                    removeButton.setLayoutX(920);
                    removeButton.setLayoutY(320 + (24  * (i + 1)));
                    removeButton.setMinWidth(50);
                    removeButton.setStyle("-fx-background-color:  #cc3c1f; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    removeButton.setMaxHeight(24);
                    removeButton.setOnAction(event -> removeFromOrder());
                    anchorPane.getChildren().add(removeButton);
                }
            }

            anchorPane.getChildren().remove(addButton);
            for (int i = 0; i < dishResult.size(); i++) {
                if (selectedDish != null && dishResult.get(i).getId() == selectedDish.getId()) {
                    addButton = new Button("Add");
                    addButton.setLayoutX(640);
                    addButton.setLayoutY(320 + (24  * (i + 1)));
                    addButton.setMinWidth(50);
                    addButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    addButton.setMaxHeight(24);
                    addButton.setOnAction(event -> addToOrder());
                    anchorPane.getChildren().add(addButton);
                }
            }
            loadAllergies();
            displayDescriptionAndImage();
        });
    }

    // ALLERGIES

    /**
     * Takes care of viewing the allergies
     */
    public void viewAllergy() {
        imageView.setVisible(false);
        description.setVisible(false);
        allergyTable.setVisible(true);
        previousPageAllergyButton.setVisible(true);
        nextPageAllergyButton.setVisible(true);
        pagesTextAllergy.setVisible(true);
        viewAllergyButton.setText("View Information");
        viewAllergyButton.setOnAction(event -> viewInfo());
    }

    /**
     * Takes care of viewing the information
     */
    public void viewInfo() {
        imageView.setVisible(true);
        description.setVisible(true);
        allergyTable.setVisible(false);
        previousPageAllergyButton.setVisible(false);
        nextPageAllergyButton.setVisible(false);
        pagesTextAllergy.setVisible(false);
        viewAllergyButton.setText("View Allergies");
        viewAllergyButton.setOnAction(event -> viewAllergy());
    }

    /**
     * Takes care of loading all allergies
     */
    public void loadAllergies() {
        try {
            allergies = JsonMapper.allergiesListMapper(RestaurantServerCommunication.getAllergiesFromDish(selectedDish.getId()));
        } catch (Exception e) {
            allergies = new ArrayList<>();
        }
        calculateAllergyPages();
    }

    /**
     * Takes care of calculating all table pages to navigate the table properly
     */
    public void calculateAllergyPages() {
        Pair<Integer, Integer> pair = calculatePages(allergyResult, totalAllergyPages, allergyPageNumber, allergies, 10);
        totalAllergyPages = pair.getKey();
        allergyPageNumber = pair.getValue();
        pagesTextAllergy.setText(allergyPageNumber + " / " + totalAllergyPages + " pages");
        allergyTable.setItems(allergyResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextAllergyPage() {
        if (allergyPageNumber < (int) totalAllergyPages) {
            allergyPageNumber++;
            calculateAllergyPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousAllergyPage() {
        if (allergyPageNumber > 1) {
            allergyPageNumber--;
        }
        calculateAllergyPages();
    }

    /**
     * Takes care of filtering all diets
     */
    public void filterDiets() {
        if (addButton != null) {
            addButton.setVisible(false);
        }
        if (removeButton != null) {
            removeButton.setVisible(false);
        }
        orderTable.setVisible(false);
        pagesTextOrder.setVisible(false);
        previousPageOrderButton.setVisible(false);
        nextPageOrderButton.setVisible(false);
        if (imageView != null) {
            imageView.setVisible(false);
        }
        if (description != null) {
            description.setVisible(false);
        }
        allergyTable.setVisible(false);
        previousPageAllergyButton.setVisible(false);
        nextPageAllergyButton.setVisible(false);
        pagesTextAllergy.setVisible(false);
        viewAllergyButton.setVisible(false);
        filterDietsButton.setText("View Information");
        filterDietsButton.setOnAction(event -> viewDishes());

        allDietTable.setVisible(true);
        nextPageAllDietButton.setVisible(true);
        previousPageAllDietButton.setVisible(true);
        pagesTextAllDiet.setVisible(true);
        selectedDietTable.setVisible(true);
        nextPageSelectedDietButton.setVisible(true);
        previousPageSelectedDietButton.setVisible(true);
        pagesTextSelectedDiet.setVisible(true);
    }

    /**
     * Takes care of viewing all dishes
     */
    public void viewDishes() {
        if (addButton != null) {
            addButton.setVisible(true);
        }
        if (removeButton != null) {
            removeButton.setVisible(true);
        }
        orderTable.setVisible(true);
        pagesTextOrder.setVisible(true);
        previousPageOrderButton.setVisible(true);
        nextPageOrderButton.setVisible(true);
        if (imageView != null) {
            imageView.setVisible(true);
        }
        if (description != null) {
            description.setVisible(true);
        }
        allergyTable.setVisible(false);
        previousPageAllergyButton.setVisible(false);
        nextPageAllergyButton.setVisible(false);
        pagesTextAllergy.setVisible(false);
        viewAllergyButton.setVisible(true);
        viewAllergyButton.setText("View Allergies");
        viewAllergyButton.setOnAction(event -> viewAllergy());
        filterDietsButton.setText("Filter Diets");
        filterDietsButton.setOnAction(event -> filterDiets());

        allDietTable.setVisible(false);
        nextPageAllDietButton.setVisible(false);
        previousPageAllDietButton.setVisible(false);
        pagesTextAllDiet.setVisible(false);
        selectedDietTable.setVisible(false);
        nextPageSelectedDietButton.setVisible(false);
        previousPageSelectedDietButton.setVisible(false);
        pagesTextSelectedDiet.setVisible(false);
        anchorPane.getChildren().remove(selectDietButton);
        anchorPane.getChildren().remove(removeDietButton);
    }

    /**
     * Takes care of calculating all pages to navigate the diet table properly
     */
    public void calculateAllDietPages() {
        Pair<Integer, Integer> pair = calculatePages(allDietResult, totalAllDietPages, allDietPageNumber, currentAllergies, 5);
        totalAllDietPages = pair.getKey();
        allDietPageNumber = pair.getValue();
        pagesTextAllDiet.setText(allDietPageNumber + " / " + totalAllDietPages + " pages");
        allDietTable.setItems(allDietResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextAllDietPage() {
        if (allDietPageNumber < totalAllDietPages) {
            allDietPageNumber++;
            calculateAllDietPages();
        }
    }

    /**
     * Handles the clicking to the previous page.
     */
    public void previousAllDietPage() {
        if (allDietPageNumber > 1) {
            allDietPageNumber--;
        }
        calculateAllDietPages();
    }

    /**
     * Takes care of calculating the right amount of pages to navigate the selected diet table properly.
     */
    public void calculateSelectedDietPages() {
        Pair<Integer, Integer> pair = calculatePages(selectedDietResult, totalSelectedDietPages, selectedDietPageNumber, selectedDiets, 6);
        totalSelectedDietPages = pair.getKey();
        selectedDietPageNumber = pair.getValue();
        pagesTextSelectedDiet.setText(selectedDietPageNumber + " / " + totalSelectedDietPages + " pages");
        selectedDietTable.setItems(selectedDietResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextSelectedDietPage() {
        if (selectedDietPageNumber < (int) totalSelectedDietPages) {
            selectedDietPageNumber++;
            calculateSelectedDietPages();
        }
    }

    /**
     * Handles the clicking to the previous page.
     */
    public void previousSelectedDietPage() {
        if (selectedDietPageNumber > 1) {
            selectedDietPageNumber--;
        }
        calculateSelectedDietPages();
    }

    /**
     * Takes care of loading all current allergies
     */
    public void displayCurrentAllergies() {
        try {
            currentAllergies = JsonMapper.allergiesListMapper(RestaurantServerCommunication.filterAllergies(""));
        } catch (Exception e) {
            currentAllergies = new ArrayList<>();
        }
        calculateAllDietPages();
    }

    /**
     * Displays all removed allergies for filtering and selection
     */
    public void displayRemovedAllergies() {
        selectedDiets = new ArrayList<>();
        calculateSelectedDietPages();
    }

    /**
     * Activates table listeners that check if a row is selected and adds a select button next to the table
     */
    public void addAllergyTableListeners() {
        allDietTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (allDietTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            restaurantTable.getSelectionModel().clearSelection();
            dishTable.getSelectionModel().clearSelection();
            orderTable.getSelectionModel().clearSelection();
            allergyTable.getSelectionModel().clearSelection();
            selectedDietTable.getSelectionModel().clearSelection();
            selectedAllergy = allDietTable.getSelectionModel().getSelectedItem();

            anchorPane.getChildren().remove(selectDietButton);
            anchorPane.getChildren().remove(removeDietButton);
            for (int i = 0; i < allDietResult.size(); i++) {
                assert selectedAllergy != null;
                if (allDietResult.get(i).getAllergyName().equals(selectedAllergy.getAllergyName())) {
                    selectDietButton = new Button("Add");
                    selectDietButton.setLayoutX(654);
                    selectDietButton.setLayoutY(93 + (24  * (i + 1)));
                    selectDietButton.setMinWidth(50);
                    selectDietButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    selectDietButton.setMaxHeight(24);
                    selectDietButton.setOnAction(event -> selectDiet());
                    anchorPane.getChildren().add(selectDietButton);
                }
            }
        });

        selectedDietTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (selectedDietTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            restaurantTable.getSelectionModel().clearSelection();
            dishTable.getSelectionModel().clearSelection();
            orderTable.getSelectionModel().clearSelection();
            allergyTable.getSelectionModel().clearSelection();
            allDietTable.getSelectionModel().clearSelection();
            if (selectedDietTable.getSelectionModel().getSelectedItem() != null
                    || !selectedDiets.contains(selectedAllergy)
                    || !anchorPane.getChildren().contains(removeDietButton)) {
                anchorPane.getChildren().remove(removeDietButton);
                selectedAllergy = selectedDietTable.getSelectionModel().getSelectedItem();
            }
            for (int i = 0; i < selectedDietResult.size(); i++) {
                assert selectedAllergy != null;
                if (selectedDietResult.get(i).getAllergyName().equals(selectedAllergy.getAllergyName())) {
                    removeDietButton = new Button("Remove");
                    removeDietButton.setLayoutX(933);
                    removeDietButton.setLayoutY(93 + (24  * (i + 1)));
                    removeDietButton.setMinWidth(50);
                    removeDietButton.setStyle("-fx-background-color:  #cc3c1f; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    removeDietButton.setMaxHeight(24);
                    removeDietButton.setOnAction(event -> addAllergy());
                    anchorPane.getChildren().add(removeDietButton);
                }
            }

            anchorPane.getChildren().remove(selectDietButton);
            for (int i = 0; i < allDietResult.size(); i++) {
                assert selectedAllergy != null;
                if (allDietResult.get(i).getAllergyName().equals(selectedAllergy.getAllergyName())) {
                    selectDietButton = new Button("Add");
                    selectDietButton.setLayoutX(654);
                    selectDietButton.setLayoutY(93 + (24  * (i + 1)));
                    selectDietButton.setMinWidth(50);
                    selectDietButton.setStyle("-fx-background-color:  #46cc00; -fx-font-size:10; -fx-text-fill: white; -fx-font:12 system;");
                    selectDietButton.setMaxHeight(24);
                    selectDietButton.setOnAction(event -> selectDiet());
                    anchorPane.getChildren().add(selectDietButton);
                }
            }
        });
    }

    /**
     * Takes care of selecting a diet.
     */
    public void selectDiet() {
        anchorPane.getChildren().remove(removeDietButton);
        anchorPane.getChildren().remove(selectDietButton);
        selectedDiets.add(selectedAllergy);
        currentAllergies.remove(selectedAllergy);
        calculateAllDietPages();
        calculateSelectedDietPages();
        applyDishFilters();
    }

    /**
     * Takes care of adding allergies.
     */
    public void addAllergy() {
        anchorPane.getChildren().remove(removeDietButton);
        anchorPane.getChildren().remove(selectDietButton);
        currentAllergies.add(selectedAllergy);
        selectedDiets.remove(selectedAllergy);
        calculateAllDietPages();
        calculateSelectedDietPages();
        applyDishFilters();
    }
}
