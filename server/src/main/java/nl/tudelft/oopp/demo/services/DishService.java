package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DISH_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.MENU_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.specifications.DishSpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AllergyRepository allergyRepository;

    /**
     * Adds a dish.
     * @param name = dish name.
     * @param menuId = menu id.
     * @param price the price of the dish.
     * @param description the description of the dish.
     * @param image the image of the dish.
     * @return Error code.
     */
    public int add(String name, int menuId, int price, String description, String image) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty()) {
            return MENU_NOT_FOUND;
        }
        Menu menu = optionalMenu.get();
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), menu.getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        if (dishRepository.existsByNameAndMenuId(name, menuId)) {
            return DUPLICATE_NAME;
        }

        Dish dish = new Dish(name, menu, price, description, image);
        dish.setAllergies(new HashSet<>());
        dishRepository.save(dish);
        return ADDED;
    }

    /**
     * Updates a specified attribute for some dish.
     * @param id = the id of the dish.
     * @param attribute = the attribute that is changed.
     * @param value = the new value of the attribute.
     * @return String to see if your request passed.
     */
    public int update(int id, String attribute, String value) {
        if (dishRepository.findById(id).isEmpty()) {
            return ID_NOT_FOUND;
        }
        Dish dish = dishRepository.findById(id).get();
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), dish.getMenu().getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        switch (attribute) {
            case "name":
                if (dishRepository.existsByNameAndMenuId(value, dish.getMenu().getId())) {
                    return DUPLICATE_NAME;
                }
                dish.setName(value);
                break;
            case "menu":
                int menuid = Integer.parseInt(value);
                Optional<Menu> optionalMenu = menuRepository.findById(menuid);
                if (optionalMenu.isEmpty()) {
                    return ID_NOT_FOUND;
                }
                Menu menu = optionalMenu.get();
                dish.setMenu(menu);
                break;
            case "price":
                dish.setPrice(Integer.parseInt(value));
                break;
            case "description":
                dish.setDescription(value);
                break;
            case "image":
                dish.setImage(value);
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        dishRepository.save(dish);
        return EXECUTED;
    }

    /**
     * Adds an allergy to a dish. If the allergy does not exist, it is created.
     * @param id = the id of the dish.
     * @param allergyName = the name of the allergy.
     */
    public int addAllergy(int id, String allergyName) {
        if (!dishRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        Dish dish = dishRepository.getOne(id);
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), dish.getMenu().getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        Allergy allergy;
        if (!allergyRepository.existsByAllergyName(allergyName)) {
            allergy = new Allergy();
            allergy.setAllergyName(allergyName);
            allergyRepository.save(allergy);
        }
        allergy = allergyRepository.findByAllergyName(allergyName);
        dish.addAllergy(allergy);
        dishRepository.save(dish);
        return ADDED;
    }

    /**
     * Removes an allergy from a dish.
     * @param id = the id of the dish.
     * @param allergyName = the name of the allergy.
     */
    public int removeAllergy(int id, String allergyName) {
        if (!dishRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        Dish dish = dishRepository.getOne(id);
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), dish.getMenu().getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        Allergy allergy;
        if (!allergyRepository.existsByAllergyName(allergyName)) {
            return NOT_FOUND;
        }
        allergy = allergyRepository.findByAllergyName(allergyName);
        dish.removeAllergy(allergy);
        dishRepository.save(dish);
        return EXECUTED;
    }

    /**
     * Deletes a dish.
     * @param id = the id of the dish.
     * @return Error code.
     */
    public int delete(int id) {
        if (!dishRepository.existsById(id)) {
            return DISH_NOT_FOUND;
        }
        if (RestaurantService.noPermissions(SecurityContextHolder.getContext(), dishRepository.getOne(id).getMenu().getRestaurant())) {
            return WRONG_CREDENTIALS;
        }
        dishRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all dishes.
     * @return all menus.
     */
    public List<Dish> all() {
        return dishRepository.findAll();
    }

    /**
     * Finds a dish with a certain id.
     * @param id menu id.
     * @return menu.
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
        search = URLDecoder.decode(search, StandardCharsets.UTF_8);
        DishSpecificationsBuilder builder = new DishSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Dish> spec = builder.build();
        return dishRepository.findAll(spec);
    }

    /**
     * Lists all dishes from a specific menu.
     * @param menuId the id of the menu
     * @return all dishes from the menu
     */
    public List<Dish> findByMenu(int menuId) {
        return dishRepository.findAllByMenuId(menuId);
    }
}

