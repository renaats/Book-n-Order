package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ROOM_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.specifications.RoomSpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Room entity.
 * Receives requests from the RoomController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Adds a room.
     * @param name = the name of the room
     * @param faculty = the name of the faculty
     * @param facultySpecific = boolean representing room restrictions
     * @param screen = boolean representing the availability of a screen
     * @param projector = boolean representing the availability of a projector
     * @param buildingId = the id of the building of the room
     * @param capacity = the number of people this room fits
     * @param plugs = the number of plugs in this room
     * @return Error code
     */
    public int add(String name,
                   String faculty,
                   boolean facultySpecific,
                   boolean screen,
                   boolean projector,
                   int buildingId,
                   int capacity,
                   int plugs) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }
        Building building = optionalBuilding.get();
        if (building.hasRoomWithName(name)) {
            return DUPLICATE_NAME;
        }

        Room room = new Room();
        room.setBuilding(building);
        room.setName(name);
        room.setFaculty(faculty);
        room.setFacultySpecific(facultySpecific);
        room.setScreen(screen);
        room.setProjector(projector);
        room.setCapacity(capacity);
        room.setPlugs(plugs);
        roomRepository.save(room);
        return ADDED;
    }

    /**
     * Updates a specified attribute for some room.
     * @param id = the id of the room
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return Error code
     */
    public int update(int id, String attribute, String value) {
        if (roomRepository.findById(id).isEmpty()) {
            return ROOM_NOT_FOUND;
        }
        Room room = roomRepository.findById(id).get();

        switch (attribute) {
            case "name":
                room.setName(value);
                break;
            case "faculty":
                room.setFaculty(value);
                break;
            case "facultyspecific":
                room.setFacultySpecific(Boolean.parseBoolean(value));
                break;
            case "screen":
                room.setScreen(Boolean.parseBoolean(value));
                break;
            case "projector":
                room.setProjector(Boolean.parseBoolean(value));
                break;
            case "buildingid":
                int buildingid = Integer.parseInt(value);
                Optional<Building> optionalBuilding = buildingRepository.findById(buildingid);
                if (optionalBuilding.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Building building = optionalBuilding.get();
                room.setBuilding(building);
                break;
            case "amountofpeople":
                room.setCapacity(Integer.parseInt(value));
                break;
            case "plugs":
                room.setPlugs(Integer.parseInt(value));
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        roomRepository.save(room);
        return EXECUTED;
    }

    /**
     * Deletes a room.
     * @param id = the id of the room
     * @return Error code
     */
    public int delete(int id) {
        if (!roomRepository.existsById(id)) {
            return ROOM_NOT_FOUND;
        }
        roomRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all rooms.
     * @return all rooms
     */
    public List<Room> all() {
        return roomRepository.findAll();
    }

    /**
     * Finds a room with the specified id.
     * @param id = the room id
     * @return a room that matches the id
     */
    public Room find(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    /**
     * Finds all room reservations for the room with the specified id.
     * @param id = the room id
     * @return all room reservation for the room that matches the id
     */
    public Set<RoomReservation> reservations(int id) {
        if (roomRepository.findById(id).isEmpty()) {
            return null;
        }
        return roomRepository.findById(id).get().getRoomReservations();
    }

    /**
     * Queries the room repository based on input
     * @param search String consisting of query parameters
     * @return list of rooms that match the query
     */
    public List<Room> search(String search) {
        RoomSpecificationsBuilder builder = new RoomSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Room> spec = builder.build();
        return roomRepository.findAll(spec);
    }
}
