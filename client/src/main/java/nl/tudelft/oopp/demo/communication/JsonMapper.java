package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;


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
     * Maps all building JSONS to a list.
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
     * @param appUserJson JSON representation of a String
     * @return AppUser Object
     */
    public static AppUser appUserMapper(String appUserJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
                // Convert JSON string to Object
                AppUser appUser = mapper.readValue(appUserJson, AppUser.class);
                return appUser;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(appUserJson);
                alert.showAndWait();
            }
            return null;
        }

        /**
         * Maps JSON to RoomReservation entity.
         * @param roomReservationJson representation of a room.
         * @return RoomReservation entity.
         */
    public static RoomReservation roomReservationMapper(String roomReservationJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            RoomReservation roomReservation = mapper.readValue(roomReservationJson, RoomReservation.class);
            return roomReservation;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(roomReservationJson);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Maps all Room Reservation JSONS to a list.
     * @param roomReservationsJson a JSON string representing a list.
     * @return A list filled with object Room Reservation
     */
    public static List<RoomReservation> roomReservationsListMapper(String roomReservationsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            List<RoomReservation> allRoomReservations = mapper.readValue(roomReservationsJson, new TypeReference<List<RoomReservation>>(){});
            return allRoomReservations;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(roomReservationsJson);
            alert.showAndWait();
        }
        return null;
    }
}
