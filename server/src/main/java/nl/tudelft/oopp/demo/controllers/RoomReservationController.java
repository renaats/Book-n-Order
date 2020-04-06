package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.services.RoomReservationService;

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
     * @param request = the Http request that calls this method.
     * @param roomId = the id of the room associated to the reservation.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return Error code
     */
    @Secured(USER)
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public int addNewRoomReservation(
            HttpServletRequest request,
            @RequestParam int roomId,
            @RequestParam long fromTimeMs,
            @RequestParam long toTimeMs) {
        return roomReservationService.add(request, roomId, fromTimeMs, toTimeMs);
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
     * @param request = the Http request that calls this method.
     * @return a list of past room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/past")
    public Iterable<RoomReservation> getPastReservations(HttpServletRequest request) {
        return roomReservationService.past(request);
    }

    /**
     * Finds all future room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of future room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/future")
    public Iterable<RoomReservation> getFutureReservations(HttpServletRequest request) {
        return roomReservationService.future(request);
    }

    /**
     * Finds all past room reservations for some room.
     * @param roomId = the room for which the room reservations are searched.
     * @return a list of past room reservations for this room.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/pastAdmin")
    public Iterable<RoomReservation> getPastReservationsAdmin(@RequestParam int roomId) {
        return roomReservationService.pastForAdmin(roomId);
    }

    /**
     * Finds all future room reservations for some room.
     * @param roomId = the room for which the room reservations are searched.
     * @return a list of future room reservations for this room.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/futureAdmin")
    public Iterable<RoomReservation> getFutureReservationsAdmin(@RequestParam int roomId) {
        return roomReservationService.futureForAdmin(roomId);
    }

    /**
     * Finds all active room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of active room reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/active")
    public Iterable<RoomReservation> getActiveReservations(HttpServletRequest request) {
        return roomReservationService.active(request);
    }

    /**
     * Finds all active reservations for a specific room.
     * @param id = the id of room the reservations are retreived for.
     * @return a list all active room reservations for this room.
     */
    @Secured(USER)
    @GetMapping(path = "/room/{id}")
    public Iterable<RoomReservation> getReservationForRoom(@PathVariable(value = "id") int id) {
        return roomReservationService.findForRoom(id);
    }

    /**
     * Cancels a room reservation if it was made by the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @param roomReservationId = the id of the target reservation.
     * @return an error code.
     */
    @Secured(USER)
    @GetMapping(path = "/cancel/{id}")
    public int cancelReservation(HttpServletRequest request, @PathVariable(value = "id") int roomReservationId) {
        return roomReservationService.cancel(request, roomReservationId);
    }

    /**
     * Finds user for a specific reservation.
     * @param id = id of reservation for which we retrieve user.
     * @return a user that corresponds to this reservation.
     */
    @Secured({ADMIN, BUILDING_ADMIN})
    @GetMapping(path = "/user/{id}")
    public AppUser findForReservation(@PathVariable int id) {
        return roomReservationService.findForReservation(id);
    }
}