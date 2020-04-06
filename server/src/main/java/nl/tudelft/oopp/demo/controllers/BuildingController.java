package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.BuildingService;

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
 * Creates server side endpoints and routes requests to the BuildingService.
 * Maps all requests that start with "/building".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    /**
     * Adds a building to the database.
     * @param name = the name of the new building.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addNewBuilding(@RequestParam String name, @RequestParam String street, @RequestParam String faculty, @RequestParam int houseNumber) {
        return buildingService.add(name, street, faculty, houseNumber);
    }

    /**
     * Deletes a building from the database.
     * @param id = the id of the existing building to be deleted.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @DeleteMapping(path = "/delete/{buildingID}")
    @ResponseBody
    public int deleteBuilding(@PathVariable(value = "buildingID") int id) {
        return buildingService.delete(id);
    }

    /**
     * Updates an attribute of a building in the database.
     * @param id = the id of the building whose value is to be updated.
     * @param attribute = the attribute whose value is updated.
     * @param value = the new value.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateBuilding(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return buildingService.update(id, attribute, value);
    }

    /**
     * Lists all buildings in the database.
     * @return Iterable of all buildings in the database.
     */
    @Secured(USER)
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Building> getAllBuildings() {
        return buildingService.all();
    }
    
    /**
     * Retrieves the building with the specified id.
     * @param id = the id of the building.
     * @return Building that matches the id.
     */
    @Secured(USER)
    @GetMapping(path = "/find/{buildingID}")
    @ResponseBody
    public Building findBuilding(@PathVariable(value = "buildingID") int id) {
        return buildingService.find(id);
    }

    /**
     * Finds a building with the specified name.
     * @param name = the name of the building
     * @return a building that matches the name
     */
    @Secured(USER)
    @GetMapping(path = "/findName/{name}")
    @ResponseBody
    public Building findBuildingByName(@PathVariable(value = "name") String name) {
        return buildingService.find(name);
    }

    /**
     * Return all rooms that are in the building with the specified id.
     * @param id = the id of the building.
     * @return Set of all rooms in the retrieved building.
     */
    @Secured(USER)
    @GetMapping(path = "/rooms/{buildingId}")
    @ResponseBody
    public Set<Room> getRooms(@PathVariable(value = "buildingId") int id) {
        return buildingService.rooms(id);
    }
}
