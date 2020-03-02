package nl.tudelft.oopp.demo.controllers;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.MainMenuDisplay;
import nl.tudelft.oopp.demo.views.QuoteDisplay;


public class MainMenuController {

    /**
     * Handles clicking the food button.
     */
    public void foodsSelected(ActionEvent event) throws IOException {

        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the bikes button.
     */
    public void bikesSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bikeReservations.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the rooms button. Should send you to the mainScene.
     */

    @SuppressWarnings({"checkstyle:MethodParamPad", "checkstyle:EmptyLineSeparator", "CheckStyle"})
    public void roomsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bookRoom.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    public void bikesSelected1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    public void roomsSelected1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    public void foodsSelected1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    public void gobacktoMainMenu1(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
