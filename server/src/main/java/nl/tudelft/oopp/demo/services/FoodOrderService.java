package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.ORDER_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESERVATION_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.USER_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_USER;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishOrderRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the FoodOrder entity.
 * Receives requests from the FoodOrderController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class FoodOrderService {
    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishOrderRepository dishOrderRepository;

    /**
     * Adds a foodOrder.
     * @param request = the Http request that calls this method.
     * @param restaurantId = the id of the restaurant associated to the food order.
     * @param deliverLocation = the building where the food order needs to be delivered.
     * @param deliverTimeMs = the deliver time of the food order in milliseconds (Java Date).
     * @return String containing the result of your request.
     */
    public int add(HttpServletRequest request, int restaurantId, int deliverLocation, long deliverTimeMs) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return RESTAURANT_NOT_FOUND;
        }

        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return NOT_FOUND;
        }

        Optional<Building> optionalDeliveryLocation = buildingRepository.findById(deliverLocation);
        if (optionalDeliveryLocation.isEmpty() && deliverLocation != 0) {
            return BUILDING_NOT_FOUND;
        }
        Building deliveryLocation = null;
        if (deliverLocation != 0) {
            deliveryLocation = optionalDeliveryLocation.get();
        }
        Restaurant restaurant = optionalRestaurant.get();

        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setRestaurant(restaurant);
        foodOrder.setAppUser(appUser);
        foodOrder.setDeliveryLocation(deliveryLocation);
        foodOrder.setDeliveryTime(new Date(deliverTimeMs));
        foodOrder.setDishOrders(new HashSet<>());
        foodOrderRepository.save(foodOrder);
        return foodOrder.getId() + 1000;
    }

    /**
     * Updates a specified attribute for some food order.
     * @param id = the id of the food order.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (foodOrderRepository.findById(id).isEmpty()) {
            return RESERVATION_NOT_FOUND;
        }
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), foodOrderRepository.getOne(id).getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        FoodOrder foodOrder = foodOrderRepository.findById(id).get();
        switch (attribute) {
            case "deliverylocation":
                int buildingId = Integer.parseInt(value);
                Optional<Building> optionalDeliveryLocation = buildingRepository.findById(buildingId);
                if (optionalDeliveryLocation.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Building deliveryLocation = optionalDeliveryLocation.get();
                foodOrder.setDeliveryLocation(deliveryLocation);
                break;
            case "deliverytime":
                foodOrder.setDeliveryTime(new Date(Long.parseLong(value)));
                break;
            case "useremail":
                Optional<AppUser> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return USER_NOT_FOUND;
                }
                AppUser appUser = optionalUser.get();
                foodOrder.setAppUser(appUser);
                break;
            case "active":
                foodOrder.setActive(Boolean.parseBoolean(value));
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        foodOrderRepository.save(foodOrder);
        return EXECUTED;
    }

    /**
     * Adds a dishOrder to food order.
     * @param id = the id of the food order.
     * @param dishName = the name of the dish.
     */
    public int addDishOrder(HttpServletRequest request, int id, String dishName, int amount) {
        if (!foodOrderRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return NOT_FOUND;
        }
        FoodOrder foodOrder = foodOrderRepository.getOne(id);
        if (!appUser.equals(foodOrder.getAppUser())) {
            return WRONG_USER;
        }
        if (!dishRepository.existsByName(dishName)) {
            return NOT_FOUND;
        }
        Dish dish = dishRepository.findByName(dishName);
        dishOrderRepository.save(new DishOrder(dish, foodOrder, amount));
        return ADDED;
    }

    /**
     * Gets all DishOrders for a food order.
     * @param id = the id of the food order.
     */
    public List<DishOrder> getDishOrders(HttpServletRequest request, int id) {
        if (!foodOrderRepository.existsById(id)) {
            return null;
        }
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return null;
        }
        FoodOrder foodOrder = foodOrderRepository.getOne(id);
        if (!appUser.equals(foodOrder.getAppUser())) {
            return null;
        }
        return dishOrderRepository.findAllByFoodOrderId(id);
    }

    /**
     * Deletes a food order.
     * @param id = the id of the food order.
     * @return String containing the result of your request.
     */
    public int delete(int id) {
        if (!foodOrderRepository.existsById(id)) {
            return RESERVATION_NOT_FOUND;
        }
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), foodOrderRepository.getOne(id).getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        foodOrderRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all food orders.
     * @return Iterable of all food orders.
     */
    public List<FoodOrder> all() {
        return foodOrderRepository.findAll();
    }

    /**
     * Finds a food order with the specified id.
     * @param id = the food order id.
     * @return a food order that matches the id.
     */
    public FoodOrder find(int id) {
        return foodOrderRepository.findById(id).orElse(null);
    }

    /**
     * Finds all past food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of past food orders for this user.
     */
    public List<FoodOrder> past(HttpServletRequest request) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return foodOrders;
        }
        for (FoodOrder foodOrder: foodOrderRepository.findAll()) {
            if (foodOrder.getAppUser() == appUser && (!foodOrder.getDeliveryTime().after(new Date()) || !foodOrder.isActive())) {
                foodOrders.add(foodOrder);
            }
        }
        return foodOrders;
    }

    /**
     * Finds all future food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of future food orders for this user.
     */
    public List<FoodOrder> future(HttpServletRequest request) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return foodOrders;
        }
        for (FoodOrder foodOrder: foodOrderRepository.findAll()) {
            if (foodOrder.getAppUser() == appUser && foodOrder.getDeliveryTime().after(new Date()) && foodOrder.isActive()) {
                foodOrders.add(foodOrder);
            }
        }
        return foodOrders;
    }

    /**
     * Finds all past food orders for the restaurants of the user that sends the Http request.
     * @param restaurantId = the restaurant for which the food orders are searched.
     * @return a list of past food orders for this user.
     */
    public List<FoodOrder> pastForRestaurant(int restaurantId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<FoodOrder> foodOrders = new ArrayList<>();
        for (FoodOrder foodOrder: foodOrderRepository.findAllByRestaurantId(restaurantId)) {
            if ((securityContext.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))
                    || foodOrder.getRestaurant().getEmail().equals(securityContext.getAuthentication().getName()))
                    && (!foodOrder.getDeliveryTime().after(new Date()) || !foodOrder.isActive())) {
                foodOrders.add(foodOrder);
            }
        }
        return foodOrders;
    }

    /**
     * Finds all future food orders for the restaurants of the user that sends the Http request.
     * @param restaurantId = the restaurant for which the food orders are searched.
     * @return a list of future food orders for this user.
     */
    public List<FoodOrder> futureForRestaurant(int restaurantId) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        for (FoodOrder foodOrder: foodOrderRepository.findAllByRestaurantId(restaurantId)) {
            if ((securityContext.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))
                    || foodOrder.getRestaurant().getEmail().equals(securityContext.getAuthentication().getName()))
                    && foodOrder.getDeliveryTime().after(new Date()) && foodOrder.isActive()) {
                foodOrders.add(foodOrder);
            }
        }
        return foodOrders;
    }

    /**
     * Finds all active food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of active food orders for this user.
     */
    public List<FoodOrder> active(HttpServletRequest request) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return foodOrders;
        }
        for (FoodOrder foodOrder: foodOrderRepository.findAll()) {
            if (foodOrder.getAppUser() == appUser && foodOrder.isActive()) {
                foodOrders.add(foodOrder);
            }
        }
        return foodOrders;
    }

    /**
     * Cancels a food order if it was made by the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @param foodOrderId = the id of the target order.
     * @return an error code.
     */
    public int cancel(HttpServletRequest request, int foodOrderId) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (foodOrderRepository.findById(foodOrderId).isEmpty()) {
            return ORDER_NOT_FOUND;
        }
        FoodOrder foodOrder = foodOrderRepository.findById(foodOrderId).get();
        if (!foodOrder.getAppUser().equals(appUser)) {
            return WRONG_USER;
        }
        foodOrder.setActive(false);
        foodOrderRepository.save(foodOrder);
        return EXECUTED;
    }

    /**
     * Adds a feedback to the food order.
     * @param foodOrderId the id of the food order.
     * @param feedback the feedback (true if positive, false if negative).
     * @return an error code.
     */
    public int addFeedback(int foodOrderId, boolean feedback) {
        if (foodOrderRepository.findById(foodOrderId).isEmpty()) {
            return ORDER_NOT_FOUND;
        }
        FoodOrder foodOrder = foodOrderRepository.findById(foodOrderId).get();
        foodOrder.setFeedback(feedback);
        foodOrder.setFeedbackHasBeenGiven(true);
        foodOrderRepository.save(foodOrder);
        return EXECUTED;
    }
}
