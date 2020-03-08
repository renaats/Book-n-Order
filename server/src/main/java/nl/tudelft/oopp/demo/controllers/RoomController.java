package nl.tudelft.oopp.demo.controllers;

import java.util.Set;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.services.RoomService;
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
@RestController
@RequestMapping(path = "/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    /**
     * Adds a room.
     * @param name = the name of the room
     * @param faculty = the name of the faculty
     * @param buildingId = the id of the building of the room
     * @param facultySpecific = boolean representing room restrictions
     * @param screen = boolean representing the availability of a screen
     * @param projector = boolean representing the availability of a projector
     * @param nrPeople = the number of people this room fits
     * @param plugs = the number of plugs in this room
     * @return String to see if your request passed
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
            @RequestParam int nrPeople,
            @RequestParam int plugs) {
        return roomService.add(name, faculty, facultySpecific, screen, projector, buildingId, nrPeople, plugs);
    }

    /**
     * Updates a specified attribute for some room.
     * @param id = the id of the room
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return roomService.update(id, attribute, value);
    }

    /**
     * Deletes a room.
     * @param id = the id of the room
     * @return String to see if your request passed
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @DeleteMapping(path = "/delete/{roomID}")
    @ResponseBody
    public int deleteRoom(@PathVariable(value = "roomID") int id) {
        return roomService.delete(id);
    }

    /**
     * Lists all rooms.
     * @return all rooms
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Room> getAllRooms() {
        return roomService.all();
    }

    /**
     * Finds a room with the specified id.
     * @param id = the room id
     * @return a room that matches the id
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/find/{roomId}")
    @ResponseBody
    public Room findRoom(@PathVariable (value = "roomId") int id) {
        return roomService.find(id);
    }

    /**
     * Finds all room reservations for the room with the specified id.
     * @param id = the room id
     * @return all room reservation for the room that matches the id
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @GetMapping(path = "/reservations/{roomId}")
    @ResponseBody
    public Set<RoomReservation> findReservations(@PathVariable (value = "roomId") int id) {
        return roomService.reservations(id);
    }

}