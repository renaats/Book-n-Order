package nl.tudelft.oopp.demo.controllers;

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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class MainMenuController {

    /**
     * Handles clicking the food button.
     */
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
    public void roomsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
        Scene roomSelectScene = new Scene (roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }
}
