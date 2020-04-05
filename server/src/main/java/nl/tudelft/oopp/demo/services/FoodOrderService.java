package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.*;

import static nl.tudelft.oopp.demo.config.Constants.*;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;

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
    private RestaurantHourService restaurantHourService;

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
        Restaurant restaurant = optionalRestaurant.get();

        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return NOT_FOUND;
        }

        Optional<Building> optionalDeliveryLocation = buildingRepository.findById(deliverLocation);
        if (optionalDeliveryLocation.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }
        RestaurantHours restaurantHours = restaurantHourService.find(restaurantId, deliverTimeMs);
        LocalTime startTime2 = restaurantHours.getStartTime();
        LocalTime endTime = restaurantHours.getEndTime();
        Date order = new Date(deliverTimeMs);
        LocalDate localDate = order.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime dateTime = LocalDateTime.of(localDate, restaurantHours.getStartTime());
        Date openingHour = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        dateTime = LocalDateTime.of(localDate, restaurantHours.getEndTime());
        Date closingHour = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        if(order.compareTo(openingHour) < 0 || order.compareTo(closingHour) > 0) {
            return CLOSED_RESTAURANT;
        }
        Building deliveryLocation = optionalDeliveryLocation.get();
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setRestaurant(restaurant);
        foodOrder.setAppUser(appUser);
        foodOrder.setDeliveryLocation(deliveryLocation);
        foodOrder.setDeliveryTime(new Date(deliverTimeMs));
        foodOrder.setDishes(new HashSet<>());
        foodOrderRepository.save(foodOrder);
        return ADDED;
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
     * Adds a dish to food order.
     * @param id = the id of the food order.
     * @param dishName = the name of the dish.
     */
    public int addDish(HttpServletRequest request, int id, String dishName) {
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
        Dish dish;
        dish = dishRepository.findByName(dishName);
        foodOrder.addDish(dish);
        foodOrderRepository.save(foodOrder);
        return ADDED;
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
}
