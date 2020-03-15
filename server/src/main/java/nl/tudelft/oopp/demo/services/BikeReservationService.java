package nl.tudelft.oopp.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * @param bikeId = the id of the bike associated to the reservation.
     * @param userEmail = the email of the user associated to the reservation.
     * @param fromBuilding = the building where the user picks up the reserved bike.
     * @param toBuilding = the building where the user drops off the reserved bike.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return String containing the result of your request.
     */
    public int add(int bikeId, String userEmail, int fromBuilding, int toBuilding, long fromTimeMs, long toTimeMs) {
        Optional<Bike> optionalBike = bikeRepository.findById(bikeId);
        if (optionalBike.isEmpty()) {
            return 416;
        }
        Bike bike = optionalBike.get();
        BikeReservation bikeReservation = new BikeReservation();
        bikeReservation.setBike(bike);

        Optional<AppUser> optionalUser = userRepository.findById(userEmail);
        if (optionalUser.isEmpty()) {
            return 404;
        }
        AppUser appUser = optionalUser.get();
        bikeReservation.setAppUser(appUser);

        Optional<Building> optionalFromBuilding = buildingRepository.findById(fromBuilding);
        if (!optionalFromBuilding.isPresent()) {
            return 422;
        }
        Building fromBuildingLoc = optionalFromBuilding.get();
        bikeReservation.setFromBuilding(fromBuildingLoc);

        Optional<Building> optionalToBuilding = buildingRepository.findById(toBuilding);
        if (!optionalToBuilding.isPresent()) {
            return 422;
        }
        Building toBuildingLoc = optionalToBuilding.get();
        bikeReservation.setToBuilding(toBuildingLoc);

        if (bike.hasBikeReservationBetween(new Date(fromTimeMs), new Date(toTimeMs))) {
            return 308;
        }
        bikeReservation.setFromTime(new Date(fromTimeMs));
        bikeReservation.setToTime(new Date(toTimeMs));

        bikeReservationRepository.save(bikeReservation);
        return 201;
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
            return 421;
        }
        BikeReservation bikeReservation = bikeReservationRepository.findById(id).get();

        switch (attribute) {
            case "fromTime":
                bikeReservation.setFromTime(new Date(Integer.parseInt(value)));
                break;
            case "toTime":
                bikeReservation.setToTime(new Date(Integer.parseInt(value)));
                break;
            case "fromBuilding":
                int fromBuildingId = Integer.parseInt(value);
                Optional<Building> optionalFromBuilding = buildingRepository.findById(fromBuildingId);
                if (optionalFromBuilding.isEmpty()) {
                    return 422;
                }
                Building fromBuilding = optionalFromBuilding.get();
                bikeReservation.setFromBuilding(fromBuilding);
                break;
            case "toBuilding":
                int toBuildingId = Integer.parseInt(value);
                Optional<Building> optionalToBuilding = buildingRepository.findById(toBuildingId);
                if (optionalToBuilding.isEmpty()) {
                    return 422;
                }
                Building toBuilding = optionalToBuilding.get();
                bikeReservation.setFromBuilding(toBuilding);
                break;
            case "roomId":
                int bikeId = Integer.parseInt(value);
                Optional<Bike> optionalBike = bikeRepository.findById(bikeId);
                if (optionalBike.isEmpty()) {
                    return 416;
                }
                Bike bike = optionalBike.get();
                bikeReservation.setBike(bike);
                break;
            case "userEmail":
                Optional<AppUser> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return 419;
                }
                AppUser appUser = optionalUser.get();
                bikeReservation.setAppUser(appUser);
                break;
            default:
                return 420;
        }
        bikeReservationRepository.save(bikeReservation);
        return 200;
    }

    /**
     * Deletes a bike reservation.
     * @param id = the id of the bike reservation.
     * @return String containing the result of your request.
     */
    public int delete(int id) {
        if (!bikeReservationRepository.existsById(id)) {
            return 421;
        }
        bikeReservationRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all bike reservations.
     * @return Iterable of all bike reservations.
     */
    public List<BikeReservation> all() {
        return bikeReservationRepository.findAll();
    }
}
