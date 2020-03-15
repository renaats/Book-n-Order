package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.services.RestaurantHourService;
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
@RequestMapping(path = "/restaurant_hours")
public class RestaurantHourController {
    @Autowired
    private RestaurantHourService restaurantHourService;

    /**
     * Adds restaurant hours to the database.
     * @param restaurantId = the id of the restaurant.
     * @param day = the day of the week in number representation (1 to 7)
     * @param startTimeS = the starting time in seconds
     * @param endTimeS = the ending time in seconds
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addRestaurantHours(@RequestParam int restaurantId, @RequestParam int day, @RequestParam int startTimeS, @RequestParam int endTimeS) {
        return restaurantHourService.add(restaurantId, day, startTimeS, endTimeS);
    }

    /**
     * Updates a database attribute.
     * @param id = the restaurant hour id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return message if it passes
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateRestaurantHours(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return restaurantHourService.update(id, attribute, value);
    }

    /**
     * Deletes restaurant hours.
     * @param restaurantId = the id of the restaurant
     * @param day = the day of the week
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRestaurantHours(@RequestParam int restaurantId, @RequestParam int day) {
        return restaurantHourService.delete(restaurantId, day);
    }

    /**
     * Lists all restaurant hours in the database.
     * @return all restaurant hours in the database
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<RestaurantHours> getAllRestaurantHours() {
        return restaurantHourService.all();
    }

    /**
     * Finds the hours for a restaurant with the specified id.
     * @param restaurantId = the id of the restaurant
     * @param day = the day of the week
     * @return restaurant hours that match the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{restaurantID}/{day}")
    @ResponseBody
    public RestaurantHours findRestaurantHours(@PathVariable(value = "restaurantID") int restaurantId, @PathVariable(value = "day") int day) {
        return restaurantHourService.find(restaurantId, day);
    }
}
