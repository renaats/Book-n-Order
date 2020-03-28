package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;

import nl.tudelft.oopp.demo.services.DishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates server side endpoints and routes requests to the DishService.
 * Maps all requests that start with "/dish".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/dish")
public class DishController {
    @Autowired
    DishService dishService;

    /**
     * Adds a dish.
     * @param name dish name.
     * @param menuId menu id.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addNewDish(@RequestParam String name, @RequestParam int menuId) {
        return dishService.add(name, menuId);
    }

    /**
     * Adds an allergy to a dish. If the allergy does not exist, it is created.
     * @param id = the id of the dish.
     * @param allergyName = the name of the allergy.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/addAllergy")
    @ResponseBody
    public void addAllergy(@RequestParam int id, @RequestParam String allergyName) {
        dishService.addAllergy(id, allergyName);
    }

    /**
     * Updates a specified attribute for a dish.
     * @param id = the id of the food order.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return dishService.update(id, attribute, value);
    }

    /**
     * Deletes a dish.
     * @param id dish id.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete/{dishID}")
    @ResponseBody
    public int deleteDish(@PathVariable(value = "dishID") int id) {
        return dishService.delete(id);
    }
}
