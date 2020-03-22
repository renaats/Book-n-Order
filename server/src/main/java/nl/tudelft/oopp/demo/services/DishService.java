package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Dish entity.
 * Receives requests from the DishController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private FoodOrderRepository foodOrderRepository;

    /**
     * Adds a dish
     * @param name dish name
     * @param menuId menu id
     * @return Error code
     */
    public int add(String name, int menuId) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty()) {
            return 429;
        }
        Menu menu = optionalMenu.get();

        Dish dish = new Dish();
        dish.setName(name);
        dish.setMenu(menu);
        dishRepository.save(dish);
        return 201;
    }

    /**
     * Deletes a dish
     * @param id dish id
     * @return Error code
     */
    public int delete(int id) {
        if (!dishRepository.existsById(id)) {
            return 430;
        }
        dishRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all dishes
     * @return all menus
     */
    public List<Dish> all() {
        return dishRepository.findAll();
    }

    /**
     * Finds a dish with a certain id
     * @param id menu id
     * @return menu
     */
    public Dish find(int id) {
        return dishRepository.findById(id).orElse(null);
    }
}
