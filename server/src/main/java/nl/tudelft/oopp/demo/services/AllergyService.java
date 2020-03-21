package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;

import nl.tudelft.oopp.demo.repositories.RoleRepository;
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
     * @param allergyName = the name of the allergy
     * @return int to see if your request passed
     */
    public int add(String allergyName) {
        Allergy allergy = new Allergy();
        allergy.setAllergyName(allergyName);
        allergyRepository.save(allergy);
        return 201;
    }

    /**
     * Updates an allergy name.
     * @param id = the allergy id
     * @param allergyName = the new allergy name
     * @return message if it passes
     */
    public int update(int id, String allergyName) {
        if (!allergyRepository.existsById(id)) {
            return 416;
        }
        Allergy allergy = allergyRepository.getOne(id);
        String old = allergy.getAllergyName();
        allergy.setAllergyName(allergyName);
        allergyRepository.save(allergy);
        return 201;
    }

    /**
     * Deletes an allergy.
     * @param id = the id of the allergy
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!allergyRepository.existsById(id)) {
            return 416;
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
     * @param id = the id of the allergy
     * @return the allergy that matches the provided id
     */
    public Allergy find(int id) {
        return allergyRepository.findById(id).orElse(null);
    }
}
