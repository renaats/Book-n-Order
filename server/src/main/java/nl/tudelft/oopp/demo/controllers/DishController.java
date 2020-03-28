package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.services.DishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;


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
    @Secured({"ROLE_RESTAURANT", "ROLE_ADMIN"})
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
    @Secured({"ROLE_ADMIN", "ROLE_RESTAURANT"})
    @PostMapping(path = "/addAllergy")
    @ResponseBody
    public void addAllergy(@RequestParam int id, @RequestParam String allergyName) {
        dishService.addAllergy(id, allergyName);
    }

    /**
     * Deletes a dish.
     * @param id dish id.
     * @return Error code.
     */
    @Secured({"ROLE_RESTAURANT", "ROLE_ADMIN"})
    @DeleteMapping(path = "/delete/{dishID}")
    @ResponseBody
    public int deleteDish(@PathVariable(value = "dishID") int id) {
        return dishService.delete(id);
    }

    /**
     * Allows for a multi-parameter Dish search in a DishRepository.
     * @param search The search string in the format "[param1][operation][value],[param2][operation][value],..."
     *               where [operation] is ':', '<', or '>'.
     * @return List of Dish objects that match the search criteria.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/filter")
    @ResponseBody
    public List<Dish> search(@RequestParam String query) {
        return dishService.search(query);
    }
}
