package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the RoomFeedbackTest entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomFeedbackTest {
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RoomFeedbackRepository roomFeedbackRepository;

    RoomReservation roomReservation;
    RoomReservation roomReservation2;
    Room room;
    AppUser appUser;
    Building building;
    RoomFeedback feedback;
    RoomFeedback feedback2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(building);

        room = new Room("Ampere", building, "EWI", false, true, true, 300, 250);
        room.setRoomReservations(new HashSet<>());
        roomRepository.saveAndFlush(room);

        appUser = new AppUser("R.Jursevskis@student.tudelft.nl", "1234", "Renats", "Jursevskis", "EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        roomReservation = new RoomReservation(roomRepository.findAll().get(0), userRepository.findAll().get(0),
                new Date(10000000000L), new Date(11000000000L));
        roomReservationRepository.saveAndFlush(roomReservation);
        roomReservation = roomReservationRepository.findAll().get(0);

        feedback = new RoomFeedback(appUser, appUser, roomReservation, new Date(10000000000L), "good");
        roomFeedbackRepository.saveAndFlush(feedback);
    }

    @Test
    public void testConstructor() {
        assertNotNull(feedback);
    }
}
