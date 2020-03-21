package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class BikeDatabaseEditController implements Initializable {

    @FXML
    private TableView<Bike> table;
    @FXML
    private TableColumn<Bike, String> colId;
    @FXML
    private TableColumn<Bike, String> colLocation;
    @FXML
    private TableColumn colAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
        loadDataUpdateChoiceBox();
        listBuildingsButtonClicked();
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.removeAll();
        String a = "Name";
        String b = "Street";
        String c = "House Number";
        updateChoiceBoxList.addAll(a, b, c);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        try {
            //List<Bike> bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(ServerCommunication.getBuildings())));
            buildingResult.clear();
            buildingResult.addAll(bikes);
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
     * Changes view to main menu
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Changes view to main Bike database menu
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToBikeMenu() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseMenu.fxml");
    }

    /**
     * Changes view to BikeDatabaseAdd
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToAddBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseAdd.fxml");
    }

    /**
     * Changes view to main BikeDatabaseEdit
     * @throws IOException should never throw an exception as the input is always the same
     */
    public void goToEditBike() throws IOException {
        ApplicationDisplay.changeScene("/BikeDatabaseEdit.fxml");
    }









    @FXML
    private TextField buildingDeleteByIdTextField;

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();
    final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField buildingFindByIdTextField;
    @FXML
    private TextField buildingChangeToField;


    /**
     * Handles clicking the building find button.
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
     * Handles clicking of the Remove Building button through the table.
     */
    public void deleteBuildingButtonClickedByTable() {
        try {
            Building building = table.getSelectionModel().getSelectedItem();
            ServerCommunication.deleteBuilding(building.getId());
            buildingResult.removeIf(b -> b.getId().equals(building.getId()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Handles clicking of the edit Building button through the table.
     */
    public void editBuildingButtonClickedByTable() {
        try {
            Building building = table.getSelectionModel().getSelectedItem();
            buildingFindByIdTextField.setText(Integer.toString(building.getId()));
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
            int id = Integer.parseInt(buildingFindByIdTextField.getText());
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

}
