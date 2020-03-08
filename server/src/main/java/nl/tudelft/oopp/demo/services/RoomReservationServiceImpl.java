package nl.tudelft.oopp.demo.services;

import java.util.Date;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
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

    @Override
    public int update(int id, String attribute, String value) {
        if (roomReservationRepository.findById(id).isEmpty()) {
            return 421;
        }
        RoomReservation roomReservation = roomReservationRepository.findById(id).get();

        switch (attribute) {
            case "fromDate":
                roomReservation.setFromTime(new Date(Integer.parseInt(value)));
                break;
            case "toDate":
                roomReservation.setToTime(new Date(Integer.parseInt(value)));
                break;
            case "roomId":
                int roomId = Integer.parseInt(value);
                Optional<Room> optionalRoom = roomRepository.findById(roomId);
                if (optionalRoom.isEmpty()) {
                    return 418;
                }
                Room room = optionalRoom.get();
                roomReservation.setRoom(room);
                break;
            case "userEmail":
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

    @Override
    public int delete(int id) {
        if (!roomReservationRepository.existsById(id)) {
            return 421;
        }
        roomReservationRepository.deleteById(id);
        return 200;
    }

    @Override
    public Iterable<RoomReservation> all() {
        return roomReservationRepository.findAll();
    }
}
