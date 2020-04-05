package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "registrationScene.fxml" file
 */
public class RegistrationController implements Initializable {

    private final ObservableList<String> facultyList = FXCollections.observableArrayList();
    private final ObservableList<String> studyList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> facultyChoiceBox;
    @FXML
    private ChoiceBox<String> studyChoiceBox;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private TextField surnameField;

    /**
     * Register button that communicates all the fields to UserRelated class.
     */
    public void registerButton() {
        try {
            String password2 = passwordConfirm.getText();
            String email = emailField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            String surname = surnameField.getText();
            String faculty = facultyChoiceBox.getValue();
            String study = studyChoiceBox.getValue();
            if (password.equals(password2)) {
                CustomAlert.informationAlert(UserServerCommunication.addUser(email, name, surname, faculty, password, study));
            } else {
                CustomAlert.errorAlert("Passwords do not match.");
            }
        } catch (Exception e) {
            CustomAlert.warningAlert("Missing fields.");
        }
    }

    /**
     * Changes to MainMenuReservations.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * Changes to LoginScreen.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void loginScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/LoginScreen.fxml");
    }

    private void loadData() {
        facultyList.clear();
        String a = "Architecture and the Built Environment";
        String b = "Civil Engineering and Geosciences";
        String c = "Electrical Engineering, Mathematics & Computer Science";
        String d = "Industrial Design Engineering";
        String e = "Aerospace Engineering";
        String f = "Technology, Policy and Management";
        String g = "Applied Sciences";
        String h = "Mechanical, Maritime and Materials Engineering";
        facultyList.addAll(a, b, c, d, e, f, g, h);
        facultyChoiceBox.getItems().addAll(facultyList);

        studyList.clear();
        studyList.add("Aerospace Engineering");
        studyList.add("Applied Earth Sciences");
        studyList.add("Architecture");
        studyList.add("Civil Engineering");
        studyList.add("Computer Science and Engineering");
        studyList.add("Electrical Engineering");
        studyList.add("Industrial Design");
        studyList.add("Clinical Technology");
        studyList.add("Life Science and Technology");
        studyList.add("Maritime Technology");
        studyList.add("Molecular Science and Technology");
        studyList.add("Nanobiology");
        studyList.add("Technical Public Administration");
        studyList.add("Applied Physics");
        studyList.add("Technical Mathematics");
        studyList.add("Mechanical Engineering");
        studyChoiceBox.getItems().addAll(studyList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}