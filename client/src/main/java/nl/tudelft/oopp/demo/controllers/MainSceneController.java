package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
//Class not properly commented because it's to be deleted soon

public class    MainSceneController {

    /**
     * Handles clicking the user button.
     */
    public void userButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getUser());
        alert.showAndWait();
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getBuildings());
        alert.showAndWait();
    }

    /**
     * Handles clicking the remove button.
     */
    public void deleteBuildingButtonClicked() {
        int id = 4;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Removed a building ");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBuilding(id));
        alert.showAndWait();
    }

    /**
     *  method changes the view to that of the main menu
     * @param actionEvent clicking the button to go back to menu
     * @throws IOException the method will never throw an exception
     */

    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainMenuReservations.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }
    /**
     *  method changes the view to that of the main menu
     * @param actionEvent clicking the button to go back to menu
     * @throws IOException the method will never throw an exception
     */

    public void gobacktoMainMenu1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}
