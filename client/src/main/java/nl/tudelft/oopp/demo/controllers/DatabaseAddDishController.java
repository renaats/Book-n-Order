package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Manages the User inputs for the DatabaseAddDish.fxml scene
 */
public class DatabaseAddDishController implements Initializable {

    final ObservableList<Menu> menus = FXCollections.observableArrayList();

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> menuChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menus.addAll(Objects.requireNonNull(JsonMapper.menuListMapper(ServerCommunication.getMenus())));
        loadDataToChoiceBoxes();
        for (Menu menu : menus) {
            menuChoiceBox.getItems().add(menu.getName());
        }
    }

    /**
     * Loads the restaurants and dishes to the choice boxes
     */
    private void loadDataToChoiceBoxes() {
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to add menu view
     * @throws IOException Should never throw the exception
     */
    public void goToAddMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddMenu.fxml");
    }

    /**
     * Goes to the edit menu view
     * @throws IOException Should never throw the exception
     */
    public void goToEditMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditMenu.fxml");
    }

    /**
     * Goes to the menu for editing restaurants
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantsMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseFoodMainMenu.fxml");
    }

    /**
     * Adds a dish to the database with the given menu and given name
     */
    public void databaseAddDish() {
        String name = nameTextField.getText();
        if (name.equals("")) {
            CustomAlert.warningAlert("please input a name");
        }
        String menuName = menuChoiceBox.getValue();
        if (name.equals(null)) {
            CustomAlert.warningAlert("select a menu");
        }
        int menuId = -1;
        for (Menu value : menus) {
            if (menuName.equals(value.getName())) {
                menuId = value.getId();
            }
        }
        CustomAlert.informationAlert(ServerCommunication.addDish(name, menuId));
    }
}
