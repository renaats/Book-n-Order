package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.services.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
     * @param deliverTimeMs = the delivery time of the food order.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_USER")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewBikeReservation(
            @RequestParam String userEmail,
            @RequestParam int restaurantId,
            @RequestParam int deliverLocation,
            @RequestParam long deliverTimeMs) {
        return foodOrderService.add(restaurantId, userEmail, deliverLocation, deliverTimeMs);
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
     * Deletes a food order.
     * @param id = the id of the food order.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteFoodOrder(@RequestParam int id) {
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
}
