package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;


public class TemplateController {

    /**
     * Acts as placeholder for all other pages.
     */
    public void template() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("not implemented");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}