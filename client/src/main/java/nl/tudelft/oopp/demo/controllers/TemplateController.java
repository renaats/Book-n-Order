package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.views.QuoteDisplay;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.TemplateDisplay;
import nl.tudelft.oopp.demo.views.QuoteDisplay;

import java.io.IOException;
import java.net.URL;


public class TemplateController {

    /**
     * Template for all pages
     */
    public void template(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("not implememnted");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void roomsSelected() throws IOException {
        QuoteDisplay.main(new String[0]);
    }
}