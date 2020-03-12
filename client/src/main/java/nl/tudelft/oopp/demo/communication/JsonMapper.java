package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.oopp.demo.entities.Building;

import java.io.IOException;

public class JsonMapper {

    public static Object map(String buildingJson) {

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
}
