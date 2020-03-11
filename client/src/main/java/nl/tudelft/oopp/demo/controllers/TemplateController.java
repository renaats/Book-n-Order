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

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void changecenterView(){

    }

    public void bookRoom(ActionEvent actionEvent) throws IOException {
        URL url = getClass().getResource("/login-screen.fxml");
        mainBorderPane.setCenter(FXMLLoader.load(url));
    }



    public void mainMenu(MouseEvent mouseEvent) {
    }
}