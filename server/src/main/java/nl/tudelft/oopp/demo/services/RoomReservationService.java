package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the RoomReservation entity.
 * Receives requests from the RoomReservationController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class RoomReservationService {
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a room reservation.
     * @param roomId = the id of the room associated to the reservation.
     * @param userEmail = the email of the user associated to the reservation.
     * @param fromTimeMs = the starting time of the reservation.
     * @param toTimeMs = the ending time of the reservation.
     * @return String containing the result of your request.
     */
    public int add(int roomId, String userEmail, long fromTimeMs, long toTimeMs) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return 416;
        }
        Room room = optionalRoom.get();

        Optional<AppUser> optionalUser = userRepository.findById(userEmail);
        if (optionalUser.isEmpty()) {
            return 404;
        }
        AppUser appUser = optionalUser.get();

        if (room.hasRoomReservationBetween(new Date(fromTimeMs), new Date(toTimeMs))) {
            return 308;
        }

        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoom(room);
        roomReservation.setAppUser(appUser);
        roomReservation.setFromTime(new Date(fromTimeMs));
        roomReservation.setToTime(new Date(toTimeMs));
        roomReservationRepository.save(roomReservation);
        return 201;
    }

    /**
     * Updates a specified attribute for some room reservation.
     * @param id = the id of the room reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (roomReservationRepository.findById(id).isEmpty()) {
            return 421;
        }
        RoomReservation roomReservation = roomReservationRepository.findById(id).get();

        switch (attribute) {
            case "fromdate":
                roomReservation.setFromTime(new Date(Long.parseLong(value)));
                break;
            case "todate":
                roomReservation.setToTime(new Date(Long.parseLong(value)));
                break;
            case "roomid":
                int roomId = Integer.parseInt(value);
                Optional<Room> optionalRoom = roomRepository.findById(roomId);
                if (optionalRoom.isEmpty()) {
                    return 418;
                }
                Room room = optionalRoom.get();
                roomReservation.setRoom(room);
                break;
            case "useremail":
                Optional<AppUser> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return 419;
                }
                AppUser appUser = optionalUser.get();
                roomReservation.setAppUser(appUser);
                break;
            default:
                return 420;
        }
        roomReservationRepository.save(roomReservation);
        return 200;
    }

    /**
     * Deletes a room reservation.
     * @param id = the id of the room reservation.
     * @return String containing the result of your request.
     */
    public int delete(int id) {
        if (!roomReservationRepository.existsById(id)) {
            return 421;
        }
        roomReservationRepository.deleteById(id);
        return 200;
    }

    /**
     * Finds a room reservation with the specified id.
     * @param id = thre id of the room reservation
     * @return a room reservation with the specified id  or null if no such reservation exists
     */
    public RoomReservation find(int id) {
        return roomReservationRepository.findById(id).orElse(null);
    }

    /**
     * Lists all room reservations.
     * @return List of all room reservations.
     */
    public List<RoomReservation> all() {
        return roomReservationRepository.findAll();
    }

    /**
     * Finds all past room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of past room reservations for this user.
     */
    public List<RoomReservation> past(HttpServletRequest request) {
        List<RoomReservation> roomReservations = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return roomReservations;
        }
        for (RoomReservation roomReservation: roomReservationRepository.findAll()) {
            if (roomReservation.getAppUser() == appUser && roomReservation.getToTime().before(new Date())) {
                roomReservations.add(roomReservation);
            }
        }
        return roomReservations;
    }

    /**
     * Finds all future room reservations for the user that sends the Http request.
     * @param request = the Http request that calls this method
     * @return a list of future room reservations for this user.
     */
    public List<RoomReservation> future(HttpServletRequest request) {
        List<RoomReservation> roomReservations = new ArrayList<>();
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = UserService.getAppUser(token, userRepository);
        if (appUser == null) {
            return roomReservations;
        }
        for (RoomReservation roomReservation: roomReservationRepository.findAll()) {
            if (roomReservation.getAppUser() == appUser && roomReservation.getToTime().after(new Date())) {
                roomReservations.add(roomReservation);
            }
        }
        return roomReservations;
    }
}
