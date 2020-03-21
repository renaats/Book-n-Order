package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.user.UserInformation;

/**
 * Maps Json server responses into objects in the Entities package
 */
public class JsonMapper {

    /**
     * Current mapper for buildings
     * @param buildingJson JSON string representation of a building
     * @return Building object
     */
    public static Building buildingMapper(String buildingJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(buildingJson, Building.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(buildingJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all building JSONS to a list.
     * @param buildingsJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static List<Building> buildingListMapper(String buildingsJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(buildingsJson, new TypeReference<>(){});
    }

    /**
     * Maps JSON to Room entity.
     * @param roomJson JSON representation of a room.
     * @return Room entity.
     */
    public static Room roomMapper(String roomJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomJson, Room.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(roomJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all room JSONS to a list.
     * @param roomsJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static List<Room> roomListMapper(String roomsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomsJson, new TypeReference<>(){});
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(roomsJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps a JSON string to an AppUser object
     * @param appUserJson JSON representation of a String
     * @return AppUser Object
     */
    public static AppUser appUserMapper(String appUserJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(appUserJson, AppUser.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(appUserJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps a JSON string to an UserInformation object
     * @param userInformationJson JSON representation of a String
     * @return UserInformation Object
     */
    public static UserInformation userInformationMapper(String userInformationJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(userInformationJson, UserInformation.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(userInformationJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps a JSON string to an Restaurant object
     * @param restaurantJson JSON representation of a String
     * @return Restaurant Object
     */
    public static Restaurant restaurantMapper(String restaurantJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantJson, Restaurant.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(restaurantJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all restaurant JSONS to a list.
     * @param restaurantsJson a JSON string representing a list.
     * @return A list filled with object Restaurant
     */
    public static List<Restaurant> restaurantListMapper(String restaurantsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantsJson, new TypeReference<>(){});
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(restaurantsJson);
            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(JsonMapper.class.getResource("/alertWarning.css").toExternalForm());
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all restaurant hours JSONS to an object.
     * @param restaurantHoursJson a JSON string
     * @return Restaurant hour object
     */
    public static RestaurantHours restaurantHoursMapper(String restaurantHoursJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantHoursJson, RestaurantHours.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(restaurantHoursJson);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all building hours JSONS to an object.
     * @param buildingHourJson a JSON string
     * @return building hour object
     */
    public static BuildingHours buildingHoursMapper(String buildingHourJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(buildingHourJson, BuildingHours.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(buildingHourJson);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all bikes JSONS to a list of bike objects.
     * @param bikesJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static List<Bike> bikeListMapper(String bikesJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            List<Bike> bikes = mapper.readValue(bikesJson, new TypeReference<List<Bike>>(){});
            return bikes;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(bikesJson);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all bikes JSONS to a list of bike objects.
     * @param bikesJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static Object bikeListMapper(String bikesJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            List<Building> bikes = mapper.readValue(bikesJson, new TypeReference<List<Building>>(){});
            return bikes;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(bikesJson);
            alert.showAndWait();
        }
        return null;
    }
}