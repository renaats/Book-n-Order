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
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class DatabaseEditRoomController implements Initializable {

    final ObservableList updateChoiceBoxList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> updateChoiceBox;
    @FXML
    private TextField roomFindByIdTextField;
    @FXML
    private TextField roomFindByIdUpdateField;
    @FXML
    private TextField roomChangeToField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataUpdateChoiceBox();
    }

    /**
     * Handles clicking the building find button.
     */
    public void room_id_ButtonClicked() {
        int id = Integer.parseInt(roomFindByIdTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building Finder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.findRoom(id));
        alert.showAndWait();
    }

    /**
     * Handles clicking the list button.
     */
    public void roomBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getRooms());
        alert.showAndWait();
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }


    /**
     * Handles the sending of update values.
     */
    public void updateRoomButtonClicked() {
        try {
            int id = Integer.parseInt(roomFindByIdUpdateField.getText());
            String attribute = updateChoiceBox.getValue().replaceAll(" ", "").toLowerCase();
            String changeValue = roomChangeToField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Building remover");
            alert.setHeaderText(null);
            alert.setContentText(ServerCommunication.updateRoom(id, attribute, changeValue));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing argument.");
            alert.showAndWait();
        }
    }

    /**
     * Takes care of the options for the updateChoiceBox in the GUI
     */
    public void loadDataUpdateChoiceBox() {
        updateChoiceBoxList.removeAll();
        String a = "Name";
        String b = "Faculty";
        String c = "Building ID";
        String d = "Faculty Specific";
        String e = "Screen";
        String f = "Projector";
        String g = "Amount of people";
        String h = "Plugs";
        updateChoiceBoxList.addAll(a, b, c, d, e, f, g, h);
        updateChoiceBox.getItems().addAll(updateChoiceBoxList);
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
     * When the menu item add is clicked it take you to the DatabaseAddRooms.fxml view
     * @param actionEvent the clicking of the menu item add.
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseAddRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * returns to the main menu
     * @param mouseEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
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
