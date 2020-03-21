package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.entities.Room;

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
            Building building = mapper.readValue(buildingJson, Building.class);
            return building;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all building JSONS to a list.
     * @param buildingsJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static List<Building> buildingListMapper(String buildingsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            List<Building> buildings = mapper.readValue(buildingsJson, new TypeReference<List<Building>>(){});
            return buildings;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(buildingsJson);
            alert.showAndWait();
        }
        return null;
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
            Room room = mapper.readValue(roomJson, Room.class);
            return room;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(roomJson);
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
            List<Room> rooms = mapper.readValue(roomsJson, new TypeReference<List<Room>>(){});
            return rooms;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(roomsJson);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps a JSON string to an AppUser object
     * @param roomJson JSON representation of a String
     * @return AppUser Object
     */
    public static AppUser appUserMapper(String roomJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            AppUser appUser = mapper.readValue(roomJson, AppUser.class);
            return appUser;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(roomJson);
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
            Restaurant restaurant = mapper.readValue(restaurantJson, Restaurant.class);
            return restaurant;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(restaurantJson);
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
            List<Restaurant> restaurantsList = mapper.readValue(restaurantsJson, new TypeReference<List<Restaurant>>(){});
            return restaurantsList;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(restaurantsJson);
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
        System.out.println(restaurantHoursJson);

        try {
            // Convert JSON string to Object
            RestaurantHours restaurantHours = mapper.readValue(restaurantHoursJson, RestaurantHours.class);
            return restaurantHours;
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
     * Maps all restaurant hours JSONS to an object.
     * @param buildingHourJson a JSON string
     * @return Restaurant hour object
     */
    public static BuildingHours buildingHoursMapper(String buildingHourJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            BuildingHours buildingHours = mapper.readValue(buildingHourJson, BuildingHours.class);
            return buildingHours;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(buildingHourJson);
            alert.showAndWait();
        }
        return null;
    }
}