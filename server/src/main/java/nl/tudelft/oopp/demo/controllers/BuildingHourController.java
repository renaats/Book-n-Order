package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.USER;

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
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @param startTimeS = the starting time in seconds.
     * @param endTimeS = the ending time in seconds.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @PostMapping(path = "/add")
    @ResponseBody
    public int addBuildingHours(@RequestParam int buildingId, @RequestParam long date, @RequestParam int startTimeS, @RequestParam int endTimeS) {
        return buildingHourService.add(buildingId, date, startTimeS, endTimeS);
    }

    /**
     * Updates a database attribute.
     * @param id = the building hour id.
     * @param attribute = the attribute that is changed.
     * @param value = the new value of the attribute.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateBuildingHours(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return buildingHourService.update(id, attribute, value);
    }

    /**
     * Deletes building hours.
     * @param buildingId = the id of the building.
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @return Error code.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteBuildingHours(@RequestParam int buildingId, @RequestParam long date) {
        return buildingHourService.delete(buildingId, date);
    }

    /**
     * Lists all building hours in the database.
     * @return all building hours in the database.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BuildingHours> getAllBuildingHours() {
        return buildingHourService.all();
    }

    /**
     * Finds the hours for a building with the specified id.
     * @param buildingId = the id of the building.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return building hours that match the id.
     */
    @Secured(USER)
    @GetMapping(path = "/find/{buildingID}/{day}")
    @ResponseBody
    public BuildingHours findBuildingHours(@PathVariable(value = "buildingID") int buildingId, @PathVariable(value = "day") long dateInMilliseconds) {
        return buildingHourService.find(buildingId, dateInMilliseconds);
    }

    /**
     * Finds the hours for a building with the specified id.
     * @param buildingId = the id of the building.
     * @param day = the day.
     * @return building hours that match the id.
     */
    @Secured({USER, ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/findAdmin/{buildingID}/{day}")
    @ResponseBody
    public BuildingHours findAdminHours(@PathVariable(value = "buildingID") int buildingId, @PathVariable(value = "day") long day) {
        return buildingHourService.findAdmin(buildingId, day);
    }
}
