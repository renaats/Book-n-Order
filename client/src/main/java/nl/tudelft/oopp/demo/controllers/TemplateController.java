package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "Template.fxml" file
 */
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
}
