package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Restaurant entity.
 * Receives requests from the RestaurantController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class RestaurantService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Adds a restaurant.
     * @param buildingId = the building, where the restaurant is located
     * @param name = the name of the restaurant
     * @return String to see if your request passed
     */
    public int add(int buildingId, String name) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setBuilding(optionalBuilding.get());
        restaurant.setName(name);
        restaurantRepository.save(restaurant);
        return ADDED;
    }

    /**
     * Updates a specified attribute for some restaurant.
     * @param id = the id of the restaurant
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public int update(int id, String attribute, String value) {
        if (restaurantRepository.findById(id).isEmpty()) {
            return RESTAURANT_NOT_FOUND;
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();

        switch (attribute) {
            case "name":
                restaurant.setName(value);
                break;
            case "building":
                int buildingid = Integer.parseInt(value);
                Optional<Building> optionalBuilding = buildingRepository.findById(buildingid);
                if (optionalBuilding.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Building building = optionalBuilding.get();
                restaurant.setBuilding(building);
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        restaurantRepository.save(restaurant);
        return ADDED;
    }

    /**
     * Deletes a restaurant.
     * @param id = the id of the restaurant
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!restaurantRepository.existsById(id)) {
            return RESTAURANT_NOT_FOUND;
        }
        restaurantRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all restaurants.
     * @return all restaurants
     */
    public List<Restaurant> all() {
        return restaurantRepository.findAll();
    }

    /**
     * Finds a restaurant with the specified id.
     * @param id = the restaurant id
     * @return a restaurant that matches the id
     */
    public Restaurant find(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    /**
     * Finds a restaurant with the specified name.
     * @param name = the restaurant name
     * @return a restaurant that matches the name
     */
    public Restaurant find(String name) {
        return restaurantRepository.findByName(name);
    }
}
