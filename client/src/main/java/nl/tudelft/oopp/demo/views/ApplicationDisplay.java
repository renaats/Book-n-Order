package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        URL xmlUrl = getClass().getResource("/login-screen.fxml");
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
        Parent pane = FXMLLoader.load(
                ApplicationDisplay.class.getResource(fxml));
        primaryStage.getScene().setRoot(pane);
    }

    public static void showCalendarScene(PersonalCalendarView cal){
        primaryStage.getScene().setRoot(cal);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
