package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
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
            System.out.println("test4");
            e.printStackTrace();
        }
        return null;
    }

    public static Room roomMapper(String roomJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            System.out.println(roomJson);
            Room room = mapper.readValue(roomJson, Room.class);
            return room;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("test");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all building JSONS to a list.
     * @param buildingsJson a JSON string representing a list.
     * @return A list filled with object Buildings
     */
    public static List<Building> roomListMapper(String buildingsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            System.out.println(buildingsJson);
            List<Building> buildings = mapper.readValue(buildingsJson, new TypeReference<List<Building>>(){});
            return buildings;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
