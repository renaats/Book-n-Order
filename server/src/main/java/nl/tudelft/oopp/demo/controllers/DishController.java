package nl.tudelft.oopp.demo.controllers;

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
}
