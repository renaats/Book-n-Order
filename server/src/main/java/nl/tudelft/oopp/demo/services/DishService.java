package nl.tudelft.oopp.demo.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
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
    private AllergyRepository allergyRepository;

    /**
     * Adds a dish.
     * @param name dish name.
     * @param menuId menu id.
     * @return Error code.
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
        dish.setAllergies(new HashSet<>());
        dishRepository.save(dish);
        return 201;
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
            return 416;
        }
        Dish dish = dishRepository.findById(id).get();
        switch (attribute) {
            case "name":
                dish.setName(value);
                break;
            case "menu":
                int menuid = Integer.parseInt(value);
                Optional<Menu> optionalMenu = menuRepository.findById(menuid);
                if (optionalMenu.isEmpty()) {
                    return 416;
                }
                Menu menu = optionalMenu.get();
                dish.setMenu(menu);
                break;
            default:
                return 412;
        }
        dishRepository.save(dish);
        return 200;
    }

    /**
     * Adds an allergy to a dish. If the allergy does not exist, it is created.
     * @param id = the id of the dish.
     * @param allergyName = the name of the allergy.
     */
    public int addAllergy(int id, String allergyName) {
        if (!dishRepository.existsById(id)) {
            return 416;
        }
        Dish dish = dishRepository.getOne(id);
        Allergy allergy;
        if (!allergyRepository.existsByAllergyName(allergyName)) {
            allergy = new Allergy();
            allergy.setAllergyName(allergyName);
            allergyRepository.save(allergy);
        }
        allergy = allergyRepository.findByAllergyName(allergyName);
        dish.addAllergy(allergy);
        dishRepository.save(dish);
        return 201;
    }

    /**
     * Deletes a dish.
     * @param id dish id.
     * @return Error code.
     */
    public int delete(int id) {
        if (!dishRepository.existsById(id)) {
            return 430;
        }
        dishRepository.deleteById(id);
        return 200;
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
}