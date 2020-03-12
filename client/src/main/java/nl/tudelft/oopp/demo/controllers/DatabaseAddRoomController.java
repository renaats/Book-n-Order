package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Takes care of the functionality  DataBaseAddRoom.fxml file
 */
public class DatabaseAddRoomController {

    @FXML
    private ToggleButton facultySpecificToggle;
    @FXML
    private ToggleButton screenToggle;
    @FXML
    private ToggleButton projectorToggle;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField facultyTextField;
    @FXML
    private TextField buildingIdTextField;
    @FXML
    private TextField capacityTextField;
    @FXML
    private TextField plugsTextField;

    private boolean facultySpecificToggleFlag;
    private boolean screenToggleFlag;
    private boolean projectorToggleFlag;

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e Just an ActionEvent param
     */
    @FXML
    private void toggleClickFacultySpecific(ActionEvent e) {
        if (facultySpecificToggleFlag) {
            facultySpecificToggle.setText("False");
            facultySpecificToggleFlag = false;
        } else {
            facultySpecificToggle.setText("True");
            facultySpecificToggleFlag = true;
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e Just an ActionEvent param
     */
    @FXML
    private void toggleClickProjector(ActionEvent e) {
        if (projectorToggleFlag) {
            projectorToggle.setText("False");
            projectorToggleFlag = false;
        } else {
            projectorToggle.setText("True");
            projectorToggleFlag = true;
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     * @param e Just an ActionEvent param
     */
    @FXML
    private void toggleClickScreen(ActionEvent e) {
        if (screenToggleFlag) {
            screenToggle.setText("False");
            screenToggleFlag = false;
        } else {
            screenToggle.setText("True");
            screenToggleFlag = true;
        }
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

    /**
     * Changes to DatabaseEditBuildings.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * Changes to DatabaseEditRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }

    public void myAccountScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Adds a room to the database
     * @param actionEvent action event parameter
     */
    public void databaseAddRoom(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String faculty = facultyTextField.getText();
        int buildingId = Integer.parseInt(buildingIdTextField.getText());
        boolean facultySpecific = Boolean.parseBoolean(facultySpecificToggle.getText());
        boolean screen = Boolean.parseBoolean(screenToggle.getText());
        boolean projector = Boolean.parseBoolean(projectorToggle.getText());
        int capacity = Integer.parseInt(capacityTextField.getText());
        int plugs = Integer.parseInt(plugsTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room adder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.addRoom(name, faculty, buildingId, facultySpecific, screen, projector, capacity, plugs));
        alert.showAndWait();
    }

    /**
     * returns to the main menu
     * @param actionEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }

    /**
     * When the room icon is clicked it take you to the RoomEditOrAdd.fxml view
     * @param mouseEvent the clicking of the room icon.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void goToRoomMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/RoomsEditOrAdd.fxml");
    }
    /**
     * When the menu item add is clicked it take you to the DatabaseAddRooms.fxml view
     * @param actionEvent the clicking of the menu item add.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseAddRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * When the menu item edit is clicked it take you to the DatabaseAddRooms.fxml view
     * @param actionEvent the clicking of the menu item edit.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseEditRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }
    /**
     * When the menu item remove is clicked it take you to the DatabaseAddRooms.fxml view
     * @param actionEvent the clicking of the menu item remove.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseRemoveRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveRoom.fxml");
    }
}
