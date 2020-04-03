package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import javax.servlet.http.HttpServletRequest;

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

/**
 * Creates server side endpoints and routes requests to the RestaurantService.
 * Maps all requests that start with "/restaurant".
 * Manages access control on a per-method basis.
 */
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
     * @param email = the email of the restaurant
     * @return Error code
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRestaurant(
            @RequestParam int buildingId,
            @RequestParam String name,
            @RequestParam String email
    ) {
        return restaurantService.add(buildingId, name, email);
    }


    /**
     * Adds a restaurant.
     * @param id the restaurant id.
     * @param feedback the feedback of the user.
     * @return Error code.
     */
    @Secured({USER})
    @PostMapping(path = "/addFeedback") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRestaurant(
            @RequestParam int id,
            @RequestParam boolean feedback
    ) {
        return restaurantService.addFeedback(id,feedback);
    }

    /**
     * Adds a review to a restaurant.
     * @param id = the id of the restaurant
     * @return Error code
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/feedback")
    @ResponseBody
    public int restaurantFeedback(@RequestParam int id) {
        return restaurantService.getFeedback(id);
    }

    /**
     * Updates a specified attribute for some restaurant.
     * @param id = the id of the restaurant
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return Error code
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return restaurantService.update(id, attribute, value);
    }


    /**
     * Deletes a restaurant.
     * @param id = the id of the restaurant
     * @return Error code
     */
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete/{restaurantID}")
    @ResponseBody
    public int deleteRestaurant(@PathVariable(value = "restaurantID") int id) {
        return restaurantService.delete(id);
    }

    /**
     * Lists all restaurants.
     * @return all restaurants
     */
    @Secured(USER)
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantService.all();
    }

    /**
     * Lists all restaurants that the user is an owner of.
     * @param request = the Http request that calls this method.
     * @return all restaurant that have this user as an owner.
     */
    @Secured({RESTAURANT, ADMIN})
    @GetMapping(path = "/owned")
    public Iterable<Restaurant> getOwned(HttpServletRequest request) {
        return restaurantService.owned(request);
    }

    /**
     * Finds a restaurant with the specified id.
     * @param id = the restaurant id
     * @return a restaurant that matches the id
     */
    @Secured(USER)
    @GetMapping(path = "/find/{id}")
    @ResponseBody
    public Restaurant findRestaurant(@PathVariable (value = "id") int id) {
        return restaurantService.find(id);
    }

    /**
     * Finds a restaurant with the specified name.
     * @param name = the restaurant name
     * @return a restaurant that matches the name
     */
    @Secured(USER)
    @GetMapping(path = "/findName/{name}")
    @ResponseBody
    public Restaurant findRestaurantByName(@PathVariable (value = "name") String name) {
        return restaurantService.find(name);
    }
}