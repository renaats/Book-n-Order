package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.services.RoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Repository
@RestController
@RequestMapping(path = "/room_reservation")
public class RoomReservationController {
    @Autowired
    RoomReservationService roomReservationService;

    /**
     * Adds a room reservation.
     * @param roomId = the id of the room associated to the reservation
     * @param userEmail = the email of the user associated to the reservation
     * @param fromTimeMs = the starting time of the reservation
     * @param toTimeMs = the ending time of the reservation
     * @return String to see if your request passed
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public String addNewRoomReservation(
            @RequestParam String userEmail,
            @RequestParam int roomId,
            @RequestParam long fromTimeMs,
            @RequestParam long toTimeMs) {
        return roomReservationService.add(roomId, userEmail, fromTimeMs, toTimeMs);
    }

    /**
     * Updates a specified attribute for some room reservation.
     * @param id = the id of the room reservation
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    @PostMapping(path = "/update")
    @ResponseBody
    public String updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return roomReservationService.update(id, attribute, value);
    }


    /**
     * Deletes a room reservation.
     * @param id = the id of the room reservation
     * @return String to see if your request passed
     */
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public String deleteRoomReservation(@RequestParam int id) {
        return roomReservationService.delete(id);
    }

    /**
     * Lists all room reservations.
     * @return all room reservations
     */
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<RoomReservation> getAllRoomReservations() {
        return roomReservationService.all();
    }

}