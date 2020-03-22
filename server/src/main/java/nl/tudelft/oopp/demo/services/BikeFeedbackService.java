package nl.tudelft.oopp.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.BikeFeedback;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.repositories.BikeFeedbackRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeFeedbackService {
    @Autowired
    private BikeFeedbackRepository bikeFeedbackRepository;

    @Autowired
    private BikeReservationRepository bikeReservationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a bike feedback.
     * @return String to see if your request passed
     */
    public int add(String clientEmail, String recipientEmail, int reservationId, long time, String feedback) {

        BikeFeedback roomFeedback = new BikeFeedback();

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

        Optional<BikeReservation> optionalReservation = bikeReservationRepository.findById(reservationId);
        if (!optionalReservation.isPresent()) {
            return 422;
        }
        BikeReservation bikeReservation = optionalReservation.get();
        roomFeedback.setBikeReservation(bikeReservation);

        roomFeedback.setTime(new Date(time));

        roomFeedback.setFeedback(feedback);

        bikeFeedbackRepository.save(roomFeedback);
        return 201;
    }

    /**
     * Updates a specified attribute for some bike feedback.
     * @param id = the id of the bike reservation.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return String containing the result of your request.
     */
    public int update(int id, String attribute, String value) {
        if (bikeFeedbackRepository.findById(id).isEmpty()) {
            return 421;
        }
        BikeFeedback bikeFeedback = bikeFeedbackRepository.findById(id).get();

        switch (attribute) {
            case "clientemail":
                Optional<AppUser> optionalClient = userRepository.findById(value);
                if (optionalClient.isEmpty()) {
                    return 419;
                }
                AppUser client = optionalClient.get();
                bikeFeedback.setClient(client);
                break;
            case "recipientemail":
                Optional<AppUser> optionalRecipient = userRepository.findById(value);
                if (optionalRecipient.isEmpty()) {
                    return 419;
                }
                AppUser recipient = optionalRecipient.get();
                bikeFeedback.setRecipient(recipient);
                break;
            case "roomreservation":
                int bikeReservationId = Integer.parseInt(value);
                Optional<BikeReservation> optionalBikeReservation = bikeReservationRepository.findById(bikeReservationId);
                if (optionalBikeReservation.isEmpty()) {
                    return 422;
                }
                BikeReservation bikeReservation = optionalBikeReservation.get();
                bikeFeedback.setBikeReservation(bikeReservation);
                break;
            case "time":
                long dateLong = Long.parseLong(value);
                Date date = new Date(dateLong);
                bikeFeedback.setTime(date);
                break;
            case "feedback":
                bikeFeedback.setFeedback(value);
                break;
            default:
                return 420;
        }
        bikeFeedbackRepository.save(bikeFeedback);
        return 200;
    }

    /**
     * Deletes a bike feedback.
     * @param id = the id of the bike feedback
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!bikeFeedbackRepository.existsById(id)) {
            return 432;
        }
        bikeFeedbackRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all bike feedback.
     * @return all bike feedback
     */
    public List<BikeFeedback> all() {
        return bikeFeedbackRepository.findAll();
    }

    /**
     * Finds a bike feedback with the specified id.
     * @param id = the id of the bike feedback
     * @return the bike feedback that matches the provided id
     */
    public BikeFeedback find(int id) {
        return bikeFeedbackRepository.findById(id).orElse(null);
    }
}
