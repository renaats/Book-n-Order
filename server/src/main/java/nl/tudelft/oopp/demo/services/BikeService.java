package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Bike entity.
 * Receives requests from the BikeController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class BikeService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BikeRepository bikeRepository;

    /**
     * Adds a bike.
     * @param buildingId = the building, where the bike is located
     * @param available = the availability of the bike
     * @return String to see if your request passed
     */
    public int add(int buildingId, boolean available) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return ID_NOT_FOUND;
        }

        Bike bike = new Bike();
        bike.setLocation(optionalBuilding.get());
        bike.setAvailable(available);
        bikeRepository.save(bike);
        return ADDED;
    }

    /**
     * Updates a specified attribute for some bike.
     * @param id = the id of the bike
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public int update(int id, String attribute, String value) {
        if (bikeRepository.findById(id).isEmpty()) {
            return ID_NOT_FOUND;
        }
        Bike bike = bikeRepository.findById(id).get();

        switch (attribute) {
            case "available":
                bike.setAvailable(Boolean.parseBoolean(value));
                break;
            case "location":
                int buildingid = Integer.parseInt(value);
                Optional<Building> optionalBuilding = buildingRepository.findById(buildingid);
                if (optionalBuilding.isEmpty()) {
                    return ID_NOT_FOUND;
                }
                Building building = optionalBuilding.get();
                bike.setLocation(building);
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        bikeRepository.save(bike);
        return EXECUTED;
    }

    /**
     * Deletes a bike.
     * @param id = the id of the bike
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!bikeRepository.existsById(id)) {
            return NOT_FOUND;
        }
        bikeRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all bikes.
     * @return all bikes
     */
    public List<Bike> all() {
        return bikeRepository.findAll();
    }

    /**
     * Finds a bike with the specified id.
     * @param id = the bike id
     * @return a bike that matches the id
     */
    public Bike find(int id) {
        return bikeRepository.findById(id).orElse(null);
    }
}
