package nl.tudelft.oopp.demo.controllers;

import java.awt.image.BandCombineOp;
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
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseAddRooms.fxml" file
 */
public class DatabaseAddRoomController implements Initializable {

    private final ObservableList<String> studyList = FXCollections.observableArrayList();
    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> studySpecificChoiceBox;
    @FXML
    private ToggleButton screenToggle;
    @FXML
    private ToggleButton projectorToggle;
    @FXML
    private ToggleButton buildingsTableToggle;
    @FXML
    private Button previousPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField buildingIdTextField;
    @FXML
    private TextField capacityTextField;
    @FXML
    private TextField plugsTextField;
    @FXML
    private Text pagesText;
    @FXML
    private TableView<Building> table;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, Integer> colId;

    private boolean screenToggleFlag;
    private boolean projectorToggleFlag;
    private boolean buildingsTableToggleFlag;
    private int pageNumber;
    private double totalPages;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        if (pageNumber == 0) {
            pageNumber++;
        }

        anchorPane.getChildren().remove(table);
        anchorPane.getChildren().remove(previousPageButton);
        anchorPane.getChildren().remove(nextPageButton);
        anchorPane.getChildren().remove(pagesText);

        loadStudySpecificChoiceBox();
        retrieveAllBuildings();

        tableSelectMethod();
    }

    private void loadStudySpecificChoiceBox() {
        studyList.clear();
        String a = "Computer Science and Engineering";
        studyList.addAll(a);
        studySpecificChoiceBox.getItems().addAll(studyList);
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickProjector() {
        if (projectorToggleFlag) {
            projectorToggle.setText("false");
        } else {
            projectorToggle.setText("true");
        }
        projectorToggleFlag = !projectorToggleFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickScreen() {
        if (screenToggleFlag) {
            screenToggle.setText("false");

        } else {
            screenToggle.setText("true");
        }
        screenToggleFlag = !screenToggleFlag;
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickBuildingsTable() {
        if (buildingsTableToggleFlag) {
            buildingsTableToggle.setText("Show");
            anchorPane.getChildren().remove(table);
            anchorPane.getChildren().remove(previousPageButton);
            anchorPane.getChildren().remove(nextPageButton);
            anchorPane.getChildren().remove(pagesText);
        } else {
            buildingsTableToggle.setText(" Hide");
            anchorPane.getChildren().add(table);
            anchorPane.getChildren().add(previousPageButton);
            anchorPane.getChildren().add(nextPageButton);
            anchorPane.getChildren().add(pagesText);
        }
        buildingsTableToggleFlag = !buildingsTableToggleFlag;
    }

    /**
     * Adds a room to the database
     */
    public void databaseAddRoom() {
        String name = nameTextField.getText();
        int buildingId;
        try {
            buildingId = Integer.parseInt(buildingIdTextField.getText());
        } catch (NumberFormatException e) {
            Building building = JsonMapper.buildingMapper(ServerCommunication.findBuildingByName(buildingIdTextField.getText()));
            assert building != null;
            buildingId = building.getId();
        }
        String studySpecific = studySpecificChoiceBox.getValue();
        boolean screen = Boolean.parseBoolean(screenToggle.getText());
        boolean projector = Boolean.parseBoolean(projectorToggle.getText());
        int capacity = Integer.parseInt(capacityTextField.getText());
        int plugs = Integer.parseInt(plugsTextField.getText());
        String status = nameTextField.getText();
        String response = ServerCommunication.addRoom(name, buildingId, studySpecific, screen, projector, capacity, plugs, status);
        if (response.equals("Successfully added!")) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * returns to the main menu
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void goToRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Handles clicking the list button.
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

        totalPages = Math.ceil(buildings.size() / 7.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (buildings.size() > 7) {
            for (int i = 1; i < 8; i++) {
                try {
                    buildingResult.add(buildings.get((i - 7) + pageNumber * 7));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }  else {
            buildingResult.addAll(buildings);
        }
        table.setItems(buildingResult);
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            retrieveAllBuildings();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllBuildings();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            final Building building = table.getSelectionModel().getSelectedItem();
            if (building != null) {
                buildingIdTextField.setText(building.getName());
            }
        });
    }
}
