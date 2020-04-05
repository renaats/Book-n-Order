package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

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

import javafx.scene.control.Button;
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
import nl.tudelft.oopp.demo.communication.DishServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseRestaurantMenuController implements Initializable {

    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();
    private final ObservableList<Allergy> allergySelectedList = FXCollections.observableArrayList();
    private final ObservableList<Restaurant> ownedRestaurants = FXCollections.observableArrayList();

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

    private Button deleteButton;
    private Button deleteButtonAllergies;

    private Boolean allergiesTableFlag;
    private Boolean restaurantTableFlag;
    private int pageNumber;
    private double totalPages;
    private int allergySelectedPageNumber;
    private int restaurantPageNumber;
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

        retrieveOwnedRestaurants();
        dishTableSelectListener();
        restautantTableSelectListener();
        allergiesTableSelectListener();
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
        ApplicationDisplay.changeScene("/DatabaseAddDish.fxml");
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
            retrieveAllAllergies();
        } else {
            restaurantToggleButton.setText("Select");
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
            retrieveAllDishes();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllDishes();
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
            retrieveAllAllergies();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousAllergyPage() {
        if (allergySelectedPageNumber > 1) {
            allergySelectedPageNumber--;
        }
        retrieveAllAllergies();
    }

    /**
     * Handles clicking the list button.
     */
    public void retrieveAllDishes() {
        dishList.clear();
        List<Dish> dishes = new ArrayList<>();
        try {
            dishes = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.dishListMapper(DishServerCommunication.findDishesByMenu(Integer.parseInt(menuIdFieldRead.getText())))));
        } catch (Exception e) {
            dishTableView.setPlaceholder(new Label(""));
        }

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
        ownedRestaurants.clear();
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            if (UserServerCommunication.getAdminButtonPermission()) {
                restaurants = new ArrayList<>(Objects.requireNonNull(
                        JsonMapper.restaurantListMapper(DishServerCommunication.getRestaurants())));
            } else {
                restaurants = new ArrayList<>(Objects.requireNonNull(
                        JsonMapper.restaurantListMapper(DishServerCommunication.getOwnedRestaurants())));
            }
        } catch (Exception e) {
            restaurantTable.setPlaceholder(new Label(""));
        }

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
     * Handles clicking the list button.
     */
    public void retrieveAllAllergies() {
        allergySelectedList.clear();
        List<Allergy> allergies = new ArrayList<>();
        try {
            allergies = JsonMapper.allergiesListMapper(DishServerCommunication.getAllergiesFromDish(Integer.parseInt(dishIdFieldRead.getText())));
        } catch (Exception e) {
            allergiesTableCurrent.setPlaceholder(new Label(""));
        }

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
                dishIdFieldRead.setText(Integer.toString(dish.getId()));
                dishNameFieldRead.setText(dish.getName());
                dishPriceFieldRead.setText(Double.toString((double) dish.getPrice() / 100));
                dishDescriptionFieldRead.setText(dish.getDescription());
                dishImageTextField.setText(dish.getImage());
                retrieveAllAllergies();
            } else {
                dishIdFieldRead.clear();
                dishNameFieldRead.clear();
                dishPriceFieldRead.clear();
                dishDescriptionFieldRead.clear();
                dishImageTextField.clear();
                retrieveAllAllergies();
            }

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
                        String response = DishServerCommunication.deleteDish(dish.getId());
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
            final Restaurant restaurant = restaurantTable.getSelectionModel().getSelectedItem();
            if (restaurant != null) {
                idFieldRead.setText(Integer.toString(restaurant.getId()));
                nameFieldRead.setText(restaurant.getName());
                locationFieldRead.setText(restaurant.getBuilding().getName());
                ownerTextField.setText(restaurant.getEmail());
                Menu menu = null;
                try {
                    menu = JsonMapper.menuMapper(DishServerCommunication.findMenuByRestaurant(restaurant.getId()));
                } catch (JsonProcessingException e) {
                    // intentionally left blank
                }
                if (menu != null) {
                    menuIdFieldRead.setText(Integer.toString(menu.getId()));
                    menuNameFieldRead.setText(menu.getName());
                    retrieveAllDishes();
                } else {
                    menuNameFieldRead.clear();
                    menuIdFieldRead.clear();
                    retrieveAllDishes();
                }
            }
        });
    }

    /**
     * Listener that checks if a row is selected in the allergies table, if so, fill the text fields.
     */
    public void allergiesTableSelectListener() {
        allergiesTableCurrent.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButtonAllergies);

            final Allergy allergy = allergiesTableCurrent.getSelectionModel().getSelectedItem();

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
                        String response = DishServerCommunication.deleteAllergyFromDish(
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
        CustomAlert.informationAlert(DishServerCommunication.updateMenuName(Integer.parseInt(menuIdFieldRead.getText()), name));
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

        if (buildingFound) {
            if (nameFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a name.");
                return;
            } else {
                DishServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "name", nameFieldRead.getText());
            }
            if (locationFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a building.");
                return;
            } else {
                DishServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "building", Integer.toString(buildingId));
            }
            if (ownerTextField.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a owner.");
                return;
            } else {
                DishServerCommunication.updateRestaurant(Integer.parseInt(idFieldRead.getText()), "email", ownerTextField.getText());
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
        if (DishServerCommunication.findDishesByMenu(menuId).equals("Not found.")) {
            CustomAlert.informationAlert(DishServerCommunication.deleteMenu(menuId));
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
        try {

            double price = -1;
            if (dishNameFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a name.");
                return;
            } else {
                String name = dishNameFieldRead.getText();
                DishServerCommunication.updateDish(dishId, "name", name);
            }
            if (dishPriceFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a price.");
                return;
            } else {
                try {
                    price = Double.parseDouble(dishPriceFieldRead.getText());
                    DishServerCommunication.updateDish(dishId, "price", Integer.toString((int) (price * 100)));
                } catch (NumberFormatException e) {
                    CustomAlert.warningAlert("Price requires a number.");
                    return;
                }
            }

            DishServerCommunication.updateDish(dishId, "image", dishImageTextField.getText());

            if (dishDescriptionFieldRead.getText().isEmpty()) {
                CustomAlert.warningAlert("Please provide a description.");
                return;
            } else {
                String description = dishDescriptionFieldRead.getText();
                DishServerCommunication.updateDish(dishId, "description", description);
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
                CustomAlert.informationAlert(DishServerCommunication.addAllergyToDish(name, Integer.parseInt(dishIdFieldRead.getText())));
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
            CustomAlert.informationAlert(DishServerCommunication.deleteRestaurant(Integer.parseInt(idFieldRead.getText())));
            retrieveOwnedRestaurants();
        }
    }
}
