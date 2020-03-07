package nl.tudelft.oopp.demo.services;

import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {
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
    public String add(int buildingId, String name) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return "Could not find building with id " + buildingId + "!";
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setBuilding(optionalBuilding.get());
        restaurant.setName(name);
        restaurantRepository.save(restaurant);
        return "Saved!";
    }

    /**
     * Updates a specified attribute for some restaurant.
     * @param id = the id of the restaurant
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public String update(int id, String attribute, String value) {
        if (restaurantRepository.findById(id).isEmpty()) {
            return "Restaurant with ID: " + id + " does not exist!";
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
                    return "Could not find building with id " + buildingid + "!";
                }
                Building building = optionalBuilding.get();
                restaurant.setBuilding(building);
                break;
            default:
                return "No attribute with name " + attribute + " found!";
        }
        restaurantRepository.save(restaurant);
        return "The attribute has been set!";
    }

    /**
     * Deletes a restaurant.
     * @param id = the id of the restaurant
     * @return String to see if your request passed
     */
    public String delete(int id) {
        if (!restaurantRepository.existsById(id)) {
            return "Restaurant with ID: " + id + " does not exist!";
        }
        restaurantRepository.deleteById(id);
        return "Deleted!";
    }

    /**
     * Lists all restaurants.
     * @return all restaurants
     */
    public Iterable<Restaurant> all() {
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
}
