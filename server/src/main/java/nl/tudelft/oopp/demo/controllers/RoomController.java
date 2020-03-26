package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.RoomService;
import nl.tudelft.oopp.demo.specifications.RoomSpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates server side endpoints and routes requests to the RoomService.
 * Maps all requests that start with "/room".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * Adds a room to the database.
     * @param name = the name of the new room.
     * @param faculty = the name of the faculty.
     * @param buildingId = the id of the building of the room.
     * @param facultySpecific = boolean representing room restrictions.
     * @param screen = boolean representing the availability of a screen.
     * @param projector = boolean representing the availability of a projector.
     * @param capacity = the number of people this room fits.
     * @param plugs = the number of plugs in this room.
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRoom(
            @RequestParam String name,
            @RequestParam String faculty,
            @RequestParam boolean facultySpecific,
            @RequestParam boolean screen,
            @RequestParam boolean projector,
            @RequestParam int buildingId,
            @RequestParam int capacity,
            @RequestParam int plugs) {
        return roomService.add(name, faculty, facultySpecific, screen, projector, buildingId, capacity, plugs);
    }

    /**
     * Updates a specified attribute for given room.
     * @param id = the id of the room.
     * @param attribute = the attribute that is to be changed.
     * @param value = the new value of the attribute.
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return roomService.update(id, attribute, value);
    }

    /**
     * Deletes a room.
     * @param id = the id of the room to be deleted.
     * @return Error code
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @DeleteMapping(path = "/delete/{roomID}")
    @ResponseBody
    public int deleteRoom(@PathVariable(value = "roomID") int id) {
        return roomService.delete(id);
    }

    /**
     * Lists all rooms.
     * @return Iterable of all rooms.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Room> getAllRooms() {
        return roomService.all();
    }

    /**
     * Retrieves a room with the specified id.
     * @param id = the room id.
     * @return Room that matches the id.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{roomId}")
    @ResponseBody
    public Room findRoom(@PathVariable (value = "roomId") int id) {
        return roomService.find(id);
    }

    /**
     * Retrieves all room reservations for the room with the specified id.
     * @param id = the room id.
     * @return Set of all room reservations for the room that matches the id.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @GetMapping(path = "/reservations/{roomId}")
    @ResponseBody
    public Set<RoomReservation> findReservations(@PathVariable (value = "roomId") int id) {
        return roomService.reservations(id);
    }

    /**
     * Allows for a multi-parameter Room search in a RoomRepository.
     * @param search The search string in the format "[param1][operation][value],[param2][operation][value],..."
     *               where [operation] is ':', '<', or '>'.
     * @return List of Room objects that match the search criteria.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @GetMapping(path = "/filter")
    @ResponseBody
    public List<Room> search(@RequestParam(value = "query") String query) {
        return roomService.search(query);
    }
}