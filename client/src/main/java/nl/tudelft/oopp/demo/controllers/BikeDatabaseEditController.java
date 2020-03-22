package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * controls all user inputs in the BikeDatabaseEdit.fxml scene
 */
public class BikeDatabaseEditController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();
    @FXML
    private TextField bikeFindByIdTextField;
    @FXML
    private TableView<Bike> table;
    @FXML
    private TableColumn<Bike, String> colId;
    @FXML
    private TableColumn<Bike, String> colLocation;
    @FXML
    private TableColumn colAvailable;
    @FXML
    private TextField bikeDeleteByIdTextField;

    @FXML
    private ChoiceBox<String> updateChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
        loadDataUpdateChoiceBox();
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.removeAll();
        String a = "Location";
        String b = "Available";
        updateChoiceBoxList.addAll(a, b);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
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

}
