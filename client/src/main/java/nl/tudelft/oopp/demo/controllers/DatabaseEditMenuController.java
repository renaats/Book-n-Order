package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Manages the user inputs for the Edit menu scene
 */
public class DatabaseEditMenuController implements Initializable {

    final ObservableList<Menu> menuResult = FXCollections.observableArrayList();

    @FXML
    private TableView table;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colRestaurant;
    @FXML
    private TableColumn colDish;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataIntoChoiceBox();

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colRestaurant.setCellValueFactory(new PropertyValueFactory<>("Restaurant"));
        colDish.setCellValueFactory(new PropertyValueFactory<>("Dishes"));

        loadDataIntoTable();
    }

    private void loadDataIntoChoiceBox() {
    }

    private void loadDataIntoTable() {
        List<Menu> menus = new ArrayList<>(Objects.requireNonNull(JsonMapper.menuListMapper(ServerCommunication.getMenus())));
        menuResult.clear();
        menuResult.addAll(menus);
        table.setItems(menuResult);
        if (menuResult.isEmpty()) {
            CustomAlert.warningAlert("No menus found.");
        }
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
        ApplicationDisplay.changeScene("/DatabaseMenuFoodRestaurants.fxml");
    }

    public void deleteRoomButtonClicked() {
    }

    public void deleteRoomByTable() {
    }

    public void editMenuByTable() {
    }
}
