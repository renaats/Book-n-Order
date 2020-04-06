package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseViewBikeReservations.fxml" file
 */
public class DatabaseBikeReservationController implements Initializable {

    private final ObservableList<BikeReservation> currentResult = FXCollections.observableArrayList();
    private final ObservableList<BikeReservation> pastResult = FXCollections.observableArrayList();
    private final ObservableList<Bike> bikeResult = FXCollections.observableArrayList();
    private List<BikeReservation> currentOrders;
    private List<BikeReservation> pastOrders;
    private List<Bike> bikes;
    private Bike selectedBike;

    @FXML
    private TableView<BikeReservation> currentTable;
    @FXML
    private TableView<BikeReservation> pastTable;
    @FXML
    private TableView<Bike> bikeTable;
    @FXML
    private TableColumn<BikeReservation, String> colCurrentFrom;
    @FXML
    private TableColumn<BikeReservation, String> colCurrentTo;
    @FXML
    private TableColumn<BikeReservation, String> colCurrentFromBuilding;
    @FXML
    private TableColumn<BikeReservation, String> colCurrentToBuilding;
    @FXML
    private TableColumn<BikeReservation, String> colPastFrom;
    @FXML
    private TableColumn<BikeReservation, String> colPastTo;
    @FXML
    private TableColumn<BikeReservation, String> colPastFromBuilding;
    @FXML
    private TableColumn<BikeReservation, String> colPastToBuilding;
    @FXML
    private TableColumn<Bike, String> colBikeId;
    @FXML
    private TableColumn<Bike, String> colBikeBuilding;
    @FXML
    private Text currentPagesText;
    @FXML
    private Text pastPagesText;
    @FXML
    private Text bikePagesText;
    @FXML
    private AnchorPane anchorPane;

    private int currentPageNumber;
    private double totalCurrentPages;
    private int pastPageNumber;
    private double totalPastPages;
    private int bikePageNumber;
    private double totalBikePages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCurrentFrom.setCellValueFactory(new PropertyValueFactory<>("fromTimeString"));
        colCurrentTo.setCellValueFactory(new PropertyValueFactory<>("toTimeString"));
        colCurrentFromBuilding.setCellValueFactory(new PropertyValueFactory<>("fromBuildingName"));
        colCurrentToBuilding.setCellValueFactory(new PropertyValueFactory<>("toBuildingName"));
        colPastFrom.setCellValueFactory(new PropertyValueFactory<>("fromTimeString"));
        colPastTo.setCellValueFactory(new PropertyValueFactory<>("toTimeString"));
        colPastFromBuilding.setCellValueFactory(new PropertyValueFactory<>("fromBuildingName"));
        colPastToBuilding.setCellValueFactory(new PropertyValueFactory<>("toBuildingName"));
        colBikeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBikeBuilding.setCellValueFactory(new PropertyValueFactory<>("locationName"));

        currentTable.setPlaceholder(new Label(""));
        pastTable.setPlaceholder(new Label(""));
        bikeTable.setPlaceholder(new Label(""));

        if (currentPageNumber == 0) {
            currentPageNumber++;
        }
        if (pastPageNumber == 0) {
            pastPageNumber++;
        }
        if (bikePageNumber == 0) {
            bikePageNumber++;
        }
        addListeners();
    }

    /**
     * Returns to the main database menu
     * @throws IOException Should never throw the exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Returns to the restaurant database menu
     * @throws IOException Should never throw the exception
     */
    public void goToRestaurantMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBikeMenu.fxml");
    }

    /**
     * Loads all orders from the database.
     */
    public void loadAllOrders() {
        try {
            currentOrders = JsonMapper.bikeReservationsListMapper(BikeServerCommunication.getAllFutureBikeReservationsForBike(selectedBike.getId()));
        } catch (Exception e) {
            currentOrders = new ArrayList<>();
        }
        currentOrders.sort(Comparator.comparing(BikeReservation::getFromTimeString));
        try {
            pastOrders = JsonMapper.bikeReservationsListMapper(BikeServerCommunication.getAllPreviousBikeReservationsForBike(selectedBike.getId()));
        } catch (Exception e) {
            pastOrders = new ArrayList<>();
        }
        pastOrders.sort(Comparator.comparing(BikeReservation::getFromTimeString).reversed());
        calculateCurrentPages();
        calculatePastPages();
    }

    /**
     * Calculates how many current pages there should be for browsing the table
     */
    public void calculateCurrentPages() {
        currentResult.clear();
        totalCurrentPages = Math.ceil(currentOrders.size() / 10.0);
        if (totalCurrentPages < currentPageNumber) {
            currentPageNumber--;
        }
        if (totalCurrentPages > 0) {
            currentPageNumber = Math.max(currentPageNumber, 1);
        }
        currentPagesText.setText(currentPageNumber + " / " + (int) totalCurrentPages + " pages");
        if (currentOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    currentResult.add(currentOrders.get((i - 10) + currentPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            currentResult.addAll(currentOrders);
        }
        currentTable.setItems(currentResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextCurrentPage() {
        if (currentPageNumber < (int) totalCurrentPages) {
            currentPageNumber++;
            calculateCurrentPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousCurrentPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
        }
        calculateCurrentPages();
    }

    /**
     * Calculates how many past pages there should be for browsing the table
     */
    public void calculatePastPages() {
        pastResult.clear();
        totalPastPages = Math.ceil(pastOrders.size() / 10.0);
        if (totalPastPages < pastPageNumber) {
            pastPageNumber--;
        }
        if (totalPastPages > 0) {
            pastPageNumber = Math.max(pastPageNumber, 1);
        }
        pastPagesText.setText(pastPageNumber + " / " + (int) totalPastPages + " pages");
        if (pastOrders.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    pastResult.add(pastOrders.get((i - 10) + pastPageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            pastResult.addAll(pastOrders);
        }
        pastTable.setItems(pastResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPastPage() {
        if (pastPageNumber < (int) totalPastPages) {
            pastPageNumber++;
            calculatePastPages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPastPage() {
        if (pastPageNumber > 1) {
            pastPageNumber--;
        }
        calculatePastPages();
    }

    /**
     * Adds listeners to the order tables.
     */
    public void addListeners() {
        try {
            bikes = JsonMapper.bikeListMapper(BikeServerCommunication.getBikes());
        } catch (Exception e) {
            bikes = new ArrayList<>();
        }
        calculateBikePages();
        bikeTable.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            if (bikeTable.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            selectedBike = bikeTable.getSelectionModel().getSelectedItem();
            loadAllOrders();
        });
    }

    /**
     * Calculates how many bike pages there should be for browsing the table
     */
    public void calculateBikePages() {
        totalBikePages = Math.ceil(bikes.size() / 10.0);
        if (totalBikePages < bikePageNumber) {
            bikePageNumber--;
        }
        if (totalBikePages > 0) {
            bikePageNumber = Math.max(bikePageNumber, 1);
        }
        bikePagesText.setText(bikePageNumber + " / " + (int) totalBikePages + " pages");
        if (bikes.size() > 10) {
            for (int i = 0; i < 10; i++) {
                try {
                    bikeResult.add(bikes.get((i - 10) + bikePageNumber * 10));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            bikeResult.addAll(bikes);
        }
        bikeTable.setItems(bikeResult);
    }

    /**
     * Handles the clicking to the next page
     */
    public void nextBikePage() {
        if (bikePageNumber < (int) totalBikePages) {
            bikePageNumber++;
            calculateBikePages();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousBikePage() {
        if (bikePageNumber > 1) {
            bikePageNumber--;
        }
        calculateBikePages();
    }
}
