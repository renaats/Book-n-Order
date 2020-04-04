package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.services.MenuService;

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
 * Creates server side endpoints and routes requests to the MenuService.
 * Maps all requests that start with "/menu".
 * Manages access control on a per-method basis.
 */
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
    @Secured({ADMIN, RESTAURANT})
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
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete/{menuID}")
    @ResponseBody
    public int deleteMenu(@PathVariable(value = "menuID") int id) {
        return menuService.delete(id);
    }

    /**
     * Lists all menus.
     * @return all menus
     */
    @Secured(USER)
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Menu> getAllMenus() {
        return menuService.all();
    }

    /**
     * Finds a menu with a certain id
     * @param id menu id
     * @return menu
     */
    @Secured(USER)
    @GetMapping(path = "/find/{id}")
    @ResponseBody
    public Menu find(@PathVariable(value = "id") int id) {
        return menuService.find(id);
    }

    /**
     * Finds a menu with a certain name
     * @param name menu name
     * @return menu
     */
    @Secured(USER)
    @GetMapping(path = "/findName/{name}")
    @ResponseBody
    public Menu findName(@PathVariable(value = "name") String name) {
        return menuService.find(name);
    }

    /**
     * Finds a menu with a certain restaurant id
     * @param restaurantId restaurant id
     * @return menu
     */
    @Secured(USER)
    @GetMapping(path = "/findRestaurant/{restaurantId}")
    @ResponseBody
    public Menu findRestaurant(@PathVariable(value = "restaurantId") int restaurantId) {
        return menuService.findRestaurant(restaurantId);
    }

    /**
     * Changes a menu's name
     * @param menuId menu id
     * @return Error code
     */
    @Secured(USER)
    @PostMapping(path = "/changeName")
    @ResponseBody
    public int changeMenuName(@RequestParam String name, @RequestParam int menuId) {
        return menuService.changeMenuName(menuId, name);
    }
}
