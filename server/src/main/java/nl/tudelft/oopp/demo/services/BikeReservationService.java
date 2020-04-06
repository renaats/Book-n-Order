package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ALL_BIKES_RESERVED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESERVATION_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.USER_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_USER;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the BikeReservation entity.
 * Receives requests from the BikeReservationController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class BikeReservationService {
    @Autowired
    private BikeReservationRepository bikeReservationRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Adds a bike reservation.
     * @param request = the Http request that calls this method.
     * @param fromBuilding = the building where the user picks up the reserved bike.
     * @param toBuilding = the building where the user drops off the reserved bike.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return String containing the result of your request.
     */
    public int add(HttpServletRequest request, int fromBuilding, int toBuilding, long fromTimeMs, long toTimeMs) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return NOT_FOUND;
        }

        Optional<Building> optionalFromBuilding = buildingRepository.findById(fromBuilding);
        if (optionalFromBuilding.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }

        Optional<Building> optionalToBuilding = buildingRepository.findById(toBuilding);
        if (optionalToBuilding.isEmpty()) {
            return BUILDING_NOT_FOUND;
        }

        Bike bike = null;
        for (Bike loopBike: bikeRepository.findAll()) {
            if (loopBike.isAvailable() && !loopBike.hasBikeReservationBetween(new Date(fromTimeMs), new Date(toTimeMs))) {
                bike = loopBike;
                break;
            }
        }
        if (bike == null) {
            return ALL_BIKES_RESERVED;
        }
        Building fromBuildingLoc = optionalFromBuilding.get();
        Building toBuildingLoc = optionalToBuilding.get();

        BikeReservation bikeReservation = new BikeReservation();
        bikeReservation.setBike(bike);
        bikeReservation.setAppUser(appUser);
        bikeReservation.setFromBuilding(fromBuildingLoc);
        bikeReservation.setToBuilding(toBuildingLoc);
        bikeReservation.setFromTime(new Date(fromTimeMs));
        bikeReservation.setToTime(new Date(toTimeMs));
        bikeReservationRepository.save(bikeReservation);
        return ADDED;
    }

    /**
     * Updates a specified attribute for some bike reservation.
     * @param id = the id of the bike reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (bikeReservationRepository.findById(id).isEmpty()) {
            return RESERVATION_NOT_FOUND;
        }
        BikeReservation bikeReservation = bikeReservationRepository.findById(id).get();

        switch (attribute) {
            case "fromtime":
                bikeReservation.setFromTime(new Date(Long.parseLong(value)));
                break;
            case "totime":
                bikeReservation.setToTime(new Date(Long.parseLong(value)));
                break;
            case "frombuilding":
                int fromBuildingId = Integer.parseInt(value);
                Optional<Building> optionalFromBuilding = buildingRepository.findById(fromBuildingId);
                if (optionalFromBuilding.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Building fromBuilding = optionalFromBuilding.get();
                bikeReservation.setFromBuilding(fromBuilding);
                break;
            case "tobuilding":
                int toBuildingId = Integer.parseInt(value);
                Optional<Building> optionalToBuilding = buildingRepository.findById(toBuildingId);
                if (optionalToBuilding.isEmpty()) {
                    return BUILDING_NOT_FOUND;
                }
                Building toBuilding = optionalToBuilding.get();
                bikeReservation.setToBuilding(toBuilding);
                break;
            case "bike":
                int bikeId = Integer.parseInt(value);
                Optional<Bike> optionalBike = bikeRepository.findById(bikeId);
                if (optionalBike.isEmpty()) {
                    return ID_NOT_FOUND;
                }
                Bike bike = optionalBike.get();
                bikeReservation.setBike(bike);
                break;
            case "useremail":
                Optional<AppUser> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return USER_NOT_FOUND;
                }
                AppUser appUser = optionalUser.get();
                bikeReservation.setAppUser(appUser);
                break;
            case "active":
                bikeReservation.setActive(Boolean.parseBoolean(value));
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        bikeReservationRepository.save(bikeReservation);
        return EXECUTED;
    }

    /**
     * Deletes a bike reservation.
     * @param id = the id of the bike reservation.
     * @return String containing the result of your request.
     */
    public int delete(int id) {
        if (!bikeReservationRepository.existsById(id)) {
            return RESERVATION_NOT_FOUND;
        }
        bikeReservationRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all bike reservations.
     * @return Iterable of all bike reservations.
     */
    public List<BikeReservation> all() {
        return bikeReservationRepository.findAll();
    }

    /**
     * Finds a bike reservation with the specified id.
     * @param id = the bike reservation id.
     * @return a bike reservation that matches the id.
     */
    public BikeReservation find(int id) {
        return bikeReservationRepository.findById(id).orElse(null);
    }

    /**
     * Finds all past bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of past bike reservations for this user.
     */
    public List<BikeReservation> past(HttpServletRequest request) {
        List<BikeReservation> bikeReservations = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return bikeReservations;
        }
        for (BikeReservation bikeReservation: bikeReservationRepository.findAll()) {
            if (bikeReservation.getAppUser() == appUser && (!bikeReservation.getToTime().after(new Date())) || !bikeReservation.isActive()) {
                bikeReservations.add(bikeReservation);
            }
        }
        return bikeReservations;
    }

    /**
     * Finds all future bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of future bike reservations for this user.
     */
    public List<BikeReservation> future(HttpServletRequest request) {
        List<BikeReservation> bikeReservations = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return bikeReservations;
        }
        for (BikeReservation bikeReservation: bikeReservationRepository.findAll()) {
            if (bikeReservation.getAppUser() == appUser && bikeReservation.getToTime().after(new Date()) && bikeReservation.isActive()) {
                bikeReservations.add(bikeReservation);
            }
        }
        return bikeReservations;
    }

    /**
     * Finds all past bike reservations for some bike.
     * @param bikeId = the bike for which the bike reservations are searched.
     * @return a list of past bike reservations for this bike.
     */
    public List<BikeReservation> pastForAdmin(int bikeId) {
        List<BikeReservation> bikeReservations = new ArrayList<>();
        for (BikeReservation bikeReservation: bikeReservationRepository.findAllByBikeId(bikeId)) {
            if (!bikeReservation.getToTime().after(new Date()) || !bikeReservation.isActive()) {
                bikeReservations.add(bikeReservation);
            }
        }
        return bikeReservations;
    }

    /**
     * Finds all future bike reservations for some bike.
     * @param bikeId = the bike for which the bike reservations are searched.
     * @return a list of future bike reservations for this bike.
     */
    public List<BikeReservation> futureForAdmin(int bikeId) {
        List<BikeReservation> bikeReservations = new ArrayList<>();
        for (BikeReservation bikeReservation: bikeReservationRepository.findAllByBikeId(bikeId)) {
            if (bikeReservation.getToTime().after(new Date()) && bikeReservation.isActive()) {
                bikeReservations.add(bikeReservation);
            }
        }
        return bikeReservations;
    }

    /**
     * Finds all active bike reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @return a list of active bike reservations for this user.
     */
    public List<BikeReservation> active(HttpServletRequest request) {
        List<BikeReservation> bikeReservations = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return bikeReservations;
        }
        for (BikeReservation bikeReservation: bikeReservationRepository.findAll()) {
            if (bikeReservation.getAppUser() == appUser && bikeReservation.isActive()) {
                bikeReservations.add(bikeReservation);
            }
        }
        return bikeReservations;
    }

    /**
     * Cancels a bike reservation if it was made by the user that sends the Http request.
     * @param request = the Http request that calls this method.
     * @param bikeReservationId = the id of the target reservation.
     * @return an error code.
     */
    public int cancel(HttpServletRequest request, int bikeReservationId) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (bikeReservationRepository.findById(bikeReservationId).isEmpty()) {
            return RESERVATION_NOT_FOUND;
        }
        BikeReservation bikeReservation = bikeReservationRepository.findById(bikeReservationId).get();
        if (!bikeReservation.getAppUser().equals(appUser)) {
            return WRONG_USER;
        }
        bikeReservation.setActive(false);
        bikeReservationRepository.save(bikeReservation);
        return EXECUTED;
    }
}
