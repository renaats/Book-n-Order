package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.services.FoodOrderService;

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
 * Creates server side endpoints and routes requests to the FoodOrderService.
 * Maps all requests that start with "/food_order".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/food_order")
public class FoodOrderController {
    @Autowired
    FoodOrderService foodOrderService;

    /**
     * Adds a food order.
     * @param request = the Http request that calls this method.
     * @param restaurantId = the id of the restaurant where the food order is placed.
     * @param deliverLocation = the delivery location of the food order.
     * @param deliverTimeMs = the delivery time of the food order in milliseconds.
     * @return String containing the result of your request.
     */
    @Secured(USER)
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public int addNewFoodOrder(HttpServletRequest request, @RequestParam int restaurantId, @RequestParam int deliverLocation,
                               @RequestParam long deliverTimeMs) {
        return foodOrderService.add(request, restaurantId, deliverLocation, deliverTimeMs);
    }

    /**
     * Updates a specified attribute for some food order.
     * @param id = the id of the food order.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return foodOrderService.update(id, attribute, value);
    }


    /**
     * Adds feedback for some food order.
     * @param id = the id of the food order.
     * @return Int containing the result of your request.
     */
    @Secured({USER})
    @PostMapping(path = "/addFeedback")
    @ResponseBody
    public int addFeedback(@RequestParam int id, @RequestParam boolean feedback) {
        return foodOrderService.addFeedback(id, feedback);
    }

    /**
     * Adds a dishOrder to a food order.
     * @param id = the id of the food order.
     * @param name = the name of the dish.
     */
    @Secured(USER)
    @PostMapping(path = "/addDishOrder")
    public int addDishOrder(HttpServletRequest request, @RequestParam int id, @RequestParam String name, @RequestParam int amount) {
        return foodOrderService.addDishOrder(request, id, name, amount);
    }

    /**
     * Gets a dishOrder for some food order.
     * @param id = the id of the food order.
     */
    @Secured(USER)
    @PostMapping(path = "/getDishOrders")
    public List<DishOrder> getDishOrder(HttpServletRequest request, @RequestParam int id) {
        return foodOrderService.getDishOrders(request, id);
    }

    /**
     * Deletes a food order.
     * @param id = the id of the food order.
     * @return String containing the result of your request.
     */
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete/{id}")
    @ResponseBody
    public int deleteFoodOrder(@PathVariable(value = "id") int id) {
        return foodOrderService.delete(id);
    }

    /**
     * Lists all food orders.
     * @return Iterable of all food orders.
     */
    @Secured({ADMIN, RESTAURANT})
    @GetMapping(path = "/all")
    @ResponseBody
    public List<FoodOrder> getAllFoodOrders() {
        return foodOrderService.all();
    }

    /**
     * Finds all past food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of past food orders for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/past")
    public Iterable<FoodOrder> getPastFoodOrders(HttpServletRequest request) {
        return foodOrderService.past(request);
    }

    /**
     * Finds all future food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of future food orders for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/future")
    public Iterable<FoodOrder> getFutureFoodOrders(HttpServletRequest request) {
        return foodOrderService.future(request);
    }

    /**
     * Finds all past food orders for the restaurants of the user that sends the Http request.
     * @param restaurantId = the restaurant for which the food orders are searched.
     * @return a list of past food orders for this user.
     */
    @Secured({ADMIN, RESTAURANT})
    @GetMapping(path = "/pastRestaurant")
    public Iterable<FoodOrder> getPastFoodOrdersRestaurant(@RequestParam int restaurantId) {
        return foodOrderService.pastForRestaurant(restaurantId);
    }

    /**
     * Finds all future food orders for the restaurants of the user that sends the Http request.
     * @param restaurantId = the restaurant for which the food orders are searched.
     * @return a list of future food orders for this user.
     */
    @Secured({ADMIN, RESTAURANT})
    @GetMapping(path = "/futureRestaurant")
    public Iterable<FoodOrder> getFutureFoodOrdersRestaurant(@RequestParam int restaurantId) {
        return foodOrderService.futureForRestaurant(restaurantId);
    }

    /**
     * Finds all active food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of active food orders for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/active")
    public Iterable<FoodOrder> getActiveReservations(HttpServletRequest request) {
        return foodOrderService.active(request);
    }

    /**
     * Cancels a food order if it was made by the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @param foodOrderId = the id of the target order.
     * @return an error code.
     */
    @Secured(USER)
    @GetMapping(path = "/cancel/{id}")
    public int cancelOrder(HttpServletRequest request, @PathVariable(value = "id") int foodOrderId) {
        return foodOrderService.cancel(request, foodOrderId);
    }
}
