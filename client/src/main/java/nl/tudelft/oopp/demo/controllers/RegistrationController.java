package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class RegistrationController implements Initializable {

    private final ObservableList<String> facultyList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> facultyChoiceBox;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField surnameField;

    /**
     * Register button that communicates all the fields to ServerCommunication class.
     */
    public void registerButton() {
        try {
            String email = emailField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            String surname = surnameField.getText();
            String faculty = facultyChoiceBox.getValue().replaceAll(" ", "");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.addUser(email, name, surname, faculty, password));
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Changes to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }

    /**
     * Changes to login-screen.fxml
     * @param actionEvent actionEvent parameter
     * @throws IOException User input will be valid, no need to check this, thus we throw.
     */
    public void loginScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/login-screen.fxml");
    }

    private void loadData() {
        facultyList.clear();
        String a = "Architecture and the build Environment";
        String b = "Civil Engineering and Geosciences";
        String c = "Eletrical Engineering, Mathematics & Computer Science";
        String d = "Industrial Design Engineering";
        String e = "Aerospace Engineering";
        String f = "Technology, Policy and Management";
        String g = "Applied Sciences";
        String h = "Mechanical, Maritime and Materials Engineering";
        facultyList.addAll(a, b, c, d, e, f, g, h);
        facultyChoiceBox.getItems().addAll(facultyList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}