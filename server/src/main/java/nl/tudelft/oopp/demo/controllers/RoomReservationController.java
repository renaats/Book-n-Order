package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.services.RoomReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * Creates server side endpoints and routes requests to the RoomReservationService.
 * Maps all requests that start with "/room_reservation".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/room_reservation")
public class RoomReservationController {
    @Autowired
    private RoomReservationService roomReservationService;

    /**
     * Adds a room reservation.
     * @param roomId = the id of the room associated to the reservation.
     * @param userEmail = the email of the user associated to the reservation.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return Error code
     */
    @Secured(USER)
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRoomReservation(
            @RequestParam String userEmail,
            @RequestParam int roomId,
            @RequestParam long fromTimeMs,
            @RequestParam long toTimeMs) {
        return roomReservationService.add(roomId, userEmail, fromTimeMs, toTimeMs);
    }

    /**
     * Updates a specified attribute for some room reservation.
     * @param id = the id of the room reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return roomReservationService.update(id, attribute, value);
    }


    /**
     * Deletes a room reservation.
     * @param id = the id of the room reservation.
     * @return Error code
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRoomReservation(@RequestParam int id) {
        return roomReservationService.delete(id);
    }

    /**
     * Lists all room reservations.
     * @return Iterable of all room reservations.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<RoomReservation> getAllRoomReservations() {
        return roomReservationService.all();
    }

    /**
     * Finds all past room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of past room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/past")
    public Iterable<RoomReservation> getPastReservations(HttpServletRequest request) {
        return roomReservationService.past(request);
    }

    /**
     * Finds all future room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of future room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/future")
    public Iterable<RoomReservation> getFutureReservations(HttpServletRequest request) {
        return roomReservationService.future(request);
    }

    /**
     * Finds all room reservations for the specified room
     * @param id = the room id.
     * @return a list of future room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/room/{roomId}")
    public String getReservationsForRoom(@PathVariable(value = "roomId") int id) {
        return roomReservationService.forRoom(id);
    }
}