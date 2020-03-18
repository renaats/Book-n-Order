package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;



public class RoomEditOrAddController implements Initializable {

    @FXML
    private TextArea roomsDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * Loads the current rooms to the text area.
     */
    public void loadData() {
        //TODO
        //roomsDisplay.setText(ServerCommunication.getRooms());
    }
    /**
     * Changes the scene to the main menu of the database.
     * @param mouseEvent the event is the clicking of the mouse icon
     * @throws IOException the input is allways correct, so it should never throw and IOException.
     */

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * takes the user to the view where they can add rooms to the database
     * @param actionEvent the event is the clicking of the mouse icon
     * @throws IOException the input is allways correct, so it should never throw and IOException.
     */
    public void goToAddRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * takes the user to the view where they can remove the rooms in the data base.
     * @param actionEvent the event is the clicking of the mouse icon
     * @throws IOException the input is allways correct, so it should never throw and IOException.
     */

    public void goToRemoveRooms(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveRoom.fxml");
    }

    /**
     * changes scene to the edit room scene
     * @throws IOException should never throw an exception
     */
    public void goToEditRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditRoom.fxml");
    }
}
