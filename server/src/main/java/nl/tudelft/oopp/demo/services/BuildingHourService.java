package nl.tudelft.oopp.demo.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
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
     * Parses the dateInMilliseconds to be the beginning of a day.
     * @param dateInMilliseconds = the date that is parsed.
     * @return the parsed date
     */
    public static long parse(long dateInMilliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(new Date(dateInMilliseconds))).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Adds building hours to the database.
     * @param buildingId = the id of the building.
     * @param date = the date in milliseconds or the day of the week for regular hours
     * @param startTimeS = the starting time in milliseconds
     * @param endTimeS = the ending time in milliseconds
     * @return String containing the result of your request.
     */
    public int add(int buildingId, long date, int startTimeS, int endTimeS) {
        Building building = buildingService.find(buildingId);
        if (date >= 7) {
            date = parse(date);
        }
        if (building == null) {
            return 422;
        }
        if (date < 1) {
            return 425;
        }
        if (endTimeS < startTimeS) {
            return 426;
        }
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, date)) {
            return 427;
        }
        BuildingHours buildingHours = new BuildingHours(date, building, LocalTime.ofSecondOfDay(startTimeS), LocalTime.ofSecondOfDay(endTimeS));
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
                long dateInMilliseconds = Long.parseLong(value);
                if (dateInMilliseconds > 7) {
                    dateInMilliseconds = parse(dateInMilliseconds);
                }
                if (buildingHourRepository.existsByBuilding_IdAndDay(buildingHours.getBuilding().getId(), dateInMilliseconds)) {
                    return 427;
                }
                buildingHours.setDay(dateInMilliseconds);
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
     * @param dateInMilliseconds = the date in milliseconds
     * @return String to see if your request passed
     */
    public int delete(int buildingId, long dateInMilliseconds) {
        if (dateInMilliseconds > 7) {
            dateInMilliseconds = parse(dateInMilliseconds);
        }
        if (!buildingHourRepository.existsByBuilding_IdAndDay(buildingId, dateInMilliseconds)) {
            return 404;
        }
        buildingHourRepository.deleteByBuilding_IdAndDay(buildingId, dateInMilliseconds);
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
     * @param dateInMilliseconds = the date in milliseconds
     * @return building hours that match the id
     */
    public BuildingHours find(int buildingId, long dateInMilliseconds) {
        dateInMilliseconds = parse(dateInMilliseconds);
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, dateInMilliseconds)) {
            return buildingHourRepository.findByBuilding_IdAndDay(buildingId, dateInMilliseconds);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(dateInMilliseconds));
        long day = calendar.get(Calendar.DAY_OF_WEEK);
        if (buildingHourRepository.existsByBuilding_IdAndDay(buildingId, day)) {
            return buildingHourRepository.findByBuilding_IdAndDay(buildingId, day);
        }
        return null;
    }
}
