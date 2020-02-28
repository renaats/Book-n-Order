package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.MainMenuDisplay;
import nl.tudelft.oopp.demo.views.QuoteDisplay;


public class MainMenuController {

    /**
     * Handles clicking the food button.
     */
    @SuppressWarnings({"checkstyle:MethodParamPad", "CheckStyle"})
    public void foodsSelsected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
        Scene roomSelectScene = new Scene (roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the bikes button.
     */
    @SuppressWarnings({"checkstyle:MethodParamPad", "CheckStyle"})
    public void bikesSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
        Scene roomSelectScene = new Scene (roomSelectParent);

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
        Scene roomSelectScene = new Scene (roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }
}
