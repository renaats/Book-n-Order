package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.RoomFeedback;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomFeedbackRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomFeedbackService {
    @Autowired
    private RoomFeedbackRepository roomFeedbackRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a room feedback.
     * @return String to see if your request passed
     */
    public int add(String clientEmail, String recipientEmail, int reservationId, long time, String feedback) {

        RoomFeedback roomFeedback = new RoomFeedback();

        Optional<AppUser> optionalClient = userRepository.findById(clientEmail);
        if (optionalClient.isEmpty()) {
            return 404;
        }
        AppUser clientAppUser = optionalClient.get();
        roomFeedback.setClient(clientAppUser);

        Optional<AppUser> optionalRecipient = userRepository.findById(recipientEmail);
        if (optionalRecipient.isEmpty()) {
            return 404;
        }
        AppUser recipientAppUser = optionalRecipient.get();
        roomFeedback.setClient(recipientAppUser);

        Optional<RoomReservation> optionalReservation = roomReservationRepository.findById(reservationId);
        if (!optionalReservation.isPresent()) {
            return 422;
        }
        RoomReservation roomReservation = optionalReservation.get();
        roomFeedback.setRoomReservation(roomReservation);

        roomFeedback.setTime(new Date(time));

        roomFeedback.setFeedback(feedback);

        roomFeedbackRepository.save(roomFeedback);
        return 201;
    }

    /**
     * Updates a specified attribute for some room feedback.
     * @param id = the id of the bike reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (roomFeedbackRepository.findById(id).isEmpty()) {
            return 421;
        }
        RoomFeedback roomFeedback = roomFeedbackRepository.findById(id).get();

        switch (attribute) {
            case "clientemail":
                Optional<AppUser> optionalClient = userRepository.findById(value);
                if (optionalClient.isEmpty()) {
                    return 419;
                }
                AppUser client = optionalClient.get();
                roomFeedback.setClient(client);
                break;
            case "recipientemail":
                Optional<AppUser> optionalRecipient = userRepository.findById(value);
                if (optionalRecipient.isEmpty()) {
                    return 419;
                }
                AppUser recipient = optionalRecipient.get();
                roomFeedback.setRecipient(recipient);
                break;
            case "roomreservation":
                int roomReservationId = Integer.parseInt(value);
                Optional<RoomReservation> optionalRoomReservation = roomReservationRepository.findById(roomReservationId);
                if (optionalRoomReservation.isEmpty()) {
                    return 422;
                }
                RoomReservation roomReservation = optionalRoomReservation.get();
                roomFeedback.setRoomReservation(roomReservation);
                break;
            case "time":
                long dateLong = Long.parseLong(value);
                Date date = new Date(dateLong);
                roomFeedback.setTime(date);
                break;
            case "feedback":
                roomFeedback.setFeedback(value);
            default:
                return 420;
        }
        roomFeedbackRepository.save(roomFeedback);
        return 200;
    }

    /**
     * Deletes a room feedback.
     * @param id = the id of the room feedback
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!roomFeedbackRepository.existsById(id)) {
            return 416;
        }
        roomFeedbackRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all room feedback.
     * @return all room feedback
     */
    public List<RoomFeedback> all() {
        return roomFeedbackRepository.findAll();
    }

    /**
     * Finds a room feedback with the specified id.
     * @param id = the id of the room feedback
     * @return the room feedback that matches the provided id
     */
    public RoomFeedback find(int id) {
        return roomFeedbackRepository.findById(id).orElse(null);
    }
}
