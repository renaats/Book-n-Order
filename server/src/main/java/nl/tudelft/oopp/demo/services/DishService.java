package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.specifications.DishSpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    /**
     * Queries the dish repository based on input
     * @param search String consisting of query parameters
     * @return list of dishes that match the query
     */
    public List<Dish> search(String search) {
        DishSpecificationsBuilder builder = new DishSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Dish> spec = builder.build();
        return dishRepository.findAll(spec);
    }
}
