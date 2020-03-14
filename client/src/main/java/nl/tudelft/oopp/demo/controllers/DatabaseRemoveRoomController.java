package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseRemoveRoomController implements Initializable {

    public Button confirmDeleteByIdButton;
    public ImageView goToRoomMenuButton;
    @FXML
    private TextField roomDeleteByIdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * Needs to add the list of the rooms need proper input parsing
     */
    private void loadData() {
        //TODO
    }

    /**
     * Handles clicking the remove button.
     */
    public void deleteRoomButtonClicked() {
        int id = Integer.parseInt(roomDeleteByIdTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room remover");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteRoom(id));
        alert.showAndWait();
    }

    /**
     * Will send the user to the main database menu when clicked.
     * @param mouseEvent the clicking of the home icon.
     * @throws IOException the input will always be correct, so it should never throw and exception.
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
     * When the menu item add is clicked it take you to the DatabaseAddRooms.fxml view
     * @throws IOException the input will always be correct, so it should never throw and exception.
     */
    public void databaseAddRooms() throws IOException {
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
