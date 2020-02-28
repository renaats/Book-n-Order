package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.views.TemplateDisplay;


public class TemplateController {

    /**
     * Template for all pages
     */
    public void template() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("not implemented");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void roomsSelected() throws IOException {
        TemplateDisplay.main(new String[0]);
    }
}