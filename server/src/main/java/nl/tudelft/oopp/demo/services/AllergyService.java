package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Allergy entity.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class AllergyService {
    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private DishRepository dishRepository;

    /**
     * Adds an allergy.
     * @param allergyName = the name or type of the allergy
     * @param dishId = the dish associated with the allergy
     * @return String to see if your request passed
     */
    public int add(String allergyName, int dishId) {
        Optional<Dish> optionalDish = dishRepository.findById(dishId);
        if (optionalDish.isEmpty()) {
            return 416;
        }

        Allergy allergy = new Allergy();
        allergy.setAllergyName(allergyName);
        allergy.setDish(optionalDish.get());
        allergyRepository.save(allergy);
        return 201;
    }

    /**
     * Updates a specified attribute for some allergy.
     * @param id = the id of the allergy
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public int update(int id, String attribute, String value) {
        if (allergyRepository.findById(id).isEmpty()) {
            return 416;
        }
        Allergy allergy = allergyRepository.findById(id).get();

        switch (attribute) {
            case "allergyName":
                allergy.setAllergyName(value);
                break;
            case "dish":
                int dishId = Integer.parseInt(value);
                Optional<Dish> optionalDish = dishRepository.findById(dishId);
                if (optionalDish.isEmpty()) {
                    return 403;
                }
                Dish dish = optionalDish.get();
                allergy.setDish(dish);
                break;
            default:
                return 412;
        }
        allergyRepository.save(allergy);
        return 200;
    }

    /**
     * Deletes an allergy.
     * @param id = the id of the allergy
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!allergyRepository.existsById(id)) {
            return 404;
        }
        allergyRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all allergies.
     * @return all allergies
     */
    public List<Allergy> all() {
        return allergyRepository.findAll();
    }

    /**
     * Finds an allergy with the specified id.
     * @param id = the allergy id
     * @return a allergy that matches the id
     */
    public Allergy find(int id) {
        return allergyRepository.findById(id).orElse(null);
    }
}
