package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class OrderFoodController {

    /**
     * Handles clicking the food button.
     */
    public void foodsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Handles clicking the bikes button.
     */
    public void bikesSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bikeReservations.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void roomsSelected(ActionEvent event) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/bookRoom.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to bikeReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void rentBike(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void bookRoom(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    /**
     * Changes to orderFood.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void orderFood(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFood.fxml");
    }

    /**
     * Changes to mainMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to orderFoodRestaurantChoice.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void restaurantChoice(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodRestaurantChoice.fxml");
    }

    /**
     * Changes to orderFoodAulaMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void aulaMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodAulaMenu.fxml");
    }

    /**
     * Changes to orderFoodFoodTruckMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void foodtruckMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodFoodTruckMenu.fxml");
    }

    /**
     * Changes to orderFoodPulseMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void pulseMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodPulseMenu.fxml");
    }

    /**
     * Changes to orderFoodCoffeeStarMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void coffeestarMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodCoffeeStarMenu.fxml");
    }

    /**
     * Changes to orderFoodCafeXDinnerMenu.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void cafexMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodCafeXDinnerMenu.fxml");
    }

    /**
     * Changes to orderFoodConfirmation.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void orderFoodConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodConfirmation.fxml");
    }
}
