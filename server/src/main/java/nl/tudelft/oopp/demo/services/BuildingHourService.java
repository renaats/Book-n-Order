package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_DAY;
import static nl.tudelft.oopp.demo.config.Constants.END_BEFORE_START;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_DAY;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
     * Parses the dateInMilliseconds to be the beginning of a day.
     * @param dateInMilliseconds = the date that is parsed.
     * @return the parsed date
     */
    public static long parse(long dateInMilliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(new Date(dateInMilliseconds))).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * Adds building hours to the database.
     * @param buildingId = the id of the building.
     * @param date = the date in milliseconds or the day of the week for regular hours.
     * @param startTimeS = the starting time in milliseconds.
     * @param endTimeS = the ending time in milliseconds.
     * @return String containing the result of your request.
     */
    public int add(int buildingId, long date, int startTimeS, int endTimeS) {
        Building building = buildingService.find(buildingId);
        if (date > 7) {
            date = parse(date);
        }
        if (building == null) {
            return BUILDING_NOT_FOUND;
        }
        if (date < 1) {
            return INVALID_DAY;
        }
        if (endTimeS < startTimeS) {
            return END_BEFORE_START;
        }
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, date)) {
            return DUPLICATE_DAY;
        }
        BuildingHours buildingHours = new BuildingHours(date, building, LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
        buildingHourRepository.save(buildingHours);
        return ADDED;
    }

    /**
     * Updates a database attribute.
     * @param id = the building hour id.
     * @param attribute = the attribute that is changed.
     * @param value = the new value of the attribute.
     * @return message if it passes.
     */
    public int update(int id, String attribute, String value) {
        if (!buildingHourRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        BuildingHours buildingHours = buildingHourRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "day":
                long dateInMilliseconds = Long.parseLong(value);
                if (dateInMilliseconds > 7) {
                    dateInMilliseconds = parse(dateInMilliseconds);
                }
                if (buildingHourRepository.existsByBuilding_IdAndDay(buildingHours.getBuilding().getId(), dateInMilliseconds)) {
                    return DUPLICATE_DAY;
                }
                buildingHours.setDay(dateInMilliseconds);
                break;
            case "buildingid":
                int buildingId = Integer.parseInt(value);
                if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, buildingHours.getDay())) {
                    return DUPLICATE_DAY;
                }
                Building building = buildingService.find(buildingId);
                if (building == null) {
                    return BUILDING_NOT_FOUND;
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
                return ATTRIBUTE_NOT_FOUND;
        }
        buildingHourRepository.save(buildingHours);
        return EXECUTED;
    }

    /**
     * Deletes building hours.
     * @param buildingId = the id of the building.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return String to see if your request passed.
     */
    public int delete(int buildingId, long dateInMilliseconds) {
        if (dateInMilliseconds > 7) {
            dateInMilliseconds = parse(dateInMilliseconds);
        }
        if (!buildingHourRepository.existsByBuilding_IdAndDay(buildingId, dateInMilliseconds)) {
            return NOT_FOUND;
        }
        buildingHourRepository.deleteByBuilding_IdAndDay(buildingId, dateInMilliseconds);
        return EXECUTED;
    }

    /**
     * Lists all building hours.
     * @return all building hours.
     */
    public List<BuildingHours> all() {
        return buildingHourRepository.findAll();
    }

    /**
     * Finds the hours for a building with the specified id.
     * @param buildingId = the id of the building.
     * @param dateInMilliseconds = the date in milliseconds.
     * @return building hours that match the id.
     */
    public BuildingHours find(int buildingId, long dateInMilliseconds) {
        dateInMilliseconds = parse(dateInMilliseconds);
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, dateInMilliseconds)) {
            return buildingHourRepository.findByBuilding_IdAndDay(buildingId, dateInMilliseconds);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        calendar.setTime(new Date(dateInMilliseconds));
        long day = calendar.get(Calendar.DAY_OF_WEEK);
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return buildingHourRepository.findByBuilding_IdAndDay(buildingId, day);
        }
        return null;
    }

    /**
     * Finds the building hours with the specified id and day.
     * @param buildingId = the id of the building.
     * @param day = the day.
     * @return building hours that match the id.
     */
    public BuildingHours findAdmin(int buildingId, long day) {
        if (!buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return null;
        }
        return buildingHourRepository.findByBuilding_IdAndDay(buildingId, day);
    }
}
