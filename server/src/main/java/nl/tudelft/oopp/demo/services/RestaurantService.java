package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserRepository userRepository;

    /**
     * Determines if this user can edit the restaurant's properties.
     * @param securityContext = the SecurityContext of the request.
     * @param restaurant = the restaurant that is modified.
     * @return a boolean representing whether the user should be able to edit the properties.
     */
    public static boolean noPermissions(SecurityContext securityContext, Restaurant restaurant) {
        if (restaurant == null) {
            return true;
        }
        return securityContext.getAuthentication() == null
                || (!securityContext.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))
                && !restaurant.getEmail().equals(securityContext.getAuthentication().getName()));
    }

    /**
     * Adds a restaurant.
     * @param buildingId = the building, where the restaurant is located
     * @param name = the name of the restaurant
     * @param email = the email of the restaurant
     * @return String to see if your request passed.
     */
    public int add(int buildingId, String name, String email) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }
        if (restaurantRepository.existsByName(name)) {
            return DUPLICATE_NAME;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setBuilding(optionalBuilding.get());
        restaurant.setName(name);
        restaurant.setEmail(email);
        restaurantRepository.save(restaurant);
        return ADDED;
    }

    /**
     * Adds a feedback to the restaurant, decreases the feedbackCounter by 1 if negative and increases by 1 of positive.
     * @param id the restaurant id.
     * @param feedback the feedback is true if positive and false if negative.
     * @return the server response code.
     */
    public int addFeedback(int id, boolean feedback) {
        if (restaurantRepository.findById(id).isEmpty()) {
            return 428;
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();
        restaurant.addFeedback(feedback);
        restaurantRepository.save(restaurant);
        return EXECUTED;
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

        if (noPermissions(SecurityContextHolder.getContext(), restaurant)) {
            return WRONG_CREDENTIALS;
        }

        switch (attribute) {
            case "name":
                if (restaurantRepository.existsByName(value)) {
                    return DUPLICATE_NAME;
                }
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
            case "email":
                restaurant.setEmail(value);
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
        if (noPermissions(SecurityContextHolder.getContext(), restaurantRepository.getOne(id))) {
            return WRONG_CREDENTIALS;
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
     * Lists all accounts that the user is an owner of.
     * @param request = the Http request that calls this method.
     * @return all restaurant that have this user as an owner.
     */
    public List<Restaurant> owned(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return null;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))) {
            return restaurantRepository.findAll();
        }
        return restaurantRepository.findAllByEmail(appUser.getEmail());
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
        return restaurantRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
    }

    /**
     * Gets the feedbackCounter of the restaurant with the given id.
     * @param id the id of the restaurant.
     * @return the value of feedbackCounter.
     */
    public int getFeedback(int id) {
        return restaurantRepository.findById(id).get().getFeedbackCounter();
    }
}
