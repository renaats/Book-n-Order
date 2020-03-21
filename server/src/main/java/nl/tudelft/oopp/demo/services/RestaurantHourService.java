package nl.tudelft.oopp.demo.services;

import java.time.LocalTime;
import java.util.List;

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
     * @param day = the day of the week in number representation (1 to 7)
     * @param startTimeS = the starting time in milliseconds
     * @param endTimeS = the ending time in milliseconds
     * @return String containing the result of your request.
     */
    public int add(int restaurantId, int day, int startTimeS, int endTimeS) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        if (restaurant == null) {
            return 422;
        }
        if (day < 1 || day > 7) {
            return 425;
        }
        if (endTimeS < startTimeS) {
            return 426;
        }
        if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, day)) {
            return 427;
        }
        RestaurantHours restaurantHours = new RestaurantHours(day, restaurant,LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
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
                if (restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantHours.getRestaurant().getId(), Integer.parseInt(value))) {
                    return 427;
                }
                restaurantHours.setDay(Integer.parseInt(value));
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
     * @param day = the day of the week
     * @return String to see if your request passed
     */
    public int delete(int restaurantId, int day) {
        if (!restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, day)) {
            return 404;
        }
        restaurantHourRepository.deleteByRestaurant_IdAndDay(restaurantId, day);
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
     * @param day = the day of the week
     * @return restaurant hours that match the id
     */
    public RestaurantHours find(int restaurantId, int day) {
        if (!restaurantHourRepository.existsByRestaurant_IdAndDay(restaurantId, day)) {
            return null;
        }
        return restaurantHourRepository.findByRestaurant_IdAndDay(restaurantId, day);
    }
}
