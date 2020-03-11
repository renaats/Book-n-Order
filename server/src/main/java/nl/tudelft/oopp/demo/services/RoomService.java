package nl.tudelft.oopp.demo.services;

import java.util.Optional;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
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
     * @param nrPeople = the number of people this room fits
     * @param plugs = the number of plugs in this room
     * @return String to see if your request passed
     */
    public int add(String name,
                   String faculty,
                   boolean facultySpecific,
                   boolean screen,
                   boolean projector,
                   int buildingId,
                   int nrPeople,
                   int plugs) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if (optionalBuilding.isEmpty()) {
            return 422;
        }
        Building building = optionalBuilding.get();
        if (building.hasRoomWithName(name)) {
            return 309;
        }

        Room room = new Room();
        room.setBuilding(building);
        room.setName(name);
        room.setFaculty(faculty);
        room.setFacultySpecific(facultySpecific);
        room.setScreen(screen);
        room.setProjector(projector);
        room.setNrPeople(nrPeople);
        room.setPlugs(plugs);
        roomRepository.save(room);
        return 201;
    }

    /**
     * Updates a specified attribute for some room.
     * @param id = the id of the room
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public int update(int id, String attribute, String value) {
        if (roomRepository.findById(id).isEmpty()) {
            return 418;
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
                    return 422;
                }
                Building building = optionalBuilding.get();
                room.setBuilding(building);
                break;
            case "amountofpeople":
                room.setNrPeople(Integer.parseInt(value));
                break;
            case "plugs":
                room.setPlugs(Integer.parseInt(value));
                break;
            default:
                return 412;
        }
        roomRepository.save(room);
        return 200;
    }

    /**
     * Deletes a room.
     * @param id = the id of the room
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!roomRepository.existsById(id)) {
            return 418;
        }
        roomRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all rooms.
     * @return all rooms
     */
    public Iterable<Room> all() {
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
}
