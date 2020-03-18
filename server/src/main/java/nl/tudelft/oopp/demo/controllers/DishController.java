package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.DishService;
import nl.tudelft.oopp.demo.specifications.DishSpecificationsBuilder;
import nl.tudelft.oopp.demo.specifications.RoomSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dishes")
    @ResponseBody
    public static List<Dish> search(@RequestParam(value = "search") String search, DishRepository repository) {
        DishSpecificationsBuilder builder = new DishSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Dish> spec = builder.build();
        return repository.findAll(spec);
    }
}
