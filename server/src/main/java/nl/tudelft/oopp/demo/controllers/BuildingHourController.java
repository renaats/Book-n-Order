package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.services.BuildingHourService;

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
 * Creates server side endpoints and routes requests to the BuildingHourService.
 * Maps all requests that start with "/building_hours".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/building_hours")
public class BuildingHourController {
    @Autowired
    private BuildingHourService buildingHourService;

    /**
     * Adds building hours to the database.
     * @param buildingId = the id of the building.
     * @param day = the day of the week in number representation (1 to 7)
     * @param startTimeS = the starting time in seconds
     * @param endTimeS = the ending time in seconds
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addBuildingHours(@RequestParam int buildingId, @RequestParam int day, @RequestParam int startTimeS, @RequestParam int endTimeS) {
        return buildingHourService.add(buildingId, day, startTimeS, endTimeS);
    }

    /**
     * Updates a database attribute.
     * @param id = the building hour id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateBuildingHours(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return buildingHourService.update(id, attribute, value);
    }

    /**
     * Deletes building hours.
     * @param buildingId = the id of the building
     * @param day = the day of the week
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteBuildingHours(@RequestParam int buildingId, @RequestParam int day) {
        return buildingHourService.delete(buildingId, day);
    }

    /**
     * Lists all building hours in the database.
     * @return all building hours in the database
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BuildingHours> getAllBuildingHours() {
        return buildingHourService.all();
    }

    /**
     * Finds the hours for a building with the specified id.
     * @param buildingId = the id of the building
     * @param day = the day of the week
     * @return building hours that match the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{buildingID}/{day}")
    @ResponseBody
    public BuildingHours findBuildingHours(@PathVariable(value = "buildingID") int buildingId, @PathVariable(value = "day") int day) {
        return buildingHourService.find(buildingId, day);
    }
}
