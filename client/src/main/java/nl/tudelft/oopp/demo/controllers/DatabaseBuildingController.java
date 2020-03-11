package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Adds the functionality to DatabaseMenu.fxml file
 */
public class DatabaseBuildingController implements Initializable {

    final ObservableList<String> updateChoiceBoxList = FXCollections.observableArrayList();
    final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField buildingFindByIdTextField;
    @FXML
    private TextField buildingDeleteByIdTextField;
    @FXML
    private TextField buildingFindByIdUpdateField;
    @FXML
    private TextField buildingChangeToField;
    @FXML
    private TableView<Building> table;
    @FXML
    private TableColumn<Building, String> colId;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colStreet;
    @FXML
    private TableColumn<Building, Integer> colHouseNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));

    }

    /**
     * Handles clicking of the Find Building button.
     */
    public void buildingIdButtonClicked() {
        try {
            int id = Integer.parseInt(buildingFindByIdTextField.getText());
            Building building = JsonMapper.buildingMapper(ServerCommunication.findBuilding(id));
            buildingResult.clear();
            buildingResult.add(building);
            table.setItems(buildingResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking of the List All Buildings button.
     */
    public void listBuildingsButtonClicked() {
        try {
            List<Building> buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(ServerCommunication.getBuildings())));
            buildingResult.clear();
            buildingResult.addAll(buildings);
            table.setItems(buildingResult);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No buildings found.");
            alert.showAndWait();
        }
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     * Changes current scene to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Handles clicking of the Remove Building button.
     */
    public void deleteBuildingButtonClicked() {
        try {
            int id = Integer.parseInt(buildingDeleteByIdTextField.getText());
            ServerCommunication.deleteBuilding(id);
            buildingResult.removeIf(b -> b.getId() == id);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the sending of updated values.
     */
    public void updateBuildingButtonClicked() {
        try {
            int id = Integer.parseInt(buildingFindByIdUpdateField.getText());
            String attribute = updateChoiceBox.getValue().replaceAll(" ", "");
            String changeValue = buildingChangeToField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building update");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateBuilding(id, attribute, changeValue));
            alert.showAndWait();
            buildingResult.clear();
            listBuildingsButtonClicked();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.clear();
        String a = "Name";
        String b = "Street";
        String c = "House Number";
        updateChoiceBoxList.addAll(a, b, c);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

    /**
     * Switches scene to DatabaseAddBuildings.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    public void myAccountScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }
    /**
     * returns to the main menu
     * @param actionEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}
