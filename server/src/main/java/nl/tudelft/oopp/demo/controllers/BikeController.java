package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.services.BikeService;
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

@Repository
@RestController // This means that this class is a Controller
@RequestMapping(path = "/bike") // This means URL's start with /bike (after Application path)
public class BikeController {
    @Autowired
    private BikeService bikeService;

    /**
     * Adds a bike.
     * @param buildingId = the building, where the bike is located
     * @param available = the availability of the bike
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE_ADMIN"})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewBike(
            @RequestParam int buildingId,
            @RequestParam boolean available
    ) {
        return bikeService.add(buildingId, available);
    }

    /**
     * Updates a specified attribute for some bike.
     * @param id = the id of the bike
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return bikeService.update(id, attribute, value);
    }


    /**
     * Deletes a bike.
     * @param id = the id of the bike
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE_ADMIN"})
    @DeleteMapping(path = "/delete/{bikeID}")
    @ResponseBody
    public int deleteBike(@PathVariable(value = "bikeID") int id) {
        return bikeService.delete(id);
    }

    /**
     * Lists all bikes.
     * @return all bikes
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Bike> getAllBikes() {
        return bikeService.all();
    }

    /**
     * Finds a bike with the specified id.
     * @param id = the bike id
     * @return a bike that matches the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{id}")
    @ResponseBody
    public Bike findBike(@PathVariable (value = "id") int id) {
        return bikeService.find(id);
    }

}