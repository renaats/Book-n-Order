package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import nl.tudelft.oopp.demo.entities.Building;

public class JsonMapper {

    /**
     * Current test mapper for buildings
     * @param buildingJson JSON string representation of a building
     * @return Building object
     */
    public static Object map(String buildingJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            System.out.println(buildingJson);
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
}
