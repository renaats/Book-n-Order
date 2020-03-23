package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Date;
import java.util.HashSet;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeFeedback;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeFeedbackRepository;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the RoomFeedbackTest entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BikeFeedbackTest {
    @Autowired
    private BikeReservationRepository bikeReservationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BikeFeedbackRepository bikeFeedbackRepository;

    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;
    Bike bike;
    AppUser appUser;
    Building building;
    Building fromBuilding;
    Building toBuilding;
    BikeFeedback feedback;
    BikeFeedback feedback2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(building);

        bike = new Bike();
        bike.setLocation(building);
        bike.setAvailable(true);
        bikeRepository.saveAndFlush(bike);

        appUser = new AppUser("a.delia@student.tudelft.nl", "1234", "Alto", "Delia", "EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        fromBuilding = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(fromBuilding);

        toBuilding = new Building("SportHal", "Mekelweg", 8);
        buildingRepository.saveAndFlush(toBuilding);

        bikeReservation = new BikeReservation(bike, userRepository.findAll().get(0), fromBuilding, toBuilding,
                new Date(10000000000L), new Date(11000000000L));
        bikeReservationRepository.saveAndFlush(bikeReservation);
        bikeReservation = bikeReservationRepository.findAll().get(0);

        feedback = new BikeFeedback(appUser, appUser, bikeReservation, new Date(10000000000L), "good");
        bikeFeedbackRepository.save(feedback);
    }

    @Test
    public void testConstructor() {
        assertNotNull(feedback);
    }

    @Test
    public void saveAndRetrieveBikeFeedback() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback, feedback2);
    }

    @Test
    public void testGetClient() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback.getClient(), feedback2.getClient());
    }

    @Test
    public void testGetRecipient() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback.getRecipient(), feedback2.getRecipient());
    }

    @Test
    public void testGetBikeFeedback() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback.getBikeReservation(), feedback2.getBikeReservation());
    }

    @Test
    public void testGetTime() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback.getTime(), feedback2.getTime());
    }

    @Test
    public void testFeedback() {
        feedback2 = bikeFeedbackRepository.findAll().get(0);
        assertEquals(feedback.getFeedback(), feedback2.getFeedback());
    }

    @Test
    public void testEqualBikeFeedback() {
        feedback2 = new BikeFeedback(appUser, appUser, bikeReservation, new Date(10000000000L), "good");
        assertNotSame(feedback, feedback2);
    }
}
