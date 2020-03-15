package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.services.MenuService;
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
@RequestMapping(path = "/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    /**
     * Adds a menu
     * @param name menu name
     * @param restaurantId restaurant id
     * @return Error code
     */
    @Secured({"Missing argumentSTAURANT_OWNER"})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addNewMenu(@RequestParam String name, @RequestParam int restaurantId) {
        return menuService.add(name, restaurantId);
    }

    /**
     * Deletes a menu
     * @param id menu id
     * @return Error code
     */
    @Secured({"ROLE_RESTAURANT"})
    @DeleteMapping(path = "/delete/{menuID}")
    @ResponseBody
    public int deleteMenu(@PathVariable(value = "menuID") int id) {
        return menuService.delete(id);
    }
}
