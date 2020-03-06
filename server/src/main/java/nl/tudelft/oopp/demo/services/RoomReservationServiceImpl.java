package nl.tudelft.oopp.demo.services;

import java.util.Date;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
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
    public String add(int roomId, String userEmail, long fromTimeMs, long toTimeMs) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            return "Could not find room with id " + roomId + "!";
        }
        Room room = optionalRoom.get();

        Optional<User> optionalUser = userRepository.findById(userEmail);
        if (optionalUser.isEmpty()) {
            return "Could not find user with email " + userEmail + "!";
        }
        User user = optionalUser.get();

        if (room.hasRoomReservationBetween(new Date(fromTimeMs), new Date(toTimeMs))) {
            return "This room is already reserved at this time!";
        }

        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoom(room);
        roomReservation.setUser(user);
        roomReservation.setFromTime(new Date(fromTimeMs));
        roomReservation.setToTime(new Date(toTimeMs));
        roomReservationRepository.save(roomReservation);
        return "Saved!";
    }

    @Override
    public String update(int id, String attribute, String value) {
        if (roomReservationRepository.findById(id).isEmpty()) {
            return "Room with ID: " + id + " does not exist!";
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
                    return "Could not find room with id " + roomId + "!";
                }
                Room room = optionalRoom.get();
                roomReservation.setRoom(room);
                break;
            case "userEmail":
                Optional<User> optionalUser = userRepository.findById(value);
                if (optionalUser.isEmpty()) {
                    return "Could not find user with email " + value + "!";
                }
                User user = optionalUser.get();
                roomReservation.setUser(user);
                break;
            default:
                return "No attribute with name " + attribute + " found!";
        }
        roomReservationRepository.save(roomReservation);
        return "The attribute has been set!";
    }

    @Override
    public String delete(int id) {
        if (!roomReservationRepository.existsById(id)) {
            return "Room reservation with ID: " + id + " does not exist!";
        }
        roomReservationRepository.deleteById(id);
        return "Deleted!";
    }

    @Override
    public Iterable<RoomReservation> all() {
        return roomReservationRepository.findAll();
    }
}
