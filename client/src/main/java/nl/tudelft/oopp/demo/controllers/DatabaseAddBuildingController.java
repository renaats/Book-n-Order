package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseAddBuildings.fxml" file
 */
public class DatabaseAddBuildingController implements Initializable {

    private final ObservableList<String> facultyList = FXCollections.observableArrayList();

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;
    @FXML
    private ChoiceBox<String> facultyChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
     * sends the user to the edit building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }

    /**
     * Adds building to the database
     */
    public void databaseAddBuilding() {
        String name = null;
        String street = null;
        // -1 is a placeholder since you cannot initialize an empty integer.
        int houseNumber = -1;
        String faculty = null;
        try {
            name = nameTextField.getText();
            street = streetTextField.getText();
            if (facultyChoiceBox.getValue() == null) {
                CustomAlert.warningAlert("Please choose a faculty, or select the blank space.");
                return;
            } else {
                faculty = facultyChoiceBox.getValue();
            }
            if (name.equals("")) {
                CustomAlert.warningAlert("Missing name.");
                return;
            }
            if (street.equals("")) {
                CustomAlert.warningAlert("Missing street.");
                return;
            }
            try {
                houseNumber = Integer.parseInt(houseNumberTextField.getText());
            } catch (Exception e) {
                CustomAlert.errorAlert("Not an integer.");
                return;
            }
        } catch (Exception e) {
            CustomAlert.errorAlert("Could not parse attributes.");
        }
        String response = BuildingServerCommunication.addBuilding(name, street, houseNumber, faculty);
        if (response.equals("Successfully added!")) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * Goes back to the database building menu when the icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
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
