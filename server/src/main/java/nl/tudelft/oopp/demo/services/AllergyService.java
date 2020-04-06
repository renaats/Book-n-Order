package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.specifications.AllergySpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
        return ADDED;
    }

    /**
     * Deletes an allergy.
     * @param allergyName = the name of the allergy
     * @return String to see if your request passed
     */
    public int delete(String allergyName) {
        if (!allergyRepository.existsByAllergyName(allergyName)) {
            return ID_NOT_FOUND;
        }
        allergyRepository.deleteByAllergyName(allergyName);
        return EXECUTED;
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

    /**
     * Finds all allergies for a dish the specified id.
     * @param dishId = the id of the dish
     * @return the allergies that match the provided dish id
     */
    public List<Allergy> findAllByDishId(int dishId) {
        return allergyRepository.findAllByDishesId(dishId);
    }

    /**
     * Queries the allergy repository based on input
     * @param search String consisting of query parameters
     * @return list of allergies that match the query
     */
    public List<Allergy> search(String search) {
        AllergySpecificationsBuilder builder = new AllergySpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Allergy> spec = builder.build();
        return allergyRepository.findAll(spec);
    }
}
