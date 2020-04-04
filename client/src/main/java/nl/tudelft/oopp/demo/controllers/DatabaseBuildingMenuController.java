package nl.tudelft.oopp.demo.controllers;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseBuildingMenu.fxml" file
 */
public class DatabaseBuildingMenuController implements Initializable {

    private final ObservableList<Building> buildingResult = FXCollections.observableArrayList();
    private final ObservableList<String> facultyList = FXCollections.observableArrayList();

    @FXML
    private TextField idFieldRead;
    @FXML
    private TextField nameFieldRead;
    @FXML
    private TextField streetFieldRead;
    @FXML
    private TextField houseNumberFieldRead;
    @FXML
    private TextField buildingFindTextField;
    @FXML
    private Text pagesText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Building> table;
    @FXML
    private TableColumn<Building, String> colName;
    @FXML
    private TableColumn<Building, String> colStreet;
    @FXML
    private TableColumn<Building, Integer> colHouseNumber;
    @FXML
    private TableColumn<Building, Integer> colFaculty;
    @FXML
    private ChoiceBox<String> facultyChoiceBox;

    private int pageNumber;
    private double totalPages;
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colHouseNumber.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        idFieldRead.setDisable(true);
        if (pageNumber == 0) {
            pageNumber++;
        }

        listBuildingsButtonClicked();
        tableHoverMethod();
        loadFacultyChoiceBox();
    }

    /**
     * return to the database main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        buildingResult.clear();
        List<Building> buildings;
        try {
            buildings = new ArrayList<>(Objects.requireNonNull(JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            buildings = new ArrayList<>();
            buildings.add(null);
        }

        totalPages = Math.ceil(buildings.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (buildings.size() > 16) {
            for (int i = 1; i < 16; i++) {
                try {
                    buildingResult.add(buildings.get((i - 15) + pageNumber * 15));
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
            listBuildingsButtonClicked();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        listBuildingsButtonClicked();
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableHoverMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Building building = table.getSelectionModel().getSelectedItem();
            if (building != null) {
                idFieldRead.setText(Integer.toString(building.getId()));
                nameFieldRead.setText(building.getName());
                streetFieldRead.setText(building.getStreet());
                houseNumberFieldRead.setText(Integer.toString(building.getHouseNumber()));
                facultyChoiceBox.setValue(building.getFaculty());
            }

            for (int i = 0; i < buildingResult.size(); i++) {
                if (buildingResult.get(i).getId().equals(building.getId())) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(1200);
                    deleteButton.setLayoutY(179 + (24  * (i + 1)));
                    deleteButton.setMinWidth(60);
                    deleteButton.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButton.setMinHeight(20);
                    deleteButton.setOnAction(event -> {
                        for (int i1 = 0; i1 < buildingResult.size(); i1++) {
                            if (buildingResult.get(i1).getId().equals(building.getId())) {
                                buildingResult.remove(buildingResult.get(i1));
                                anchorPane.getChildren().remove(deleteButton);
                            }
                        }
                        String response = BuildingServerCommunication.deleteBuilding(building.getId());
                        listBuildingsButtonClicked();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }

    /**
     * Takes care of finding a building by name or ID.
     */
    public void findBuilding() {
        try {
            int id = Integer.parseInt(buildingFindTextField.getText());
            Building building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(id));
            if (building != null) {
                buildingResult.clear();
                buildingResult.add(building);
                table.setItems(buildingResult);
                pagesText.setText("1 / 1 pages");
            }
        } catch (Exception e) {
            String name = buildingFindTextField.getText();
            System.out.println(name);
            Building building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuildingByName(name));
            if (building != null) {
                buildingResult.clear();
                buildingResult.add(building);
                table.setItems(buildingResult);
                pagesText.setText("1 / 1 pages");
            }
        }
    }

    /**
     * Updates a building directly from the fields.
     */
    public void updateBuilding() {
        int id;
        boolean passes = true;
        try {
            id = Integer.parseInt(idFieldRead.getText());
            Building building = JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(id));
            assert building != null;
            if (!building.getName().equals(nameFieldRead.getText())) {
                String response = BuildingServerCommunication.updateBuilding(id, "name", nameFieldRead.getText());
                if (response.equals("Name already exists.")) {
                    passes = false;
                    CustomAlert.warningAlert("Name already exists.");
                }
            }
            if (!building.getStreet().equals(streetFieldRead.getText())) {
                BuildingServerCommunication.updateBuilding(id, "street", streetFieldRead.getText());
            }
            try {
                if (!(building.getHouseNumber() == Integer.parseInt(houseNumberFieldRead.getText()))) {
                    BuildingServerCommunication.updateBuilding(id, "houseNumber", houseNumberFieldRead.getText());
                }
            } catch (NumberFormatException e) {
                CustomAlert.warningAlert("Cannot parse house number.");
                passes = false;
            }
            if ((building.getFaculty() == null) || !building.getFaculty().equals(facultyChoiceBox.getValue())) {
                BuildingServerCommunication.updateBuilding(id, "faculty", facultyChoiceBox.getValue());
            }
        } catch (NumberFormatException e) {
            CustomAlert.warningAlert("No selection detected.");
            passes = false;
        }
        if (passes) {
            CustomAlert.informationAlert("Successfully Executed.");
        }
        listBuildingsButtonClicked();
    }

    /**
     * Handles clicking of the Remove Building button.
     */
    public void deleteBuilding() {
        try {
            int id = Integer.parseInt(idFieldRead.getText());
            BuildingServerCommunication.deleteBuilding(id);
            buildingResult.removeIf(b -> b.getId() == id);
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    private void loadFacultyChoiceBox() {
        facultyList.clear();
        String a = "Architecture and the Built Environment";
        String b = "Civil Engineering and Geosciences";
        String c = "Electrical Engineering, Mathematics & Computer Science";
        String d = "Industrial Design Engineering";
        String e = "Aerospace Engineering";
        String f = "Technology, Policy and Management";
        String g = "Applied Sciences";
        String h = "Mechanical, Maritime and Materials Engineering";
        String i = "";
        facultyList.addAll(a, b, c, d, e, f, g, h, i);
        facultyChoiceBox.getItems().addAll(facultyList);
    }
}
