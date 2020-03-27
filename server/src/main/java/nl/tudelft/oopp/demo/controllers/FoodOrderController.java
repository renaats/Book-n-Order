package nl.tudelft.oopp.demo.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.services.FoodOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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
     * @param restaurantId = the id of the restaurant where the food order is placed.
     * @param userEmail = the email of the user associated to the food order.
     * @param deliverLocation = the delivery location of the food order.
     * @param deliverTimeMs = the delivery time of the food order in milliseconds.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_USER")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewFoodOrder(@RequestParam String userEmail, @RequestParam int restaurantId, @RequestParam int deliverLocation,
                               @RequestParam long deliverTimeMs, @RequestParam Set<Integer> dishIds) {
        return foodOrderService.add(restaurantId, userEmail, deliverLocation, deliverTimeMs, dishIds);
    }

    /**
     * Updates a specified attribute for some food order.
     * @param id = the id of the food order.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return foodOrderService.update(id, attribute, value);
    }

    /**
     * Adds a dish to a food order.
     * @param id = the id of the food order
     * @param name = the name of the dish
     */
    //@Secured("ROLE_ADMIN") SHOULD BE UNCOMMENTED WHEN IN PRODUCTION!
    @PostMapping(path = "/addDish")
    @ResponseBody
    public void addDish(@RequestParam int id, @RequestParam String name) {
        foodOrderService.addDish(id, name);
    }

    /**
     * Deletes a food order.
     * @param id = the id of the food order.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteFoodOrder(@PathVariable(value = "id") int id) {
        return foodOrderService.delete(id);
    }

    /**
     * Lists all food orders.
     * @return Iterable of all food orders.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<FoodOrder> getAllFoodOrders() {
        return foodOrderService.all();
    }

    /**
     * Finds all past food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of past food orders for this user.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/past")
    public Iterable<FoodOrder> getPastReservations(HttpServletRequest request) {
        return foodOrderService.past(request);
    }

    /**
     * Finds all future food orders for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of future food orders for this user.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/future")
    public Iterable<FoodOrder> getFutureReservations(HttpServletRequest request) {
        return foodOrderService.future(request);
    }
}
