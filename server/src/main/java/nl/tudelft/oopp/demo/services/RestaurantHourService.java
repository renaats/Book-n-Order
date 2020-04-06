package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_DAY;
import static nl.tudelft.oopp.demo.config.Constants.END_BEFORE_START;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_DAY;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.repositories.RestaurantHourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the RestaurantHours entity.
 * Receives requests from the RestaurantHourController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class RestaurantHourService {
    @Autowired
    RestaurantHourRepository restaurantHourRepository;
    @Autowired
    RestaurantService restaurantService;

    /**
     * Adds restaurant hours to the database.
     * @param restaurantId = the id of the restaurant.
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @param startTimeS = the starting time in milliseconds.
     * @param endTimeS = the ending time in milliseconds.
     * @return String containing the result of your request.
     */
    public int add(int restaurantId, long date, int startTimeS, int endTimeS) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        if (date > 7) {
            date = BuildingHourService.parse(date);
        }
        if (restaurant == null) {
            return BUILDING_NOT_FOUND;
        }
        if (date < 1) {
            return INVALID_DAY;
        }
        if (endTimeS < startTimeS) {
            return END_BEFORE_START;
        }
        if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, date)) {
            return DUPLICATE_DAY;
        }
        RestaurantHours restaurantHours =
                new RestaurantHours(date, restaurant, LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
        restaurantHourRepository.save(restaurantHours);
        return ADDED;
    }

    /**
     * Updates a database attribute.
     * @param id = the restaurant hour id.
     * @param attribute = the attribute that is changed.
     * @param value = the new value of the attribute.
     * @return message if it passes.
     */
    public int update(int id, String attribute, String value) {
        if (!restaurantHourRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        RestaurantHours restaurantHours = restaurantHourRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "day":
                long dateInMilliseconds = Long.parseLong(value);
                if (dateInMilliseconds > 7) {
                    dateInMilliseconds = BuildingHourService.parse(dateInMilliseconds);
                }
                if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantHours.getRestaurant().getId(), dateInMilliseconds)) {
                    return DUPLICATE_DAY;
                }
                restaurantHours.setDay(dateInMilliseconds);
                break;
            case "restaurantid":
                int restaurantId = Integer.parseInt(value);
                if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, restaurantHours.getDay())) {
                    return DUPLICATE_DAY;
                }
                Restaurant restaurant = restaurantService.find(restaurantId);
                if (restaurant == null) {
                    return BUILDING_NOT_FOUND;
                }
                restaurantHours.setRestaurant(restaurant);
                break;
            case "starttimes":
                restaurantHours.setStartTime(LocalTime.ofSecondOfDay(Integer.parseInt(value)));
                break;
            case "endtimes":
                restaurantHours.setEndTime(LocalTime.ofSecondOfDay(Integer.parseInt(value)));
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        restaurantHourRepository.save(restaurantHours);
        return EXECUTED;
    }

    /**
     * Deletes restaurant hours.
     * @param restaurantId = the id of the restaurant.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return String to see if your request passed.
     */
    public int delete(int restaurantId, long dateInMilliseconds) {
        if (dateInMilliseconds > 7) {
            dateInMilliseconds = BuildingHourService.parse(dateInMilliseconds);
        }
        if (!restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, dateInMilliseconds)) {
            return NOT_FOUND;
        }
        restaurantHourRepository.deleteByRestaurant_IdAndDay(restaurantId, dateInMilliseconds);
        return EXECUTED;
    }

    /**
     * Lists all restaurant hours.
     * @return all restaurant hours.
     */
    public List<RestaurantHours> all() {
        return restaurantHourRepository.findAll();
    }

    /**
     * Finds the hours for a restaurant with the specified id.
     * @param restaurantId = the id of the restaurant.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return restaurant hours that match the id.
     */
    public RestaurantHours find(int restaurantId, long dateInMilliseconds) {
        dateInMilliseconds = BuildingHourService.parse(dateInMilliseconds);
        if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, dateInMilliseconds)) {
            return restaurantHourRepository.findByRestaurant_IdAndDay(restaurantId, dateInMilliseconds);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        calendar.setTime(new Date(dateInMilliseconds));
        long day = calendar.get(Calendar.DAY_OF_WEEK);
        if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, day)) {
            return restaurantHourRepository.findByRestaurant_IdAndDay(restaurantId, day);
        }
        return null;
    }

    /**
     * Finds the hours for a restaurant with the specified id and day.
     * @param restaurantId = the id of the restaurant.
     * @param day = the day;
     * @return restaurant hours that match the id.
     */
    public RestaurantHours findAdmin(int restaurantId, long day) {
        if (!restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, day)) {
            return null;
        }
        return restaurantHourRepository.findByRestaurant_IdAndDay(restaurantId, day);
    }
}
