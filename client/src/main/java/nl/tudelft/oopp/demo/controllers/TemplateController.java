package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;
import java.net.URL;


public class TemplateController {
    /**
    @FXML
    private AnchorPane content;

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        content.getChildren().setAll(FXMLLoader.load("vista2.fxml"));
    }
**/
    public void bookRoom(ActionEvent actionEvent) {
    }

    public void rentBike(ActionEvent actionEvent) {
    }

    public void orderFood(ActionEvent actionEvent) {
    }

    public void myAccountScene(ActionEvent actionEvent) {
    }

    public void myCurrentBookings(ActionEvent actionEvent) {
    }

    public void myPreviousBookings(ActionEvent actionEvent) {
    }

    public void mainMenu(MouseEvent mouseEvent) {
    }
}