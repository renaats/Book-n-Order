package nl.tudelft.oopp.demo.controllers.database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseMainMenu.fxml" file
 */
public class DatabaseMainMenuController implements Initializable {
    @FXML
    private Button buildingButton;
    @FXML
    private Button roomButton;
    @FXML
    private Button bikeButton;
    @FXML
    private Button restaurantButton;
    @FXML
    private ImageView buildingImage;
    @FXML
    private ImageView roomImage;
    @FXML
    private ImageView bikeImage;
    @FXML
    private ImageView restaurantImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (RoomServerCommunication.updateRoom(0, "Dummy", "Value").equals(ErrorMessages.getErrorMessage(401))) {
            roomButton.setOnAction(null);
            roomImage.setOnMouseClicked(null);
            roomButton.setStyle("-fx-background-color: gray;");
        }
        if (BuildingServerCommunication.updateBuilding(0, "Dummy", "Value").equals(ErrorMessages.getErrorMessage(401))) {
            buildingButton.setOnAction(null);
            buildingImage.setOnMouseClicked(null);
            buildingButton.setStyle("-fx-background-color: gray;");
        }
        if (RestaurantServerCommunication.updateDish(0, "Dummy", "Value").equals(ErrorMessages.getErrorMessage(401))) {
            restaurantButton.setOnAction(null);
            restaurantImage.setOnMouseClicked(null);
            restaurantButton.setStyle("-fx-background-color: gray;");
        }
        if (BikeServerCommunication.updateBike(0, "Dummy", "Value").equals(ErrorMessages.getErrorMessage(401))) {
            bikeButton.setOnAction(null);
            bikeImage.setOnMouseClicked(null);
            bikeButton.setStyle("-fx-background-color: gray;");
        }
    }

    public void databaseBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    public void databaseRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    public void databaseRestaurants() throws  IOException {
        ApplicationDisplay.changeScene("/DatabaseRestaurantMenu.fxml");
    }

    public void databaseBikes() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBikeMenu.fxml");
    }
}
