package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BIKE_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.services.BikeReservationService;

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
 * Creates server side endpoints and routes requests to the BikeReservationService.
 * Maps all requests that start with "/bike_reservation".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/bike_reservation")
public class BikeReservationController {
    @Autowired
    BikeReservationService bikeReservationService;

    /**
     * Adds a bike reservation.
     * @param request = the Http request that calls this method.
     * @param fromBuilding = the building where the user picks up the reserved bike.
     * @param toBuilding = the building where the user drops off the reserved bike.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return String containing the result of your request.
     */
    @Secured(USER)
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public int addNewBikeReservation(
            HttpServletRequest request,
            @RequestParam int fromBuilding,
            @RequestParam int toBuilding,
            @RequestParam long fromTimeMs,
            @RequestParam long toTimeMs) {
        return bikeReservationService.add(request, fromBuilding, toBuilding, fromTimeMs, toTimeMs);
    }

    /**
     * Updates a specified attribute for some bike reservation.
     * @param id = the id of the bike reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    @Secured({ADMIN, BIKE_ADMIN})
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
    @Secured({ADMIN, BIKE_ADMIN})
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteBikeReservation(@RequestParam int id) {
        return bikeReservationService.delete(id);
    }

    /**
     * Lists all bike reservations.
     * @return Iterable of all bike reservations.
     */
    @Secured({ADMIN, BIKE_ADMIN})
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BikeReservation> getAllBikeReservations() {
        return bikeReservationService.all();
    }

    /**
     * Finds all past bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of past bike reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/past")
    public Iterable<BikeReservation> getPastReservations(HttpServletRequest request) {
        return bikeReservationService.past(request);
    }

    /**
     * Finds all future bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of future bike reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/future")
    public Iterable<BikeReservation> getFutureReservations(HttpServletRequest request) {
        return bikeReservationService.future(request);
    }

    /**
     * Finds all past bike reservations for some bike.
     * @param bikeId = the bike for which the bike reservations are searched.
     * @return a list of past bike reservations for this bike.
     */
    @Secured({ADMIN, BIKE_ADMIN})
    @GetMapping(path = "/pastAdmin")
    public Iterable<BikeReservation> getPastReservationsAdmin(@RequestParam int bikeId) {
        return bikeReservationService.pastForAdmin(bikeId);
    }

    /**
     * Finds all future bike reservations for some bike.
     * @param bikeId = the bike for which the bike reservations are searched.
     * @return a list of future bike reservations for this bike.
     */
    @Secured({ADMIN, BIKE_ADMIN})
    @GetMapping(path = "/futureAdmin")
    public Iterable<BikeReservation> getFutureReservationsAdmin(@RequestParam int bikeId) {
        return bikeReservationService.futureForAdmin(bikeId);
    }

    /**
     * Finds all active bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of active bike reservations for this user.
     */
    @Secured(USER)
    @GetMapping(path = "/active")
    public Iterable<BikeReservation> getActiveReservations(HttpServletRequest request) {
        return bikeReservationService.active(request);
    }

    /**
     * Cancels a bike reservation if it was made by the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @param bikeReservationId = the id of the target reservation.
     * @return an error code.
     */
    @Secured(USER)
    @GetMapping(path = "/cancel/{id}")
    public int cancelReservation(HttpServletRequest request, @PathVariable(value = "id") int bikeReservationId) {
        return bikeReservationService.cancel(request, bikeReservationId);
    }
}
