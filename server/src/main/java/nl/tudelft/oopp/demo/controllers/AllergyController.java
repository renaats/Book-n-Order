package nl.tudelft.oopp.demo.controllers;

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
     * @param allergyName = the allergy
     * @return Error code
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewAllergy(
            @RequestParam String allergyName
    ) {
        return allergyService.add(allergyName);
    }

    /**
     * Updates a the name of an allergy.
     * @param id = the allergy id
     * @param name = new name
     * @return Error code
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/update_name")
    @ResponseBody
    public int updateName(@RequestParam int id, @RequestParam String name) {
        return allergyService.update(id, name);
    }

    /**
     * Deletes an allergy.
     * @param id = the id of the allergy
     * @return Error code
     */
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/delete/{allergyID}")
    @ResponseBody
    public int deleteAllergy(@PathVariable(value = "allergyID") int id) {
        return allergyService.delete(id);
    }

    /**
     * Lists all allergies.
     * @return all allergies
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Allergy> getAllAllergies() {
        return allergyService.all();
    }

    /**
     * Finds an allergy with the specified id.
     * @param id = the bike id
     * @return a bike that matches the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{id}")
    @ResponseBody
    public Allergy findAllergy(@PathVariable (value = "id") int id) {
        return allergyService.find(id);
    }
}
