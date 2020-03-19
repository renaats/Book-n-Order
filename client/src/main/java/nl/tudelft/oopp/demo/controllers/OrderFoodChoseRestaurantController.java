package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "OrderFoodChoseRestaurant.fxml" file
 */
public class OrderFoodChoseRestaurantController implements Initializable {
    @FXML
    public TextField nameOfTheRestaurant;
    public Button submitButton;
    public TableView restaurantsTable;
    public TableColumn menuTable;
    public Button chooseTheRestaurantButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * will load the restaurants
     */
    public  void loadData(){
        //TODO
    }

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

    /**
     * Should apply the filters of the nameOfTheRestaurant text field to the restaurantsTable
     */
    public void applyFilters() {
        //TODO
    }

    /**
     * Should cause the menu of the selected restaurant to show at the menuTable
     */
    public void restaurantChoosen() {
        //TODO
    }
}
