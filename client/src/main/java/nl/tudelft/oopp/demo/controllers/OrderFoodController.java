package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class OrderFoodController {

    /**
     * Changes to myCurrentBookings.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myAccountScene.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to bikeReservations.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void rentBike(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bikeReservations.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void bookRoom(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/bookRoom.fxml");
    }

    /**
     * Changes to orderFood.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void orderFood(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFood.fxml");
    }

    /**
     * Changes to mainMenuReservations.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * Changes to orderFood1.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void restaurantChoice(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFood1.fxml");
    }

    /**
     * Changes to orderFoodAulaMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void aulaMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/DinamicRestaurant.fxml");
    }
    /**
     * return to the reservations menu when the back arrow button is clicked.
     * @param mouseEvent The event tis the clicking of the arrow button
     * @throws IOException the input will allways be the same, so it should never throw an IO exception
     */
    public void goToMainMenuReservations(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
    /**
     * Changes to orderFoodFoodTruckMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void foodtruckMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodFoodTruckMenu.fxml");
    }

    /**
     * Changes to orderFoodPulseMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void pulseMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodPulseMenu.fxml");
    }

    /**
     * Changes to orderFoodCoffeeStarMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void coffeestarMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodCoffeeStarMenu.fxml");
    }

    /**
     * Changes to orderFoodCafeXDinnerMenu.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void cafexMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/orderFoodCafeXDinnerMenu.fxml");
    }

    /**
     * Changes to orderFoodConfirmation.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void orderFoodConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/FoodConfirmation.fxml");
    }

}
