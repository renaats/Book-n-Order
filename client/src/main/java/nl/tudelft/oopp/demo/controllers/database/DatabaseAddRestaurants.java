package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseAddRestaurants implements Initializable {

    private final ObservableList<Building> buildings = FXCollections.observableArrayList();
    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();
    private final ObservableList<Menu> menuResult = FXCollections.observableArrayList();
    private final ObservableList<Menu> menus = FXCollections.observableArrayList();

    @FXML
    private Text menuPagesText;
    @FXML
    private Text buildingPagesText;
    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<Building> buildingTable;
    @FXML
    private TableColumn<Building, Integer> colBuildingId;
    @FXML
    private TableColumn<Building, String> colBuildingName;
    @FXML
    private TableView<Building> menuTable;
    @FXML
    private TableColumn<Building, Integer> colMenuId;
    @FXML
    private TableColumn<Building, String> colMenuName;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button nextBuildingPageButton;
    @FXML
    private Button previousBuildingPageButton;
    @FXML
    private Button nextMenuPageButton;
    @FXML
    private Button previousMenuPageButton;
    @FXML
    private ToggleButton buildingsTableToggle;
    @FXML
    private ToggleButton menuTableToggle;

    private boolean buildingsTableToggleFlag;
    private boolean menuTableToggleFlag;
    private int buildingPageNumber;
    private int menuPageNumber;
    private double totalBuildingPages;
    private double totalMenuPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBuildingId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBuildingName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMenuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));

        anchorPane.getChildren().remove(buildingTable);
        anchorPane.getChildren().remove(nextBuildingPageButton);
        anchorPane.getChildren().remove(previousBuildingPageButton);
        anchorPane.getChildren().remove(buildingPagesText);

        anchorPane.getChildren().remove(menuTable);
        anchorPane.getChildren().remove(nextMenuPageButton);
        anchorPane.getChildren().remove(previousMenuPageButton);
        anchorPane.getChildren().remove(menuPagesText);
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Handles retrieving all buildings and loading them into the table.
     */
    public void retrieveAllBuildings() {
        buildingResult.clear();
        List<Building> buildings;
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            buildings = new ArrayList<>();
            buildings.add(null);
        }

        totalBuildingPages = Math.ceil(buildings.size() / 7.0);

        if (totalBuildingPages < buildingPageNumber) {
            buildingPageNumber--;
        }

        buildingPagesText.setText(buildingPageNumber + " / " + (int) totalBuildingPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 1; i < 8; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + buildingPageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            buildingResult.addAll(buildings);
        }
        buildingTable.setItems(buildingResult);
    }

    /**
     * Handles retrieving all menus and loads them into the table.
     */
    public void retrieveAllMenus() {
        buildingResult.clear();
        List<Building> buildings;
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            buildings = new ArrayList<>();
            buildings.add(null);
        }

        totalBuildingPages = Math.ceil(buildings.size() / 7.0);

        if (totalBuildingPages < buildingPageNumber) {
            buildingPageNumber--;
        }

        buildingPagesText.setText(buildingPageNumber + " / " + (int) totalBuildingPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 1; i < 8; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + buildingPageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            buildingResult.addAll(buildings);
        }
        buildingTable.setItems(buildingResult);
    }

    /**
     * Goes to the edit restaurant view
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickBuildingsTable() {
        if (buildingsTableToggleFlag) {
            buildingsTableToggle.setText("Show");
            anchorPane.getChildren().remove(buildingTable);
            anchorPane.getChildren().remove(previousBuildingPageButton);
            anchorPane.getChildren().remove(nextBuildingPageButton);
            anchorPane.getChildren().remove(buildingPagesText);

            if (menuTableToggleFlag) {
                menuTableToggleFlag = !menuTableToggleFlag;
            }
        } else {
            buildingsTableToggle.setText(" Hide");
            anchorPane.getChildren().add(buildingTable);
            anchorPane.getChildren().add(previousBuildingPageButton);
            anchorPane.getChildren().add(nextBuildingPageButton);
            anchorPane.getChildren().add(buildingPagesText);
        }
        buildingsTableToggleFlag = !buildingsTableToggleFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickMenuTable() {
        if (menuTableToggleFlag) {
            menuTableToggle.setText("Show");
            anchorPane.getChildren().remove(menuTable);
            anchorPane.getChildren().remove(previousMenuPageButton);
            anchorPane.getChildren().remove(nextMenuPageButton);
            anchorPane.getChildren().remove(menuPagesText);

            if (buildingsTableToggleFlag) {
                buildingsTableToggleFlag = !buildingsTableToggleFlag;
            }
        } else {
            menuTableToggle.setText(" Hide");
            anchorPane.getChildren().add(menuTable);
            anchorPane.getChildren().add(previousMenuPageButton);
            anchorPane.getChildren().add(nextMenuPageButton);
            anchorPane.getChildren().add(menuPagesText);
        }
        menuTableToggleFlag = !menuTableToggleFlag;
    }

}
