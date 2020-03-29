package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.services.DishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import static nl.tudelft.oopp.demo.config.Constants.*;

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
     * Lists all dishes.
     * @return all dishes.
     */
    @Secured(USER)
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Dish> getAllDishes() {
        return dishService.all();
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
    @Secured({ADMIN, RESTAURANT})
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
