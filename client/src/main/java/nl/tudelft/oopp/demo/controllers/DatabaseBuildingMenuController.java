package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseBuildingMenuController {
    /**
     * return to the database main menu when the home icon is clicked
     * @param mouseEvent the click on the home icon on the databased screens
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToAddBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * sends the user to the remove building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToRemoveBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveBuildings.fxml");
    }

    /**
     * sends the user to the edit building view
     * @param actionEvent the click on Go to add building button
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }
}
