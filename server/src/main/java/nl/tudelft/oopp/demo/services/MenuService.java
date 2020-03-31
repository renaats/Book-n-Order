package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.MENU_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Menu entity.
 * Receives requests from the MenuController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;

    /**
     * Adds a menu to a restaurant.
     * @param name menu name.
     * @param restaurantId restaurant id.
     * @return Error code.
     */
    public int add(String name, int restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return RESTAURANT_NOT_FOUND;
        }
        Restaurant restaurant = optionalRestaurant.get();

        Menu menu = new Menu();
        menu.setName(name);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return ADDED;
    }

    /**
     * Deletes the menu with the given Id.
     * @param id menu id.
     * @return Error code.
     */
    public int delete(int id) {
        if (!menuRepository.existsById(id)) {
            return MENU_NOT_FOUND;
        }
        menuRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all menus.
     * @return all menus.
     */
    public List<Menu> all() {
        return menuRepository.findAll();
    }

    /**
     * Finds a menu with a certain id.
     * @param id menu id.
     * @return menu.
     */
    public Menu find(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    /**
     * Updates a specified attribute for some menu.
     * @param id the id of the menu.
     * @param attribute the attribute whose value is changed.
     * @param value the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (menuRepository.findById(id).isEmpty()) {
            return MENU_NOT_FOUND;
        }
        Menu menu = menuRepository.findById(id).get();
        switch (attribute) {
            case "restaurant":
                int restaurantId = Integer.parseInt(value);
                Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
                if (optionalRestaurant.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Restaurant restaurant = optionalRestaurant.get();
                menu.setRestaurant(restaurant);
                break;
            case "name":
                menu.setName(value);
                break;
            case "dishAdd":
                int dishId = Integer.parseInt(value);
                Optional<Dish> optionalDish = dishRepository.findById(dishId);
                if (optionalDish.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Dish dish = optionalDish.get();
                menu.addDish(dish);
                break;
            case "dishDelete":
                int dishIdDelete = Integer.parseInt(value);
                Optional<Dish> optionalDishDelete = dishRepository.findById(dishIdDelete);
                if (optionalDishDelete.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Dish dishDelete = optionalDishDelete.get();
                menu.deleteDish(dishDelete);
                break;
            case "dishDeleteAll":
                menu.deleteAllDish();
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        menuRepository.save(menu);
        return EXECUTED;
    }
}
