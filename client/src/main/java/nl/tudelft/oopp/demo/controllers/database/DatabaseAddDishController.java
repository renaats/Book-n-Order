package nl.tudelft.oopp.demo.controllers.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the User inputs for the DatabaseAddDish.fxml scene
 */
public class DatabaseAddDishController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField menuTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextArea descriptionTextArea;

    private Menu menu;
    private int menuId;

    public DatabaseAddDishController(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (menu != null) {
            menuId = menu.getId();
            menuTextField.setText(menu.getName());
        } else {
            CustomAlert.errorAlert("No menu found.");
        }
        menuTextField.setDisable(true);
        menuTextField.setOpacity(0.75);
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to the edit menu view
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    /**
     * Adds a dish to the database with the given menu and given name
     */
    public void databaseAddDish() {
        String name = nameTextField.getText();
        if (name.equals("")) {
            CustomAlert.warningAlert("Name cannot be empty.");
            return;
        }
        double price = -1.00;
        try {
            if (priceTextField.getText().contains(",")) {
                CustomAlert.warningAlert("Please use a period.");
                return;
            }
            price = Double.parseDouble(priceTextField.getText());
            if (price == -1.00) {
                CustomAlert.warningAlert("Price cannot be empty.");
                return;
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Price must be a double.");
            return;
        }
        String description = descriptionTextArea.getText();
        if (description.equals("")) {
            CustomAlert.warningAlert("Description cannot be empty.");
            return;
        }
        String image = imageTextField.getText();
        String response = RestaurantServerCommunication.addDish(name, menuId, (int) price * 100, description, image);
        if (response.equals(ErrorMessages.getErrorMessage(201))) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.warningAlert(response);
        }
    }
}
