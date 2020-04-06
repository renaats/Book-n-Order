package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseRestaurantMenuController implements Initializable {

    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();
    private final ObservableList<Allergy> allergySelectedList = FXCollections.observableArrayList();
    private final ObservableList<Restaurant> ownedRestaurants = FXCollections.observableArrayList();
    private final ObservableList<String> daysList = FXCollections.observableArrayList();
    private List<Allergy> allergies;
    private List<Dish> dishes;
    private List<Restaurant> restaurants;
    private Restaurant restaurant;

    @FXML
    private Button previousPageButtonAllergiesSelected;
    @FXML
    private Button nextPageButtonAllergiesSelected;
    @FXML
    private Button updateRestaurantButton;
    @FXML
    private Button restaurantPreviousPageButton;
    @FXML
    private Button restaurantNextPageButton;
    @FXML
    private TextField idFieldRead;
    @FXML
    private TextField ownerTextField;
    @FXML
    private TextField nameFieldRead;
    @FXML
    private TextField locationFieldRead;
    @FXML
    private TextField menuIdFieldRead;
    @FXML
    private TextField menuNameFieldRead;
    @FXML
    private TextField dishIdFieldRead;
    @FXML
    private TextField dishNameFieldRead;
    @FXML
    private TextField dishPriceFieldRead;
    @FXML
    private TextField allergyNameTextField;
    @FXML
    private TextField dishImageTextField;
    @FXML
    private ImageView allergyImage;
    @FXML
    private Text showAddAllergiesText;
    @FXML
    private Text restaurantPagesText;
    @FXML
    private ImageView showAddAllergiesButton;
    @FXML
    private Button allergyAddButton;
    @FXML
    private TextArea dishDescriptionFieldRead;
    @FXML
    private Text pagesText;
    @FXML
    private Text pagesTextAllergiesCurrent;
    @FXML
    private ToggleButton allergiesToggleButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Dish> dishTableView;
    @FXML
    private TableView<Restaurant> restaurantTable;
    @FXML
    private TableColumn<Restaurant, String> colOwnedRestaurants;
    @FXML
    private TableView<Allergy> allergiesTableCurrent;
    @FXML
    private TableColumn<Allergy, String> colAllergyName;
    @FXML
    private TableColumn<Dish, String> colDishName;
    @FXML
    private TableColumn<Dish, Double> colDishPrice;
    @FXML
    private ToggleButton restaurantToggleButton;
    @FXML
    private ImageView restaurantAddImage;
    @FXML
    private ImageView menuAddImage;
    @FXML
    private Text restaurantAddText;
    @FXML
    private Text menuAddText;
    @FXML
    private Button menuDeleteButton;
    @FXML
    private Button deleteRestaurantButton;
    @FXML
    private ChoiceBox<String> daysChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hoursStartTime;
    @FXML
    private TextField minutesStartTime;
    @FXML
    private TextField hoursEndTime;
    @FXML
    private TextField minutesEndTime;


    private Button deleteButton;
    private Button deleteButtonAllergies;

    private Boolean allergiesTableFlag;
    private Boolean restaurantTableFlag;
    private int pageNumber;
    private int restaurantId;
    private int allergySelectedPageNumber;
    private int restaurantPageNumber;
    private double totalPages;
    private double totalRestaurantPages;
    private double totalAllergySelectedPages;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  resource is not known
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!UserServerCommunication.isUserAdmin()) {
            // Removes all admin elements if a user is not an admin.
            anchorPane.getChildren().remove(restaurantAddImage);
            anchorPane.getChildren().remove(restaurantAddText);
            anchorPane.getChildren().remove(menuAddImage);
            anchorPane.getChildren().remove(menuAddText);
            anchorPane.getChildren().remove(menuDeleteButton);
            anchorPane.getChildren().remove(updateRestaurantButton);
            anchorPane.getChildren().remove(deleteRestaurantButton);
            idFieldRead.setDisable(true);
            idFieldRead.setOpacity(0.75);
            nameFieldRead.setDisable(true);
            nameFieldRead.setOpacity(0.75);
            locationFieldRead.setDisable(true);
            locationFieldRead.setOpacity(0.75);
            menuIdFieldRead.setDisable(true);
            menuIdFieldRead.setOpacity(0.75);
            dishIdFieldRead.setDisable(true);
            dishIdFieldRead.setOpacity(0.75);
            ownerTextField.setDisable(true);
            ownerTextField.setOpacity(0.75);
        }

        allergiesTableFlag = true;
        restaurantTableFlag = true;

        // Removes general elements that are hidden / shown by toggle boxes.
        anchorPane.getChildren().remove(previousPageButtonAllergiesSelected);
        anchorPane.getChildren().remove(nextPageButtonAllergiesSelected);
        anchorPane.getChildren().remove(pagesTextAllergiesCurrent);
        anchorPane.getChildren().remove(allergiesTableCurrent);
        anchorPane.getChildren().remove(allergyAddButton);
        anchorPane.getChildren().remove(allergyImage);
        anchorPane.getChildren().remove(allergyNameTextField);
        anchorPane.getChildren().remove(showAddAllergiesText);
        anchorPane.getChildren().remove(showAddAllergiesButton);
        anchorPane.getChildren().remove(restaurantTable);
        anchorPane.getChildren().remove(restaurantNextPageButton);
        anchorPane.getChildren().remove(restaurantPreviousPageButton);
        anchorPane.getChildren().remove(restaurantPagesText);

        pageNumber = 1;
        allergySelectedPageNumber = 1;
        restaurantPageNumber = 1;

        colDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDishPrice.setCellValueFactory(new PropertyValueFactory<>("priceInEuros"));
        colAllergyName.setCellValueFactory(new PropertyValueFactory<>("AllergyName"));
        colOwnedRestaurants.setCellValueFactory(new PropertyValueFactory<>("name"));

        allergiesTableCurrent.setPlaceholder(new Label(""));
        restaurantTable.setPlaceholder(new Label(""));
        dishTableView.setPlaceholder(new Label(""));

        loadDaysChoiceBox();
        retrieveOwnedRestaurants();
        dishTableSelectListener();
        restautantTableSelectListener();
        allergiesTableSelectListener();
        daysChoiceBoxListener();
        datePickerListener();
    }

    /**
     * Returns to the main database menu
     *
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to to add dishes menu
     *
     * @throws IOException Should never throw the exception
     */
    public void goToAddDishes() throws IOException {
        if (restaurant == null) {
            CustomAlert.warningAlert("Select a restaurant.");
            return;
        }
        try {
            Menu menu = JsonMapper.menuMapper(RestaurantServerCommunication.findMenuByRestaurant(restaurant.getId()));
            ApplicationDisplay.changeSceneWithVariables("/DatabaseAddDish.fxml", menu, null);
        } catch (Exception e) {
            // Left blank
        }
    }

    /**
     * Goes to to add restaurants menu
     *
     * @throws IOException Should never throw the exception
     */
    public void goToAddRestaurants() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    /**
     * Goes to to add 'menu' menu
     *
     * @throws IOException Should never throw the exception
     */
    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    /**
     * Goes to to add 'menu' menu
     *
     * @throws IOException Should never throw the exception
     */
    public void goToViewOrders() throws IOException {
        if (restaurant != null) {
            ApplicationDisplay.changeSceneWithVariables("/DatabaseViewFoodOrders.fxml", restaurant, null);
        } else {
            CustomAlert.warningAlert("Select a restaurant.");
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleAllergiesMenu() {
        if (allergiesTableFlag) {
            allergiesToggleButton.setText("Close");
            anchorPane.getChildren().add(previousPageButtonAllergiesSelected);
            anchorPane.getChildren().add(nextPageButtonAllergiesSelected);
            anchorPane.getChildren().add(pagesTextAllergiesCurrent);
            anchorPane.getChildren().add(allergiesTableCurrent);
            anchorPane.getChildren().add(showAddAllergiesText);
            anchorPane.getChildren().add(showAddAllergiesButton);
            retrieveAllAllergies();
        } else {
            allergiesToggleButton.setText("Edit");
            anchorPane.getChildren().remove(previousPageButtonAllergiesSelected);
            anchorPane.getChildren().remove(nextPageButtonAllergiesSelected);
            anchorPane.getChildren().remove(pagesTextAllergiesCurrent);
            anchorPane.getChildren().remove(allergiesTableCurrent);
            anchorPane.getChildren().remove(showAddAllergiesText);
            anchorPane.getChildren().remove(showAddAllergiesButton);
            anchorPane.getChildren().remove(allergyImage);
            anchorPane.getChildren().remove(allergyNameTextField);
            anchorPane.getChildren().remove(allergyAddButton);
            anchorPane.getChildren().remove(deleteButtonAllergies);
        }
        allergiesTableFlag = !allergiesTableFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleRestaurantList() {
        if (restaurantTableFlag) {
            restaurantToggleButton.setText(" Close");
            anchorPane.getChildren().add(restaurantTable);
            anchorPane.getChildren().add(restaurantPreviousPageButton);
            anchorPane.getChildren().add(restaurantPagesText);
            anchorPane.getChildren().add(restaurantNextPageButton);
            calculateRestaurantPages();
        } else {
            restaurantToggleButton.setText(" Select");
            anchorPane.getChildren().remove(restaurantTable);
            anchorPane.getChildren().remove(restaurantPreviousPageButton);
            anchorPane.getChildren().remove(restaurantPagesText);
            anchorPane.getChildren().remove(restaurantNextPageButton);
        }
        restaurantTableFlag = !restaurantTableFlag;
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            calculateDishPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        calculateDishPages();
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextRestaurantPage() {
        if (restaurantPageNumber < (int) totalRestaurantPages) {
            restaurantPageNumber++;
            retrieveOwnedRestaurants();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousRestaurantPage() {
        if (restaurantPageNumber > 1) {
            restaurantPageNumber--;
        }
        retrieveOwnedRestaurants();
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextAllergyPage() {
        if (allergySelectedPageNumber < (int) totalAllergySelectedPages) {
            allergySelectedPageNumber++;
            calculateAllergyPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousAllergyPage() {
        if (allergySelectedPageNumber > 1) {
            allergySelectedPageNumber--;
        }
        calculateAllergyPages();
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveAllDishes() {
        try {
            dishes = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.dishListMapper(RestaurantServerCommunication.findDishesByMenu(Integer.parseInt(menuIdFieldRead.getText())))));
        } catch (Exception e) {
            dishes = new ArrayList<>();
        }
        calculateDishPages();
    }

    /**
     * Takes care of calculating and displaying the right pages in the Dish table
     */
    public void calculateDishPages() {
        dishList.clear();

        totalPages = Math.ceil(dishes.size() / 7.0);

        if (dishes.size() == 0) {
            pageNumber = 0;
        }

        if (pageNumber == 0 && dishes.size() != 0) {
            pageNumber = 1;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (dishes.size() > 7) {
            for (int i = 0; i < 7; i++) {
                try {
                    dishList.add(dishes.get((i - 7) + pageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            dishList.addAll(dishes);
        }
        dishTableView.setItems(dishList);
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveOwnedRestaurants() {
        try {
            if (UserServerCommunication.getAdminButtonPermission()) {
                restaurants = new ArrayList<>(Objects.requireNonNull(
                        JsonMapper.restaurantListMapper(RestaurantServerCommunication.getRestaurants())));
            } else {
                restaurants = new ArrayList<>(Objects.requireNonNull(
                        JsonMapper.restaurantListMapper(RestaurantServerCommunication.getOwnedRestaurants())));
            }
        } catch (Exception e) {
            restaurants = new ArrayList<>();
        }
        calculateRestaurantPages();
    }

    /**
     * Takes care of calculating and displaying the right pages in the Restaurant table
     */
    public void calculateRestaurantPages() {
        ownedRestaurants.clear();
        totalRestaurantPages = Math.ceil(restaurants.size() / 6.0);

        if (restaurants.size() == 0) {
            restaurantPageNumber = 0;
        }

        if (pageNumber == 0 && restaurants.size() != 0) {
            restaurantPageNumber = 1;
        }

        restaurantPagesText.setText(restaurantPageNumber + " / " + (int) totalRestaurantPages + " pages");

        if (restaurants.size() > 6) {
            for (int i = 0; i < 6; i++) {
                try {
                    ownedRestaurants.add(restaurants.get((i - 6) + restaurantPageNumber * 6));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            ownedRestaurants.addAll(restaurants);
        }
        restaurantTable.setItems(ownedRestaurants);
    }

    /**
     * Retrieves all dishes
     */
    public void retrieveAllAllergies() {
        try {
            allergies = JsonMapper.allergiesListMapper(
                    RestaurantServerCommunication.getAllergiesFromDish(Integer.parseInt(dishIdFieldRead.getText())));
        } catch (Exception e) {
            allergies = new ArrayList<>();
        }
        calculateAllergyPages();
    }

    /**
     * Takes care of calculating and displaying the right pages in the Allergy table
     */
    public void calculateAllergyPages() {
        allergySelectedList.clear();
        totalAllergySelectedPages = Math.ceil(allergies.size() / 5.0);

        if (allergies.size() == 0) {
            allergySelectedPageNumber = 0;
        }

        if (allergySelectedPageNumber == 0 && allergies.size() != 0) {
            allergySelectedPageNumber = 1;
        }

        pagesTextAllergiesCurrent.setText(allergySelectedPageNumber + " / " + (int) totalAllergySelectedPages + " pages");

        if (allergies.size() > 5) {
            for (int i = 0; i < 5; i++) {
                try {
                    allergySelectedList.add(allergies.get((i - 5) + allergySelectedPageNumber * 5));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            allergySelectedList.addAll(allergies);
        }
        allergiesTableCurrent.setItems(allergySelectedList);
    }

    /**
     * Listener that checks if a row is selected in the dish table, if so, fill the text fields.
     */
    public void dishTableSelectListener() {
        dishTableView.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Dish dish = dishTableView.getSelectionModel().getSelectedItem();
            if (dish != null) {
                // Sets all the fields if there's a valid selection.
                dishIdFieldRead.setText(Integer.toString(dish.getId()));
                dishNameFieldRead.setText(dish.getName());
                dishPriceFieldRead.setText(Double.toString((double) dish.getPrice() / 100));
                dishDescriptionFieldRead.setText(dish.getDescription());
                dishImageTextField.setText(dish.getImage());
            } else {
                // If the selection is not valid, clear the fields so that fields don't stay hanging if you switch
                dishIdFieldRead.clear();
                dishNameFieldRead.clear();
                dishPriceFieldRead.clear();
                dishDescriptionFieldRead.clear();
                dishImageTextField.clear();
            }
            retrieveAllAllergies();

            // This takes care of the dynamic delete button to the side. It calculates where it should be and places it accordingly.
            for (int i = 0; i < dishList.size(); i++) {
                assert dish != null;
                if (dishList.get(i).getId() == (dish.getId())) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(1062);
                    deleteButton.setLayoutY(221 + (24 * (i + 1)));
                    deleteButton.setMinWidth(60);
                    deleteButton.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButton.setMinHeight(20);
                    deleteButton.setOnAction(event -> {
                        for (int i1 = 0; i1 < dishList.size(); i1++) {
                            if (dishList.get(i1).getId() == (dish.getId())) {
                                dishList.remove(dishList.get(i1));
                                anchorPane.getChildren().remove(deleteButton);
                            }
                        }
                        String response = RestaurantServerCommunication.deleteDish(dish.getId());
                        retrieveAllDishes();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }

    /**
     * Listener that checks if a row is selected in the restaurant table, if so, fill the text fields.
     */
    public void restautantTableSelectListener() {
        restaurantTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            restaurant = restaurantTable.getSelectionModel().getSelectedItem();
            if (restaurant != null) {
                // Sets the fields
                restaurantId = restaurant.getId();
                idFieldRead.setText(Integer.toString(restaurant.getId()));
                nameFieldRead.setText(restaurant.getName());
                locationFieldRead.setText(restaurant.getBuilding().getName());
                ownerTextField.setText(restaurant.getEmail());
                Menu menu = null;
                // Does there exist a menu check
                try {
                    menu = JsonMapper.menuMapper(RestaurantServerCommunication.findMenuByRestaurant(restaurant.getId()));
                } catch (JsonProcessingException e) {
                    // intentionally left blank
                }
                // Sets the menu fields
                if (menu != null) {
                    menuIdFieldRead.setText(Integer.toString(menu.getId()));
                    menuNameFieldRead.setText(menu.getName());
                } else {
                    menuNameFieldRead.clear();
                    menuIdFieldRead.clear();
                }
                retrieveAllDishes();
            }
        });
    }

    /**
     * Sets all fields to 0 to indicate that that means closed.
     */
    public void setClosedTextFields() {
        hoursStartTime.setText("0");
        minutesStartTime.setText("0");
        hoursEndTime.setText("0");
        minutesEndTime.setText("0");
    }

    /**
     * Listener that checks if a row is selected in the allergies table, if so, fill the text fields.
     */
    public void allergiesTableSelectListener() {
        allergiesTableCurrent.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButtonAllergies);

            final Allergy allergy = allergiesTableCurrent.getSelectionModel().getSelectedItem();

            // Takes care of placing the delete button next to the table
            for (int i = 0; i < allergySelectedList.size(); i++) {
                assert allergy != null;
                if (allergySelectedList.get(i).getAllergyName().equals(allergy.getAllergyName())) {
                    deleteButtonAllergies = new Button("Delete");
                    deleteButtonAllergies.setLayoutX(980);
                    deleteButtonAllergies.setLayoutY(462 + (24 * (i + 1)));
                    deleteButtonAllergies.setMinWidth(60);
                    deleteButtonAllergies.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButtonAllergies.setMinHeight(20);
                    deleteButtonAllergies.setOnAction(event -> {
                        for (int i1 = 0; i1 < allergySelectedList.size(); i1++) {
                            if (allergySelectedList.get(i1).getAllergyName().equals(allergy.getAllergyName())) {
                                allergySelectedList.remove(allergySelectedList.get(i1));
                                anchorPane.getChildren().remove(deleteButtonAllergies);
                            }
                        }
                        String response = RestaurantServerCommunication.deleteAllergyFromDish(
                                Integer.parseInt(dishIdFieldRead.getText()), allergy.getAllergyName());
                        retrieveAllAllergies();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButtonAllergies);
                }
            }
        });
    }

    /**
     * Takes care of updating the menu name.
     */
    public void updateMenuName() {
        String name = menuNameFieldRead.getText();
        if (name.equals("")) {
            CustomAlert.warningAlert("Please provide a name.");
        }
        CustomAlert.informationAlert(RestaurantServerCommunication.updateMenuName(Integer.parseInt(menuIdFieldRead.getText()), name));
    }

    /**
     * Takes care of updating all values for restaurant.
     */
    public void updateRestaurant() {
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
        retrieveOwnedRestaurants();
        CustomAlert.informationAlert("Successfully executed.");
    }

    /**
     * Takes care of deleting a menu.
     */
    public void deleteMenu() {
        int menuId = Integer.parseInt(menuIdFieldRead.getText());
        if (RestaurantServerCommunication.findDishesByMenu(menuId).equals("Not found.")) {
            CustomAlert.informationAlert(RestaurantServerCommunication.deleteMenu(menuId));
            menuIdFieldRead.clear();
            menuNameFieldRead.clear();
        } else {
            CustomAlert.warningAlert("Please delete all dishes first.");
        }
    }

    /**
     * Takes care of all the update values for a dish.
     */
    public void updateDish() {
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
        retrieveAllDishes();
    }

    /**
     * Shows the add allergy button and text fields for adding an allergy to a dish.
     */
    public void showAddAllergies() {
        try {
            anchorPane.getChildren().add(allergyImage);
            anchorPane.getChildren().add(allergyNameTextField);
            anchorPane.getChildren().add(allergyAddButton);
        } catch (IllegalArgumentException e) {
            anchorPane.getChildren().remove(allergyImage);
            anchorPane.getChildren().remove(allergyNameTextField);
            anchorPane.getChildren().remove(allergyAddButton);
        }
    }

    /**
     * Takes care of adding an allergy to a dish.
     */
    public void addAllergy() {
        if (allergyNameTextField.getText().isEmpty()) {
            CustomAlert.warningAlert("Please provide an allergy name.");
        } else {
            String name = allergyNameTextField.getText();
            if (menuIdFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("No dish selection detected.");
            } else {
                CustomAlert.informationAlert(RestaurantServerCommunication.addAllergyToDish(name, Integer.parseInt(dishIdFieldRead.getText())));
                anchorPane.getChildren().remove(allergyImage);
                anchorPane.getChildren().remove(allergyNameTextField);
                anchorPane.getChildren().remove(allergyAddButton);
                retrieveAllAllergies();
            }
        }
    }

    /**
     * Takes care of deleting a restaurant.
     */
    public void deleteRestaurant() {
        if (idFieldRead.getText().isEmpty()) {
            CustomAlert.warningAlert("No selection detected.");
        } else {
            CustomAlert.informationAlert(RestaurantServerCommunication.deleteRestaurant(Integer.parseInt(idFieldRead.getText())));
            retrieveOwnedRestaurants();
        }
    }

    /**
     * Takes care of updating building hours
     */
    public void updateRestaurantHours() {
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
            } else if (hoursEndTime.getText().isEmpty()
                    || minutesEndTime.getText().isEmpty()
                    || hoursStartTime.getText().isEmpty()
                    || hoursEndTime.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide opening and closing time.");
            } else if (Integer.parseInt(hoursStartTime.getText()) > 23 || Integer.parseInt(hoursEndTime.getText()) > 23) {
                CustomAlert.warningAlert("Hours cannot be larger than 23.");
            } else if (Integer.parseInt(minutesStartTime.getText()) > 59 || Integer.parseInt(minutesEndTime.getText()) > 59) {
                CustomAlert.warningAlert("Minutes cannot be larger than 59.");
            } else if (datePicker.getValue() == null) {
                // Switch case that turns the day string into a number
                switch (daysChoiceBox.getValue()) {
                    case "Monday":
                        day = 1;
                        break;
                    case "Tuesday":
                        day = 2;
                        break;
                    case "Wednesday":
                        day = 3;
                        break;
                    case "Thursday":
                        day = 4;
                        break;
                    case "Friday":
                        day = 5;
                        break;
                    case "Saturday":
                        day = 6;
                        break;
                    case "Sunday":
                        day = 7;
                        break;
                    default:
                        CustomAlert.errorAlert("Day not recognized.");
                        daysChoiceBox.setValue(null);
                }
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
                    CustomAlert.informationAlert(BuildingServerCommunication.addBuildingHours(restaurantId, dateInMs, startTime, endTime));
                } catch (NumberFormatException e) {
                    CustomAlert.warningAlert("Restaurant hours have to be an integer.");
                }
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Restaurant hours have to be an integer.");
        }
    }

    /**
     * Sets the choice box to null if the user interacts with the date picker to prevent duplicate dates.
     */
    public void datePickerListener() {
        datePicker.valueProperty().addListener((obs) -> daysChoiceBox.setValue(null));
    }

    /**
     * Automatically updates the fields when the user interacts with the choicebox.
     */
    public void daysChoiceBoxListener() {
        daysChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final String dayName = daysChoiceBox.getSelectionModel().getSelectedItem();
            if (dayName != null) {
                datePicker.setValue(null);
            }
            if (!idFieldRead.getText().isEmpty()) {
                int buildingId = Integer.parseInt(idFieldRead.getText());
                int day = 0;
                if (dayName != null) {
                    switch (dayName) {
                        case "Monday":
                            day = 1;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Tuesday":
                            day = 2;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Wednesday":
                            day = 3;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Thursday":
                            day = 4;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Friday":
                            day = 5;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Saturday":
                            day = 6;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        case "Sunday":
                            day = 7;
                            setStartAndEndTimeTextFields(buildingId, day);
                            break;
                        default:
                            CustomAlert.errorAlert("Day not recognized.");
                            daysChoiceBox.setValue(null);
                    }
                }
            }
        });
    }

    /**
     * Sets the start and end time text fields.
     * @param buildingId building id.
     * @param day the day of the week represented in int (1 - 7)
     */
    public void setStartAndEndTimeTextFields(int buildingId, int day) {
        try {
            RestaurantHours restaurantHours = JsonMapper.restaurantHoursMapper(
                    RestaurantServerCommunication.findRestaurantHoursByDay(restaurantId, day));
            LocalTime startTime = restaurantHours.getStartTime();
            hoursStartTime.setText(Integer.toString(startTime.getHour()));
            minutesStartTime.setText(Integer.toString(startTime.getMinute()));
            LocalTime endTime = restaurantHours.getEndTime();
            hoursEndTime.setText(Integer.toString(endTime.getHour()));
            minutesEndTime.setText(Integer.toString(endTime.getMinute()));
        } catch (JsonProcessingException e) {
            hoursStartTime.clear();
            minutesStartTime.clear();
            hoursEndTime.clear();
            minutesEndTime.clear();
        }
    }

    /**
     * Loads the days into the choice box.
     */
    private void loadDaysChoiceBox() {
        daysList.clear();
        String a = "Monday";
        String b = "Tuesday";
        String c = "Wednesday";
        String d = "Thursday";
        String e = "Friday";
        String f = "Saturday";
        String g = "Sunday";
        daysList.addAll(a, b, c, d, e, f, g);
        daysChoiceBox.getItems().addAll(daysList);
    }
}
