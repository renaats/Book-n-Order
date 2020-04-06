package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import java.net.URL;

import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import nl.tudelft.oopp.demo.controllers.bikes.BikeReservationConfirmationController;
import nl.tudelft.oopp.demo.controllers.database.DatabaseAddDishController;
import nl.tudelft.oopp.demo.controllers.database.DatabaseViewFoodOrderController;
import nl.tudelft.oopp.demo.controllers.restaurants.FoodReservationConfirmationController;
import nl.tudelft.oopp.demo.controllers.restaurants.OrderFoodController;
import nl.tudelft.oopp.demo.controllers.rooms.RoomConfirmationController;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;

/**
 * Manages the display of the views of the app
 */
public class ApplicationDisplay extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Loads the initial view
     * @throws IOException = All input will be valid, no need to check, this we throw.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationDisplay.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/LoginScreen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Changes the current scene to given fxml file.
     * @param fxml = filename of scene you want to change the current scene to.
     * @throws IOException = All input will be valid, no need to check, this we throw.
     */
    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(ApplicationDisplay.class.getResource(fxml));
        primaryStage.getScene().setRoot(pane);
        primaryStage.setResizable(false);
    }

    /**
     * Changes the current scene to given fxml file and transfers a variable.
     * @param fxml = filename of scene you want to change the current scene to.
     * @param variable1 = some variable.
     * @param variable2 = some variable.
     * @throws IOException = All input will be valid, no need to check, this we throw.
     */
    public static void changeSceneWithVariables(String fxml, Object variable1, Object variable2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationDisplay.class.getResource(fxml));
        fxmlLoader.setControllerFactory(controllerClass -> {
            try {
                if (controllerClass == OrderFoodController.class) {
                    return new OrderFoodController((List<Dish>) variable1, (Restaurant) variable2);
                } else if (controllerClass == FoodReservationConfirmationController.class) {
                    return new FoodReservationConfirmationController((long) variable1,(String[]) variable2);
                } else if (controllerClass == DatabaseViewFoodOrderController.class) {
                    return new DatabaseViewFoodOrderController((Restaurant) variable1);
                } else if (controllerClass == DatabaseAddDishController.class) {
                    return new DatabaseAddDishController((Menu) variable1);
                } else if (controllerClass == BikeReservationConfirmationController.class) {
                    return new BikeReservationConfirmationController((int[]) variable1, (long[]) variable2);
                } else {
                    return controllerClass.getDeclaredConstructor().newInstance();
                }
            } catch (Exception exc) {
                throw new RuntimeException(exc); // just bail
            }
        });
        Parent pane = fxmlLoader.load();
        primaryStage.getScene().setRoot(pane);
    }

    /**
     * Opens a new window to show the personal calendar.
     * @param cal = personal calendar of the user
     */
    public static void showCalendarScene(PersonalCalendarView cal) {
        Stage stage = new Stage();
        stage.setScene(new Scene(cal));
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
