package nl.tudelft.oopp.demo.controllers.database;

import static nl.tudelft.oopp.demo.controllers.database.DatabaseRestaurantMenuAlertController.daySwitchCase;
import static nl.tudelft.oopp.demo.controllers.database.DatabaseRestaurantMenuAlertController.disable;
import static nl.tudelft.oopp.demo.controllers.database.DatabaseRestaurantMenuAlertController.updateDishAlert;
import static nl.tudelft.oopp.demo.controllers.database.DatabaseRestaurantMenuAlertController.updateRestaurantAlert;
import static nl.tudelft.oopp.demo.controllers.database.DatabaseRestaurantMenuAlertController.updateRestaurantHoursAlert;
import static nl.tudelft.oopp.demo.controllers.generic.CalculatePages.calculatePages;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
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
import javafx.util.Pair;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
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

    @FXML private AnchorPane anchorPane;
    @FXML private ToggleButton restaurantToggleButton;
    @FXML private ToggleButton allergiesToggleButton;
    @FXML private ToggleButton hoursToggleButton;
    @FXML private Button previousPageButtonAllergiesSelected;
    @FXML private Button nextPageButtonAllergiesSelected;
    @FXML private Button updateRestaurantButton;
    @FXML private Button restaurantPreviousPageButton;
    @FXML private Button restaurantNextPageButton;
    @FXML private Button allergyAddButton;
    @FXML private Button menuDeleteButton;
    @FXML private Button setClosedButton;
    @FXML private Button deleteRestaurantButton;
    @FXML private Button updateRestaurantHoursButton;
    @FXML private ChoiceBox<String> daysChoiceBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField idFieldRead;
    @FXML private TextField ownerTextField;
    @FXML private TextField nameFieldRead;
    @FXML private TextField locationFieldRead;
    @FXML private TextField menuIdFieldRead;
    @FXML private TextField menuNameFieldRead;
    @FXML private TextField dishIdFieldRead;
    @FXML private TextField dishNameFieldRead;
    @FXML private TextField dishPriceFieldRead;
    @FXML private TextField allergyNameTextField;
    @FXML private TextField dishImageTextField;
    @FXML private TextField hoursStartTime;
    @FXML private TextField minutesStartTime;
    @FXML private TextField hoursEndTime;
    @FXML private TextField minutesEndTime;
    @FXML private TextArea dishDescriptionFieldRead;
    @FXML private Text restaurantHoursText;
    @FXML private Text dayText;
    @FXML private Text dateText;
    @FXML private Text fromText;
    @FXML private Text toText;
    @FXML private Text semiColonText1;
    @FXML private Text semiColonText2;
    @FXML private Text showAddAllergiesText;
    @FXML private Text restaurantPagesText;
    @FXML private Text pagesText;
    @FXML private Text pagesTextAllergiesCurrent;
    @FXML private Text restaurantAddText;
    @FXML private Text menuAddText;
    @FXML private ImageView allergyImage;
    @FXML private ImageView showAddAllergiesButton;
    @FXML private ImageView restaurantAddImage;
    @FXML private ImageView menuAddImage;
    @FXML private TableView<Dish> dishTableView;
    @FXML private TableView<Restaurant> restaurantTable;
    @FXML private TableView<Allergy> allergiesTableCurrent;
    @FXML private TableColumn<Restaurant, String> colOwnedRestaurants;
    @FXML private TableColumn<Allergy, String> colAllergyName;
    @FXML private TableColumn<Dish, String> colDishName;
    @FXML private TableColumn<Dish, Double> colDishPrice;

    private Button deleteButton;
    private Button deleteButtonAllergies;

    private Boolean allergiesTableFlag;
    private Boolean restaurantTableFlag;
    private Boolean hoursToggleFlag;

    private int pageNumber;
    private int restaurantId;
    private int allergySelectedPageNumber;
    private int restaurantPageNumber;

    private int totalPages;
    private int totalRestaurantPages;
    private int totalAllergySelectedPages;

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param location The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if resource is not known
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
            disable(idFieldRead);
            disable(nameFieldRead);
            disable(locationFieldRead);
            disable(menuIdFieldRead);
            disable(dishIdFieldRead);
            disable(ownerTextField);
        }

        allergiesTableFlag = true;
        restaurantTableFlag = true;
        hoursToggleFlag = true;

        // Removes general elements that are hidden / shown by toggle boxes.
        // Allergies selection table elements
        anchorPane.getChildren().removeAll(previousPageButtonAllergiesSelected, nextPageButtonAllergiesSelected,
                pagesTextAllergiesCurrent, allergiesTableCurrent, showAddAllergiesText, showAddAllergiesButton);
        // Select restaurant elements
        anchorPane.getChildren().removeAll(restaurantTable, restaurantPreviousPageButton, restaurantPagesText, restaurantNextPageButton);
        // Add allergies elements
        anchorPane.getChildren().removeAll(allergyImage, allergyNameTextField, allergyAddButton);
        // Restaurant Hours elements
        anchorPane.getChildren().removeAll(dayText, dateText, daysChoiceBox, datePicker, hoursStartTime, hoursEndTime,
                minutesEndTime, minutesStartTime, setClosedButton, updateRestaurantButton, updateRestaurantHoursButton,
                restaurantHoursText, fromText, toText, semiColonText1, semiColonText2);

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
        setListeners();
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to to add dishes menu
     */
    public void goToAddDishes() {
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
     * @throws IOException Should never throw the exception
     */
    public void goToAddRestaurants() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRestaurants.fxml");
    }

    /**
     * Goes to to add 'menu' menu
     * @throws IOException Should never throw the exception
     */
    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    /**
     * Goes to to add 'menu' menu
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
            anchorPane.getChildren().addAll(previousPageButtonAllergiesSelected, nextPageButtonAllergiesSelected,
                    pagesTextAllergiesCurrent, allergiesTableCurrent, showAddAllergiesText, showAddAllergiesButton);
            retrieveAllAllergies();
        } else {
            allergiesToggleButton.setText("Edit");
            anchorPane.getChildren().removeAll(previousPageButtonAllergiesSelected, nextPageButtonAllergiesSelected,
                    pagesTextAllergiesCurrent, allergiesTableCurrent, showAddAllergiesText, showAddAllergiesButton);
            anchorPane.getChildren().removeAll(allergyImage, allergyNameTextField, allergyAddButton);
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
            anchorPane.getChildren().addAll(restaurantTable, restaurantPreviousPageButton, restaurantPagesText, restaurantNextPageButton);
            calculateRestaurantPages();
        } else {
            restaurantToggleButton.setText(" Select");
            anchorPane.getChildren().removeAll(restaurantTable, restaurantPreviousPageButton, restaurantPagesText, restaurantNextPageButton);
        }
        restaurantTableFlag = !restaurantTableFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleHours() {
        if (hoursToggleFlag) {
            hoursToggleButton.setText("Close");
            anchorPane.getChildren().addAll(dayText, dateText, daysChoiceBox, datePicker, hoursStartTime, hoursEndTime,
                    minutesEndTime, minutesStartTime, setClosedButton, updateRestaurantButton, updateRestaurantHoursButton,
                    restaurantHoursText, fromText, toText, semiColonText1, semiColonText2);
        } else {
            hoursToggleButton.setText(" Edit");
            anchorPane.getChildren().removeAll(dayText, dateText, daysChoiceBox, datePicker, hoursStartTime, hoursEndTime,
                    minutesEndTime, minutesStartTime, setClosedButton, updateRestaurantButton, updateRestaurantHoursButton,
                    restaurantHoursText, fromText, toText, semiColonText1, semiColonText2);
        }
        hoursToggleFlag = !hoursToggleFlag;
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < totalPages) {
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
        if (restaurantPageNumber < totalRestaurantPages) {
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
        if (allergySelectedPageNumber < totalAllergySelectedPages) {
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
        Pair<Integer, Integer> pair = calculatePages(dishList, totalPages, pageNumber, dishes, 6);
        totalPages = pair.getKey();
        pageNumber = pair.getValue();
        pagesText.setText(pageNumber + " / " + totalPages + " pages");
        dishTableView.setItems(dishList);
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveOwnedRestaurants() {
        try {
            restaurants = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.restaurantListMapper(RestaurantServerCommunication.getOwnedRestaurants())));
        } catch (Exception e) {
            restaurants = new ArrayList<>();
        }
        calculateRestaurantPages();
    }

    /**
     * Takes care of calculating and displaying the right pages in the Restaurant table
     */
    public void calculateRestaurantPages() {
        Pair<Integer, Integer> pair = calculatePages(ownedRestaurants, totalRestaurantPages, restaurantPageNumber, restaurants, 6);
        totalRestaurantPages = pair.getKey();
        restaurantPageNumber = pair.getValue();
        restaurantPagesText.setText(restaurantPageNumber + " / " + totalRestaurantPages + " pages");
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
        Pair<Integer, Integer> pair = calculatePages(allergySelectedList, totalAllergySelectedPages, allergySelectedPageNumber, allergies, 6);
        totalAllergySelectedPages = pair.getKey();
        allergySelectedPageNumber = pair.getValue();
        pagesTextAllergiesCurrent.setText(allergySelectedPageNumber + " / " + totalAllergySelectedPages + " pages");
        allergiesTableCurrent.setItems(allergySelectedList);
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
        updateRestaurantAlert(idFieldRead, locationFieldRead, nameFieldRead, ownerTextField, this);
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
        updateDishAlert(dishIdFieldRead, dishNameFieldRead, dishPriceFieldRead, dishImageTextField, dishDescriptionFieldRead, this);
    }

    /**
     * Shows the add allergy button and text fields for adding an allergy to a dish.
     */
    public void showAddAllergies() {
        try {
            anchorPane.getChildren().addAll(allergyImage, allergyNameTextField, allergyAddButton);
        } catch (IllegalArgumentException e) {
            anchorPane.getChildren().addAll(allergyImage, allergyNameTextField, allergyAddButton);
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
                anchorPane.getChildren().removeAll(allergyImage, allergyNameTextField, allergyAddButton);
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
        updateRestaurantHoursAlert(daysChoiceBox, datePicker, idFieldRead, hoursEndTime,
                minutesEndTime, hoursStartTime, minutesStartTime, restaurantId);
    }

    /**
     * Sets the start and end time text fields.
     * @param restaurantId restaurant id.
     * @param day the day of the week represented in int (1 - 7)
     */
    public void setStartAndEndTimeTextFields(int restaurantId, int day) {
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
        daysList.addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        daysChoiceBox.getItems().addAll(daysList);
    }

    /**
     * Sets all the necessary listeners to perform actions, whenever the table rows are selected.
     */
    private void setListeners() {
        dishTableView.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Dish dish = dishTableView.getSelectionModel().getSelectedItem();
            if (dish != null) { // Sets all the fields if there's a valid selection.
                dishIdFieldRead.setText(Integer.toString(dish.getId()));
                dishNameFieldRead.setText(dish.getName());
                dishPriceFieldRead.setText(Double.toString((double) dish.getPrice() / 100));
                dishDescriptionFieldRead.setText(dish.getDescription());
                dishImageTextField.setText(dish.getImage());
            } else { // If the selection is not valid, clear the fields so that fields don't stay hanging if you switch
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
                    deleteButton.setLayoutX(973);
                    deleteButton.setLayoutY(179 + (24 * (i + 1)));
                    deleteButton.setMinWidth(50);
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

        restaurantTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            restaurant = restaurantTable.getSelectionModel().getSelectedItem();
            if (restaurant != null) { // Sets the fields
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

        allergiesTableCurrent.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButtonAllergies);

            final Allergy allergy = allergiesTableCurrent.getSelectionModel().getSelectedItem();

            // Takes care of placing the delete button next to the table
            for (int i = 0; i < allergySelectedList.size(); i++) {
                assert allergy != null;
                if (allergySelectedList.get(i).getAllergyName().equals(allergy.getAllergyName())) {
                    deleteButtonAllergies = new Button("Delete");
                    deleteButtonAllergies.setLayoutX(1200);
                    deleteButtonAllergies.setLayoutY(180 + (24 * (i + 1)));
                    deleteButtonAllergies.setMinWidth(50);
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
        datePicker.valueProperty().addListener((obs) -> daysChoiceBox.setValue(null));

        daysChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final String dayName = daysChoiceBox.getSelectionModel().getSelectedItem();
            if (dayName != null) {
                datePicker.setValue(null);
            }
            if (!idFieldRead.getText().isEmpty()) {
                int restaurantId = Integer.parseInt(idFieldRead.getText());
                int day = 0;
                if (dayName != null) {
                    day = daySwitchCase(daysChoiceBox);
                    if (day > 0) {
                        setStartAndEndTimeTextFields(restaurantId, day);
                    }
                }
            }
        });
    }
}
