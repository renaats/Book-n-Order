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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseBikeMenu.fxml" file
 */
public class DatabaseBikeMenuController implements Initializable {

    private final ObservableList<Bike> bikeResult = FXCollections.observableArrayList();

    @FXML
    private TableView<Bike> table;
    @FXML
    private Text pagesText;
    @FXML
    private ToggleButton availableToggle;
    @FXML
    private TableColumn<Bike, Integer> colId;
    @FXML
    private TableColumn<Bike, String> colBuilding;
    @FXML
    private TableColumn<Bike, Boolean> colAvailable;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField idFieldRead;
    @FXML
    private TextField buildingFieldRead;
    @FXML
    private TextField bikeFindTextField;

    private int pageNumber;
    private double totalPages;
    private Button deleteButton;
    private boolean availableToggleFlag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        if (pageNumber == 0) {
            pageNumber++;
        }

        idFieldRead.setDisable(true);
        buildingFieldRead.setDisable(true);
        retrieveAllBikes();
        tableSelectMethod();
    }

    /**
     * Handles clicking of the Find all button.
     */
    public void retrieveAllBikes() {
        bikeResult.clear();
        List<Bike> bikes = new ArrayList<>();
        try {
            bikes = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeListMapper(BikeServerCommunication.getBikes())));
        } catch (Exception e) {
            table.setPlaceholder(new Label(""));
        }

        totalPages = Math.ceil(bikes.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (bikes.size() > 16) {
            for (int i = 1; i < 16; i++) {
                try {
                    bikeResult.add(bikes.get((i - 15) + pageNumber * 15));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            bikeResult.addAll(bikes);
        }
        table.setItems(bikeResult);
    }

    /**
     * Switches scene to DatabaseAddBikes.fxml
     * @throws IOException Input will be valid
     */
    public void goToAddBikes() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBikes.fxml");
    }

    /**
     * Returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Goes to the reservation screen.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void goToReservations() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBikeReservations.fxml");
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            retrieveAllBikes();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllBikes();
    }

    /**
     * Takes care of finding a bike by ID.
     */
    public void findBike() {
        try {
            int id = Integer.parseInt(bikeFindTextField.getText());
            Bike bike = JsonMapper.bikeMapper(BikeServerCommunication.findBike(id));
            if (bike != null) {
                bikeResult.clear();
                bikeResult.add(bike);
                table.setItems(bikeResult);
                pagesText.setText("1 / 1 pages");
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("Bike ID has to be an integer.");
        }
    }

    /**
     * Updates a bike directly from the fields.
     */
    public void updateBike() {
        int id;
        try {
            id = Integer.parseInt(idFieldRead.getText());
            Bike bike = JsonMapper.bikeMapper(BikeServerCommunication.findBike(id));
            assert bike != null;
            if (!(bike.isAvailable() == Boolean.parseBoolean(availableToggle.getText()))) {
                BikeServerCommunication.updateBike(id, "available", availableToggle.getText().toLowerCase());
            }
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
            return;
        }
        CustomAlert.informationAlert("Successfully Executed.");
        retrieveAllBikes();
    }

    /**
     * Handles clicking of the Remove Bike button.
     */
    public void deleteBike() {
        try {
            int id = Integer.parseInt(idFieldRead.getText());
            CustomAlert.informationAlert(BikeServerCommunication.deleteBike(id));
            bikeResult.removeIf(r -> r.getId() == id);
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickAvailable() {
        if (availableToggleFlag) {
            availableToggle.setText("false");
            availableToggleFlag = false;
        } else {
            availableToggle.setText("true");
            availableToggleFlag = true;
        }
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Bike bike = table.getSelectionModel().getSelectedItem();
            if (bike != null) {
                idFieldRead.setText(Integer.toString(bike.getId()));
                buildingFieldRead.setText(bike.getLocation().getName());
                availableToggle.setText(Boolean.toString(bike.isAvailable()));
                if (!availableToggleFlag == bike.isAvailable()) {
                    availableToggleFlag = !availableToggleFlag;
                    availableToggle.setSelected(availableToggleFlag);
                }
            }

            for (int i = 0; i < bikeResult.size(); i++) {
                assert bike != null;
                if (bikeResult.get(i).getId() == bike.getId()) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(1120);
                    deleteButton.setLayoutY(168 + (24 * (i + 1)));
                    deleteButton.setMinWidth(60);
                    deleteButton.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButton.setMinHeight(20);
                    deleteButton.setOnAction(event -> {
                        for (int i1 = 0; i1 < bikeResult.size(); i1++) {
                            if (bikeResult.get(i1).getId() == bike.getId()) {
                                bikeResult.remove(bikeResult.get(i1));
                                anchorPane.getChildren().remove(deleteButton);
                            }
                        }
                        String response = BikeServerCommunication.deleteBike(bike.getId());
                        retrieveAllBikes();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }
}
