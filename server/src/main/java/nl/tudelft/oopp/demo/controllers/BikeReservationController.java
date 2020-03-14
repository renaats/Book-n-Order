package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.services.BikeReservationService;
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

@Repository
@RestController
@RequestMapping(path = "/bike_reservation")
public class BikeReservationController {
    @Autowired
    BikeReservationService bikeReservationService;

    /**
     * Adds a bike reservation.
     * @param bikeId = the id of the bike associated to the reservation.
     * @param userEmail = the email of the user associated to the reservation.
     * @param fromBuilding = the building where the user picks up the reserved bike.
     * @param toBuilding = the building where the user drops off the reserved bike.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_USER")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addNewBikeReservation(
            @RequestParam String userEmail,
            @RequestParam int bikeId,
            @RequestParam int fromBuilding,
            @RequestParam int toBuilding,
            @RequestParam long fromTimeMs,
            @RequestParam long toTimeMs) {
        return bikeReservationService.add(bikeId, userEmail, fromBuilding, toBuilding, fromTimeMs, toTimeMs);
    }

    /**
     * Updates a specified attribute for some bike reservation.
     * @param id = the id of the bike reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam int id, @RequestParam String attribute, @RequestParam String value) {
        return bikeReservationService.update(id, attribute, value);
    }

    /**
     * Deletes a bike reservation.
     * @param id = the id of the bike reservation.
     * @return String containing the result of your request.
     */
    @Secured({"ROLE_ADMIN", "ROLE_BUILDING_ADMIN"})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteBikeReservation(@RequestParam int id) {
        return bikeReservationService.delete(id);
    }

    /**
     * Lists all bike reservations.
     * @return Iterable of all bike reservations.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BikeReservation> getAllBikeReservations() {
        return bikeReservationService.all();
    }
}
