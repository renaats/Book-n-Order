package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.services.DishService;
import nl.tudelft.oopp.demo.specifications.DishSpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * Adds a dish
     * @param name dish name
     * @param menuId menu id
     * @return Error code
     */
    @Secured({"RESTAURANT_OWNER", "ROLE_ADMIN"})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addNewDish(@RequestParam String name, @RequestParam int menuId) {
        return dishService.add(name, menuId);
    }

    /**
     * Deletes a dish
     * @param id dish id
     * @return Error code
     */
    @Secured({"RESTAURANT_OWNER", "ROLE_ADMIN"})
    @DeleteMapping(path = "/delete/{menuID}")
    @ResponseBody
    public int deleteDish(@PathVariable(value = "dishID") int id) {
        return dishService.delete(id);
    }

    /**
     * Allows for a multi-parameter Dish search in a DishRepository.
     * @param search The search string in the format "[param1][operation][value],[param2][operation][value],..."
     *               where [operation] is ':', '<', or '>'.
     * @param repository The repository to search in.
     * @return List of Dish objects that match the search criteria.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dishes")
    @ResponseBody
    public List<Dish> search(@RequestParam(value = "search") String search) {
        return dishService.search(search);
    }
}
