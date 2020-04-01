package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.MENU_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * Adds a menu to a restaurant
     * @param name menu name
     * @param restaurantId restaurant id
     * @return Error code
     */
    public int add(String name, int restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return RESTAURANT_NOT_FOUND;
        }
        Restaurant restaurant = optionalRestaurant.get();

        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), restaurant)) {
            return WRONG_CREDENTIALS;
        }

        Menu menu = new Menu();
        menu.setName(name);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return ADDED;
    }

    /**
     * Deletes a menu
     * @param id menu id
     * @return Error code
     */
    public int delete(int id) {
        if (!menuRepository.existsById(id)) {
            return MENU_NOT_FOUND;
        }
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), menuRepository.getOne(id).getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        menuRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all menus
     * @return all menus
     */
    public List<Menu> all() {
        return menuRepository.findAll();
    }

    /**
     * Finds a menu with a certain id
     * @param id menu id
     * @return menu
     */
    public Menu find(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    /**
     * Finds a menu with a certain name
     * @param name menu name
     * @return menu
     */
    public Menu find(String name) {
        return menuRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
    }
}
