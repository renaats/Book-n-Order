package nl.tudelft.oopp.demo.services;

import java.time.LocalTime;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.repositories.BuildingHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the BuildingHours entity.
 * Receives requests from the BuildingHourController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class BuildingHourService {
    @Autowired
    BuildingHourRepository buildingHourRepository;
    @Autowired
    BuildingService buildingService;

    /**
     * Adds building hours to the database.
     * @param buildingId = the id of the building.
     * @param day = the day of the week in number representation (1 to 7)
     * @param startTimeS = the starting time in milliseconds
     * @param endTimeS = the ending time in milliseconds
     * @return String containing the result of your request.
     */
    public int add(int buildingId, int day, int startTimeS, int endTimeS) {
        Building building = buildingService.find(buildingId);
        if (building == null) {
            return 422;
        }
        if (day < 1 || day > 7) {
            return 425;
        }
        if (endTimeS < startTimeS) {
            return 426;
        }
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return 427;
        }
        BuildingHours buildingHours = new BuildingHours(day, building, LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
        buildingHourRepository.save(buildingHours);
        return 201;
    }

    /**
     * Updates a database attribute.
     * @param id = the building hour id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return message if it passes
     */
    public int update(int id, String attribute, String value) {
        if (!buildingHourRepository.existsById(id)) {
            return 416;
        }
        BuildingHours buildingHours = buildingHourRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "day":
                if (buildingHourRepository.existsByBuilding_IdAndDay(buildingHours.getBuilding().getId(), Integer.parseInt(value))) {
                    return 427;
                }
                buildingHours.setDay(Integer.parseInt(value));
                break;
            case "buildingid":
                int buildingId = Integer.parseInt(value);
                if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, buildingHours.getDay())) {
                    return 427;
                }
                Building building = buildingService.find(buildingId);
                if (building == null) {
                    return 422;
                }
                buildingHours.setBuilding(building);
                break;
            case "starttimes":
                buildingHours.setStartTime(LocalTime.ofSecondOfDay(Integer.parseInt(value)));
                break;
            case "endtimes":
                buildingHours.setEndTime(LocalTime.ofSecondOfDay(Integer.parseInt(value)));
                break;
            default:
                return 412;
        }
        buildingHourRepository.save(buildingHours);
        return 201;
    }

    /**
     * Deletes building hours.
     * @param buildingId = the id of the building
     * @param day = the day of the week
     * @return String to see if your request passed
     */
    public int delete(int buildingId, int day) {
        if (!buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return 404;
        }
        buildingHourRepository.deleteByBuilding_IdAndDay(buildingId, day);
        return 200;
    }

    /**
     * Lists all building hours.
     * @return all building hours
     */
    public List<BuildingHours> all() {
        return buildingHourRepository.findAll();
    }

    /**
     * Finds the hours for a building with the specified id.
     * @param buildingId = the id of the building
     * @param day = the day of the week
     * @return building hours that match the id
     */
    public BuildingHours find(int buildingId, int day) {
        if (!buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return null;
        }
        return buildingHourRepository.findByBuilding_IdAndDay(buildingId, day);
    }
}
