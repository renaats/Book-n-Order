package nl.tudelft.oopp.demo.services;

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
     * @param date = the date in milliseconds or the day of the week for regular hours
     * @param startTimeS = the starting time in milliseconds
     * @param endTimeS = the ending time in milliseconds
     * @return String containing the result of your request.
     */
    public int add(int restaurantId, long date, int startTimeS, int endTimeS) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        if (date >= 7) {
            date = BuildingHourService.parse(date);
        }
        if (restaurant == null) {
            return 422;
        }
        if (date < 1) {
            return 425;
        }
        if (endTimeS < startTimeS) {
            return 426;
        }
        if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, date)) {
            return 427;
        }
        RestaurantHours restaurantHours =
                new RestaurantHours(date, restaurant, LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
        restaurantHourRepository.save(restaurantHours);
        return 201;
    }

    /**
     * Updates a database attribute.
     * @param id = the restaurant hour id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return message if it passes
     */
    public int update(int id, String attribute, String value) {
        if (!restaurantHourRepository.existsById(id)) {
            return 416;
        }
        RestaurantHours restaurantHours = restaurantHourRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "day":
                long dateInMilliseconds = Long.parseLong(value);
                if (dateInMilliseconds > 7) {
                    dateInMilliseconds = BuildingHourService.parse(dateInMilliseconds);
                }
                if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantHours.getRestaurant().getId(), dateInMilliseconds)) {
                    return 427;
                }
                restaurantHours.setDay(dateInMilliseconds);
                break;
            case "restaurantid":
                int restaurantId = Integer.parseInt(value);
                if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, restaurantHours.getDay())) {
                    return 427;
                }
                Restaurant restaurant = restaurantService.find(restaurantId);
                if (restaurant == null) {
                    return 422;
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
                return 412;
        }
        restaurantHourRepository.save(restaurantHours);
        return 201;
    }

    /**
     * Deletes restaurant hours.
     * @param restaurantId = the id of the restaurant
     * @param dateInMilliseconds = the date in milliseconds
     * @return String to see if your request passed
     */
    public int delete(int restaurantId, long dateInMilliseconds) {
        if (dateInMilliseconds > 7) {
            dateInMilliseconds = BuildingHourService.parse(dateInMilliseconds);
        }
        if (!restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, dateInMilliseconds)) {
            return 404;
        }
        restaurantHourRepository.deleteByRestaurant_IdAndDay(restaurantId, dateInMilliseconds);
        return 200;
    }

    /**
     * Lists all restaurant hours.
     * @return all restaurant hours
     */
    public List<RestaurantHours> all() {
        return restaurantHourRepository.findAll();
    }

    /**
     * Finds the hours for a restaurant with the specified id.
     * @param restaurantId = the id of the restaurant
     * @param dateInMilliseconds = the date in milliseconds
     * @return restaurant hours that match the id
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
}
