package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the display of the views of the app
 */
public class ApplicationDisplay extends Application {

    private static Stage primaryStage;

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
    }

    /**
     * Opens a new window to show the personal calendar.
     * @param cal = personal calendar of the user
     * @throws IOException = All input will be valid, no need to check, this we throw.
     */
    public static void showCalendarScene(PersonalCalendar cal) throws IOException {
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
