package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "BuildingInformation.fxml" file
 */
public class BuildingInformationController implements Initializable {

    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private TableView<Building> table;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colStreet;
    @FXML
    private TableColumn<Building, Integer> colHouseNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("getName"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("getStreet"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("getHouseNumber"));
        loadDataIntoTable();
    }

    /**
     * Loads the building data into the table
     */
    public void loadDataIntoTable() {
        buildingResult.clear();
        List<Building> buildings;
        try {
            String json = BuildingServerCommunication.getBuildings();
            List<Building> buildings1 = JsonMapper.buildingListMapper(json);
            buildings = new ArrayList<>(buildings1);
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            buildings = new ArrayList<>();
            buildings.add(null);
        }

        if (buildings.size() > 10) {
            buildings = buildings.subList(0, 15);
            buildingResult.addAll(buildings);
        } else {
            buildingResult.addAll(buildings);
        }
        table.setItems(buildingResult);
    }

    /**
     * Changes to MainMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }
}
