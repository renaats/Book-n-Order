package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeFeedback;
import nl.tudelft.oopp.demo.services.BikeFeedbackService;

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
 * Creates server side endpoints and routes requests to the BikeFeedbackService.
 * Maps all requests that start with "/bike_feedback".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/bike_feedback")
public class BikeFeedbackController {
    @Autowired
    private BikeFeedbackService bikeFeedbackService;

    /**
     * Adds a bike feedback.
     * @param clientEmail = the email of the feedback sender.
     * @param recipientEmail = the email of the feedback recipient.
     * @param bikeReservationId = the id of the bike reservation being reviewed.
     * @param time = time when the feedback was sent.
     * @param feedback = the feedback text.
     * @return int containing the result of your request.
     */
    @Secured("ROLE_USER")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewBikeFeedback(
            @RequestParam String clientEmail,
            @RequestParam String recipientEmail,
            @RequestParam int bikeReservationId,
            @RequestParam long time,
            @RequestParam String feedback) {
        return bikeFeedbackService.add(clientEmail, recipientEmail, bikeReservationId, time, feedback);
    }

    /**
     * Updates a specified attribute for some bike feedback.
     * @param id = the id of the bike feedback.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return int containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return bikeFeedbackService.update(id, attribute, value);
    }

    /**
     * Deletes a bike feedback.
     * @param id = the id of the bike feedback.
     * @return int containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteBikeFeedback(@RequestParam int id) {
        return bikeFeedbackService.delete(id);
    }

    /**
     * Lists all bike feedback.
     * @return Iterable of all bike reservations.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BIKE"})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BikeFeedback> getAllBikeFeedback() {
        return bikeFeedbackService.all();
    }
}
