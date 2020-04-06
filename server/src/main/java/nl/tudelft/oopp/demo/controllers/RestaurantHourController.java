package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.USER;

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

/**
 * Creates server side endpoints and routes requests to the RestaurantHourService.
 * Maps all requests that start with "/restaurant_hours".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/restaurant_hours")
public class RestaurantHourController {
    @Autowired
    private RestaurantHourService restaurantHourService;

    /**
     * Adds restaurant hours to the database.
     * @param restaurantId = the id of the restaurant.
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @param startTimeS = the starting time in seconds.
     * @param endTimeS = the ending time in seconds.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addRestaurantHours(@RequestParam int restaurantId, @RequestParam long date, @RequestParam int startTimeS, @RequestParam int endTimeS) {
        return restaurantHourService.add(restaurantId, date, startTimeS, endTimeS);
    }

    /**
     * Updates a database attribute.
     * @param id = the restaurant hour id.
     * @param attribute = the attribute that is changed.
     * @param value = the new value of the attribute.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateRestaurantHours(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return restaurantHourService.update(id, attribute, value);
    }

    /**
     * Deletes restaurant hours.
     * @param restaurantId = the id of the restaurant.
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRestaurantHours(@RequestParam int restaurantId, @RequestParam long date) {
        return restaurantHourService.delete(restaurantId, date);
    }

    /**
     * Lists all restaurant hours in the database.
     * @return all restaurant hours in the database.
     */
    @Secured({ADMIN, RESTAURANT})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<RestaurantHours> getAllRestaurantHours() {
        return restaurantHourService.all();
    }

    /**
     * Finds the hours for a restaurant with the specified id.
     * @param restaurantId = the id of the restaurant.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return restaurant hours that match the id.
     */
    @Secured(USER)
    @GetMapping(path = "/find/{restaurantID}/{day}")
    @ResponseBody
    public RestaurantHours findRestaurantHours(@PathVariable(value = "restaurantID") int restaurantId,
                                               @PathVariable(value = "day") long dateInMilliseconds) {
        return restaurantHourService.find(restaurantId, dateInMilliseconds);
    }

    /**
     * Finds the hours for a restaurant with the specified id.
     * @param restaurantId = the id of the restaurant.
     * @param day = the day.
     * @return restaurant hours that match the id.
     */
    @Secured({ADMIN, RESTAURANT})
    @GetMapping(path = "/findAdmin/{restaurantID}/{day}")
    @ResponseBody
    public RestaurantHours findAdminHours(@PathVariable(value = "restaurantID") int restaurantId, @PathVariable(value = "day") long day) {
        return restaurantHourService.findAdmin(restaurantId, day);
    }
}
