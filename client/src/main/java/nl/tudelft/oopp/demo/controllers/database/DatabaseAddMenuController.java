package nl.tudelft.oopp.demo.controllers.database;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the User inputs for the DatabaseAddMenu.fxml scene
 */
public class DatabaseAddMenuController implements Initializable {

    private final ObservableList<Restaurant> restaurantResult = FXCollections.observableArrayList();

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> restaurantChoiceBox;
    @FXML
    private Text pagesText;
    @FXML
    private TextField restaurantNameTextField;
    @FXML
    private ToggleButton tableToggle;
    @FXML
    private TableView<Restaurant> table;
    @FXML
    private Button nextPageButton;
    @FXML
    private Button previousPageButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<Restaurant, Integer> colId;
    @FXML
    private TableColumn<Restaurant, String> colName;

    private boolean tableToggleFlag;
    private int pageNumber;
    private double totalPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        anchorPane.getChildren().removeAll(table, nextPageButton, previousPageButton, pagesText);

        pageNumber = 1;

        retrieveAllRestaurants();
        tableSelectMethod();
    }

    /**
     * Returns to the main database menu.
     * @throws IOException Should never throw the exception.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to the menu for editing restaurants.
     * @throws IOException Should never throw the exception.
     */
    public void goToRestaurantsMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    /**
     * Adds a menu to the database when the add menu button is clicked.
     */
    public void databaseAddMenu() {
        boolean restaurantFound = false;
        int restaurantId = -1;
        String name = nameTextField.getText();
        if (name.equals("")) {
            CustomAlert.warningAlert("Please provide a name.");
            return;
        }
        try {
            restaurantId = Integer.parseInt(restaurantNameTextField.getText());
        } catch (NumberFormatException e) {
            Restaurant restaurant = null;
            if (!restaurantNameTextField.getText().equals("")) {
                restaurant = JsonMapper.restaurantMapper(RestaurantServerCommunication.findRestaurantByName(restaurantNameTextField.getText()));
            } else {
                CustomAlert.warningAlert("Please provide a restaurant.");
                return;
            }
            if (restaurant == null) {
                return;
            } else {
                restaurantId = restaurant.getId();
                restaurantFound = true;
            }
        }
        String response = RestaurantServerCommunication.addMenu(name, restaurantId);
        if (response.equals("Successfully added!") && restaurantFound) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * Handles retrieving all menus and loads them into the table.
     */
    public void retrieveAllRestaurants() {
        restaurantResult.clear();
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            restaurants = new ArrayList<>(Objects.requireNonNull(JsonMapper.restaurantListMapper(RestaurantServerCommunication.getRestaurants())));
        } catch (Exception e) {
            table.setPlaceholder(new Label(""));
        }

        totalPages = Math.ceil(restaurants.size() / 7.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (restaurants.size() > 7) {
            for (int i = 0; i < 7; i++) {
                try {
                    restaurantResult.add(restaurants.get((i - 7) + pageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            restaurantResult.addAll(restaurants);
        }
        table.setItems(restaurantResult);
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickMenuTable() {
        if (tableToggleFlag) {
            tableToggle.setText("Show");
            anchorPane.getChildren().removeAll(table, previousPageButton, nextPageButton, pagesText);
        } else {
            tableToggle.setText(" Hide");
            anchorPane.getChildren().addAll(table, previousPageButton, nextPageButton, pagesText);
        }
        tableToggleFlag = !tableToggleFlag;
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final Restaurant restaurant = table.getSelectionModel().getSelectedItem();
            if (restaurant != null) {
                restaurantNameTextField.setText(restaurant.getName());
            }
        });
    }

    /**
     * Handles the clicking to the next restaurants page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            retrieveAllRestaurants();
        }
    }

    /**
     * Handles the clicking to the previous restaurants page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllRestaurants();
    }
}
