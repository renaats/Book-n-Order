package nl.tudelft.oopp.demo.services;

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
            return 422;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setBuilding(optionalBuilding.get());
        restaurant.setName(name);
        restaurantRepository.save(restaurant);
        return 201;
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
            return 428;
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
                    return 422;
                }
                Building building = optionalBuilding.get();
                restaurant.setBuilding(building);
                break;
            default:
                return 412;
        }
        restaurantRepository.save(restaurant);
        return 201;
    }

    /**
     * Deletes a restaurant.
     * @param id = the id of the restaurant
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!restaurantRepository.existsById(id)) {
            return 428;
        }
        restaurantRepository.deleteById(id);
        return 200;
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
