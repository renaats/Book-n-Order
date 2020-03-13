package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class OrderFoodController implements Initializable {
    final ObservableList listTime = FXCollections.observableArrayList();

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
    public void loadData(){
        setLabel();
        loadTime();
    }
    /**
     * Changes to myAccountScene.fxml.
     *
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void setLabel(){

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
    public void loadTime() {
        listTime.removeAll(listTime);
        listMinutes.removeAll(listMinutes);
        for (int i = 0;i <= 45; i = i + 15) {
            if (i == 0) {
                listMinutes.add("00");
            } else {
                listMinutes.add(i);
            }
        }
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                listTime.add("0" + i);
            } else {
                listTime.add(i);
            }
        }
        pickUpTimeH.getItems().addAll(listTime);
        pickUpTimeMin.getItems().addAll(listMinutes);
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
    public void goToFoodOrderConfirmation(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/FoodConfirmation.fxml");
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
