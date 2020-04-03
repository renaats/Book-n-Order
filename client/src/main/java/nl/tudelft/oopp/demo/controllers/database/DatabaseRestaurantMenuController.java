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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseRestaurantMenuController implements Initializable {

    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();

    @FXML
    private Button previousPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private Button previousPageButtonAllergiesSelected;
    @FXML
    private Button nextPageButtonAllergiesSelected;
    @FXML
    private Button previousPageButtonAllergiesAvailable;
    @FXML
    private Button nextPageButtonAllergiesAvailable;
    @FXML
    private Button addNewDishButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField idFieldRead;
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
    private TextArea dishDescriptionFieldRead;
    @FXML
    private Text selectedTextField;
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
    private TableView<Dish> allergiesTableCurrent;
    @FXML
    private TableView<Dish> allergiesTableAvailable;
    @FXML
    private TableColumn<Dish, String> colDishName;
    @FXML
    private TableColumn<Dish, Integer> colDishPrice;
    @FXML
    private Text addDishText;

    private Boolean allergiesTableFlag;
    private int pageNumber;
    private double totalPages;

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
        allergiesTableFlag = true;
        anchorPane.getChildren().remove(previousPageButtonAllergiesSelected);
        anchorPane.getChildren().remove(nextPageButtonAllergiesSelected);
        anchorPane.getChildren().remove(pagesTextAllergiesCurrent);
        anchorPane.getChildren().remove(allergiesTableCurrent);
        anchorPane.getChildren().remove(allergiesTableAvailable);
        anchorPane.getChildren().remove(previousPageButtonAllergiesAvailable);
        anchorPane.getChildren().remove(nextPageButtonAllergiesAvailable);
        anchorPane.getChildren().remove(selectedTextField);

        pageNumber = 1;

        List<Restaurant> ownedRestaurants = null;
        try {
            ownedRestaurants = JsonMapper.ownRestaurantMapper(ServerCommunication.getOwnedRestaurant());
        } catch (Exception e) {
            e.printStackTrace();
            // Intentially left blank
        }

        if (ownedRestaurants.get(0) != null) {
            idFieldRead.setText(Integer.toString(ownedRestaurants.get(0).getId()));
            nameFieldRead.setText(ownedRestaurants.get(0).getName());
            locationFieldRead.setText(ownedRestaurants.get(0).getBuilding().getName());
            Menu menu = JsonMapper.menuMapper(ServerCommunication.findMenuByRestaurant(ownedRestaurants.get(0).getId()));
            menuIdFieldRead.setText(Integer.toString(menu.getId()));
            menuNameFieldRead.setText(menu.getName());
        }

        colDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDishPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        retrieveAllDishes();
        tableSelectMethod();
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
            allergiesToggleButton.setText("Edit");
            anchorPane.getChildren().add(previousPageButtonAllergiesSelected);
            anchorPane.getChildren().add(nextPageButtonAllergiesSelected);
            anchorPane.getChildren().add(pagesTextAllergiesCurrent);
            anchorPane.getChildren().add(allergiesTableCurrent);
            anchorPane.getChildren().add(allergiesTableAvailable);
            anchorPane.getChildren().add(previousPageButtonAllergiesAvailable);
            anchorPane.getChildren().add(nextPageButtonAllergiesAvailable);
            anchorPane.getChildren().add(selectedTextField);
        } else {
            allergiesToggleButton.setText("Close");
            anchorPane.getChildren().remove(previousPageButtonAllergiesSelected);
            anchorPane.getChildren().remove(nextPageButtonAllergiesSelected);
            anchorPane.getChildren().remove(pagesTextAllergiesCurrent);
            anchorPane.getChildren().remove(allergiesTableCurrent);
            anchorPane.getChildren().remove(allergiesTableAvailable);
            anchorPane.getChildren().remove(previousPageButtonAllergiesAvailable);
            anchorPane.getChildren().remove(nextPageButtonAllergiesAvailable);
            anchorPane.getChildren().remove(selectedTextField);
        }
        allergiesTableFlag = !allergiesTableFlag;
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
     * Handles clicking the list button.
     */
    public void retrieveAllDishes() {
        dishList.clear();
        List<Dish> dishes;
        try {
            dishes = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.dishListMapper(ServerCommunication.findDishesByMenu(Integer.parseInt(menuIdFieldRead.getText())))));
        } catch (Exception e) {
            e.printStackTrace();
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            dishes = new ArrayList<>();
            dishes.add(null);
        }

        totalPages = Math.ceil(dishes.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (dishes.size() > 15) {
            for (int i = 1; i < 16; i++) {
                try {
                    dishList.add(dishes.get((i - 15) + pageNumber * 15));
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
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        dishTableView.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Dish dish = dishTableView.getSelectionModel().getSelectedItem();
            if (dish != null) {
                dishIdFieldRead.setText(Integer.toString(dish.getId()));
                dishNameFieldRead.setText(dish.getName());
                dishPriceFieldRead.setText(Integer.toString(dish.getId()));
                dishDescriptionFieldRead.setText(dish.getDescription());
            }

            for (int i = 0; i < dishList.size(); i++) {
                assert dish != null;
                if (dishList.get(i).getId() == (dish.getId())) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(505);
                    deleteButton.setLayoutY(434 + (24 * (i + 1)));
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
                        String response = ServerCommunication.deleteDish(dish.getId());
                        retrieveAllDishes();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }
}
