package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.services.AllergyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates server side endpoints and routes requests to the AllergyService.
 * Maps all requests that start with "/allergy".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController // This means that this class is a Controller
@RequestMapping(path = "/allergy") // This means URL's start with /allergy (after Application path)
public class AllergyController {
    @Autowired
    private AllergyService allergyService;

    /**
     * Adds an allergy.
     * @param allergyName = the name of the allergy.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addAllergy(@RequestParam String allergyName) {
        return allergyService.add(allergyName);
    }

    /**
     * Deletes an allergy.
     * @param name = the name of the allergy.
     * @return Error code.
     */
    @Secured({ADMIN, RESTAURANT})
    @DeleteMapping(path = "/delete/{allergyName}")
    @ResponseBody
    public int deleteAllergy(@PathVariable(value = "allergyName") String name) {
        return allergyService.delete(name);
    }

    /**
     * Lists all allergies.
     * @return all allergies.
     */
    @Secured(USER)
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Allergy> getAllAllergies() {
        return allergyService.all();
    }

    /**
     * Finds an allergy with the specified id.
     * @param name = the name of the allergy.
     * @return an allergy that matches the id.
     */
    @Secured(USER)
    @GetMapping(path = "/find/{name}")
    @ResponseBody
    public Allergy findAllergy(@PathVariable (value = "name") String name) {
        return allergyService.findByAllergyName(name);
    }

    /**
     * Finds all allergies for a dish the specified id.
     * @param dishId = the id of the dish
     * @return the allergies that match the provided dish id
     */
    @Secured(USER)
    @GetMapping(path = "/dish/{dishId}")
    @ResponseBody
    public List<Allergy> allergiesForDish(@PathVariable(value = "dishId") int dishId) {
        return allergyService.findAllByDishId(dishId);
    }

    /**
     * Allows for a multi-parameter Allergy search in a AllergyRepository.
     * @param query The search string in the format "[param1][operation][value],[param2][operation][value],..."
     *               where [operation] is ':', '<', or '>'.
     * @return List of Room objects that match the search criteria.
     */
    @Secured(USER)
    @GetMapping(path = "/filter")
    @ResponseBody
    public List<Allergy> search(@RequestParam String query) {
        return allergyService.search(query);
    }
}