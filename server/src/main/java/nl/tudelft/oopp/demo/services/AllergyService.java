package nl.tudelft.oopp.demo.services;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;

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
     * Deletes an allergy.
     * @param allergyName = the name of the allergy
     * @return String to see if your request passed
     */
    public int delete(String allergyName) {
        if (!allergyRepository.existsByAllergyName(allergyName)) {
            return 416;
        }
        allergyRepository.deleteByAllergyName(allergyName);
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
     * @param allergyName = the name of the allergy
     * @return the allergy that matches the provided id
     */
    public Allergy findByAllergyName(String allergyName) {
        return allergyRepository.findByAllergyName(allergyName);
    }
}
