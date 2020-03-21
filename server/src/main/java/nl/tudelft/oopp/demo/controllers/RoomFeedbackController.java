package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeFeedback;
import nl.tudelft.oopp.demo.entities.RoomFeedback;
import nl.tudelft.oopp.demo.services.RoomFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates server side endpoints and routes requests to the RoomFeedbackService.
 * Maps all requests that start with "/room_feedback".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/room_feedback")
public class RoomFeedbackController {
    @Autowired
    private RoomFeedbackService roomFeedbackService;

    /**
     * Adds a room feedback.
     * @param clientEmail = the email of the feedback sender.
     * @param recipientEmail = the email of the feedback recipient.
     * @param roomReservationId = the id of the room reservation being reviewed.
     * @param time = time when the feedback was sent.
     * @param feedback = the feedback text.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_USER")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewRoomFeedback(
            @RequestParam String clientEmail,
            @RequestParam String recipientEmail,
            @RequestParam int roomReservationId,
            @RequestParam long time,
            @RequestParam String feedback) {
        return roomFeedbackService.add(clientEmail, recipientEmail, roomReservationId, time, feedback);
    }

    /**
     * Updates a specified attribute for some room feedback.
     * @param id = the id of the room feedback.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_ROOM"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return roomFeedbackService.update(id, attribute, value);
    }

    /**
     * Deletes a room feedback.
     * @param id = the id of the room feedback.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_ROOM"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRoomFeedback(@RequestParam int id) {
        return roomFeedbackService.delete(id);
    }

    /**
     * Lists all room feedback.
     * @return Iterable of all room reservations.
     */
    @Secured({"ROLE_ADMIN", "ROLE_ROOM"})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<RoomFeedback> getAllRoomFeedback() {
        return roomFeedbackService.all();
    }
}
