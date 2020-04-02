package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.HAS_ROOMS;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Building entity.
 * Receives requests from the BuildingController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    /**
     * Adds a building.
     * @param name = the name of the building
     * @return String to see if your request passed
     */
    public int add(String name, String street, String faculty, int houseNumber) {
        if (buildingRepository.existsByName(name)) {
            return DUPLICATE_NAME;
        }
        Building building = new Building();
        building.setName(name);
        building.setStreet(street);
        building.setFaculty(faculty);
        building.setHouseNumber(houseNumber);
        building.setRooms(new HashSet<>());
        buildingRepository.save(building);
        return ADDED;
    }

    /**
     * Updates a database attribute.
     * @param id = the building id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return message if it passes
     */
    public int update(int id, String attribute, String value) {
        if (!buildingRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        Building building = buildingRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "name":
                if (buildingRepository.existsByName(value)) {
                    return DUPLICATE_NAME;
                }
                building.setName(value);
                break;
            case "street":
                building.setStreet(value);
                break;
            case "faculty":
                building.setFaculty(value);
                break;
            case "housenumber":
                building.setHouseNumber(Integer.parseInt(value));
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        buildingRepository.save(building);
        return ADDED;
    }

    /**
     * Deletes a building.
     * @param id = the id of the building
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (buildingRepository.findById(id).isEmpty()) {
            return NOT_FOUND;
        }
        if (buildingRepository.findById(id).get().hasRooms()) {
            return HAS_ROOMS;
        }
        buildingRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all buildings.
     * @return all buildings
     */
    public List<Building> all() {
        return buildingRepository.findAll();
    }

    /**
     * Finds a building with the specified id.
     * @param id = the id of the building
     * @return a building that matches the id
     */
    public Building find(int id) {
        return buildingRepository.findById(id).orElse(null);
    }

    /**
     * Finds a building with the specified name.
     * @param name = the name of the building
     * @return a building that matches the name
     */
    public Building find(String name) {
        return buildingRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
    }

    /**
     * Return all rooms that are in the building with the specified id.
     * @param id = the id of the building
     * @return all rooms that are in the building that matches the id
     */
    public Set<Room> rooms(int id) {
        if (buildingRepository.findById(id).isEmpty()) {
            return null;
        }
        return buildingRepository.findById(id).get().getRooms();
    }
}
