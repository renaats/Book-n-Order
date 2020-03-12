package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Repository
@RestController
@RequestMapping(path = "/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    /**
     * Adds a restaurant.
     * @param buildingId = the building, where the restaurant is located
     * @param name = the name of the restaurant
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRestaurant(
            @RequestParam int buildingId,
            @RequestParam String name
    ) {
        return restaurantService.add(buildingId, name);
    }

    /**
     * Updates a specified attribute for some restaurant.
     * @param id = the id of the restaurant
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return restaurantService.update(id, attribute, value);
    }


    /**
     * Deletes a restaurant.
     * @param id = the id of the restaurant
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @DeleteMapping(path = "/delete/{restaurantID}")
    @ResponseBody
    public int deleteRestaurant(@PathVariable(value = "restaurantID") int id) {
        return restaurantService.delete(id);
    }

    /**
     * Lists all restaurants.
     * @return all restaurants
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantService.all();
    }

    /**
     * Finds a restaurant with the specified id.
     * @param id = the restaurant id
     * @return a restaurant that matches the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{id}")
    @ResponseBody
    public Restaurant findRestaurant(@PathVariable (value = "id") int id) {
        return restaurantService.find(id);
    }

}