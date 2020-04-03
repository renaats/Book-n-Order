package nl.tudelft.oopp.demo.controllers.restaurants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "OrderFoodChooseRestaurant.fxml" file
 */
public class OrderFoodChooseRestaurantController implements Initializable {
    private final ObservableList<Restaurant> restaurantResult = FXCollections.observableArrayList();
    private final ObservableList<String> buildingNameList = FXCollections.observableArrayList();

    @FXML
    public TableView<Restaurant> restaurantTable;
    @FXML
    public TableView<Dish> dishTable;
    @FXML
    public TableView<Dish> foodOrderTable;
    @FXML
    public TableColumn<Restaurant, String> colRestaurantName;
    @FXML
    public TableColumn<Restaurant, Building> colRestaurantBuilding;
    @FXML
    public TableColumn<Dish, String> colDishName;
    @FXML
    public TableColumn<Dish, Double> colDishPrice;
    @FXML
    public TableColumn<Dish, String> colOrderDishName;
    @FXML
    public TableColumn<Dish, Double> colOrderPrice;
    @FXML
    public TableColumn<Dish, Integer> colOrderAmount;

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
//        colRestaurantName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        colRestaurantBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
//        colDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        colScreen.setCellValueFactory(new PropertyValueFactory<>("screen"));
//        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
//        colPlugs.setCellValueFactory(new PropertyValueFactory<>("plugs"));
//
//        if (pageNumber == 0) {
//            pageNumber++;
//        }
//
//        applyFilters();
//        loadBuildingChoiceBox();
    }
}
