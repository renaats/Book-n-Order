package nl.tudelft.oopp.demo.services;

import java.util.Date;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Adds a foodOrder.
     * @param restaurantId = the id of the restaurant associated to the food order.
     * @param userEmail = the email of the user associated to the food order.
     * @param deliverLocation = the building where the food order needs to be delivered.
     * @param deliverTimeMs = the deliver time of the food order.
     * @return String containing the result of your request.
     */
    public int add(int restaurantId, String userEmail, int deliverLocation, long deliverTimeMs) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return 428;
        }
        Restaurant restaurant = optionalRestaurant.get();

        Optional<AppUser> optionalUser = userRepository.findById(userEmail);
        if (optionalUser.isEmpty()) {
            return 404;
        }
        AppUser appUser = optionalUser.get();

        Optional<Building> optionalDeliveryLocation = buildingRepository.findById(deliverLocation);
        if (optionalDeliveryLocation.isEmpty()) {
            return 422;
        }
        Building deliveryLocation = optionalDeliveryLocation.get();

        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setRestaurant(restaurant);
        foodOrder.setAppUser(appUser);
        foodOrder.setDeliveryLocation(deliveryLocation);
        foodOrder.setDeliveryTime(new Date(deliverTimeMs));
        foodOrderRepository.save(foodOrder);
        return 201;
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
            return 421;
        }
        FoodOrder foodOrder = foodOrderRepository.findById(id).get();
        switch (attribute) {
            case "deliveryLocation":
                int buildingId = Integer.parseInt(value);
                Optional<Building> optionalDeliveryLocation = buildingRepository.findById(buildingId);
                if (optionalDeliveryLocation.isEmpty()) {
                    return 422;
                }
                Building deliveryLocation = optionalDeliveryLocation.get();
                foodOrder.setDeliveryLocation(deliveryLocation);
                break;
            case "deliveryTime":
                foodOrder.setDeliveryTime(new Date(Integer.parseInt(value)));
                break;
            case "userEmail":
                Optional<AppUser> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return 419;
                }
                AppUser appUser = optionalUser.get();
                foodOrder.setAppUser(appUser);
                break;
            default:
                return 420;
        }
        foodOrderRepository.save(foodOrder);
        return 200;
    }

    /**
     * Deletes a food order.
     * @param id = the id of the food order.
     * @return String containing the result of your request.
     */
    public int delete(int id) {
        if (!foodOrderRepository.existsById(id)) {
            return 421;
        }
        foodOrderRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all food orders.
     * @return Iterable of all food orders.
     */
    public Iterable<FoodOrder> all() {
        return foodOrderRepository.findAll();
    }

    /**
     * Finds a food order with the specified id.
     * @param id = the food order id
     * @return a food order that matches the id
     */
    public FoodOrder find(int id) {
        return foodOrderRepository.findById(id).orElse(null);
    }
}
