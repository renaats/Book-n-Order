package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class TemplateController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void changeCenterView(){

    }

    public void bookRoom(ActionEvent actionEvent) throws IOException {
        URL url = getClass().getResource("/login-screen.fxml");
        mainBorderPane.setCenter(FXMLLoader.load(url));
    }
}