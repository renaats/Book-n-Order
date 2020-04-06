package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the BikeReservation entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BikeReservationTest {
    @Autowired
    private BikeReservationRepository bikeReservationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;
    Bike bike;
    AppUser appUser;
    Building building;
    Building fromBuilding;
    Building toBuilding;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.saveAndFlush(building);

        bike = new Bike();
        bike.setLocation(building);
        bike.setAvailable(true);
        bikeRepository.saveAndFlush(bike);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        fromBuilding = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.saveAndFlush(fromBuilding);

        toBuilding = new Building("SportHal", "Mekelweg", "EWI", 8);
        buildingRepository.saveAndFlush(toBuilding);

        bikeReservation = new BikeReservation(bike, userRepository.findAll().get(0), fromBuilding, toBuilding,
                new Date(10000000000L), new Date(11000000000L));
        bikeReservationRepository.saveAndFlush(bikeReservation);
        bikeReservation = bikeReservationRepository.findAll().get(0);
    }

    /**
     * Tests the constructor of the BikeReservation class
     */
    @Test
    public void testConstructor() {
        assertNotNull(bikeReservation);
    }

    /**
     * Tests the saving and retrieval of an instance of BikeReservation.
     */
    @Test
    public void testSaveAndRetrieveBikeReservation() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation, bikeReservation2);
    }

    /**
     * Tests the getter for the appUser field.
     */
    @Test
    public void testGetAppUser() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getAppUser(), bikeReservation2.getAppUser());
    }

    /**
     * Tests the getter for the bike field.
     */
    @Test
    public void testGetBike() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getBike(), bikeReservation2.getBike());
    }

    /**
     * Tests the getter for the fromTime field.
     */
    @Test
    public void testGetFromTime() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getFromTime(), bikeReservation2.getFromTime());
    }

    /**
     * Tests the getter for the toTime field.
     */
    @Test
    public void testGetToTime() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getToTime(), bikeReservation2.getToTime());
    }

    /**
     * Tests the getter for the active field.
     */
    @Test
    public void testGetActive() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertTrue(bikeReservation.isActive());
    }

    /**
     * Tests the comparison between two equal bike reservations.
     */
    @Test
    public void testEqualBikeReservations() {
        bikeReservation2 = new BikeReservation(bike, appUser, fromBuilding, toBuilding, new Date(10000000000L), new Date(11000000000L));
        assertEquals(bikeReservation, bikeReservation2);
        assertNotSame(bikeReservation, bikeReservation2);
    }

    /**
     * Tests the hasBikeReservationBetween method for the Bike class with reservations that are inside each other.
     */
    @Test
    public void testReservationsInside() {
        Set<BikeReservation> bikeReservations = new HashSet<>();
        bikeReservations.add(bikeReservation);
        bike.setBikeReservations(bikeReservations);
        assertTrue(bike.hasBikeReservationBetween(new Date(10000000000L), new Date(11000000000L)));
        assertTrue(bike.hasBikeReservationBetween(new Date(10500000000L), new Date(10600000000L)));
    }

    /**
     * Tests the hasBikeReservationBetween method for the Bike class with reservations that share an endpoint.
     */
    @Test
    public void testReservationsShareEndpoint() {
        Set<BikeReservation> bikeReservations = new HashSet<>();
        bikeReservations.add(bikeReservation);
        bike.setBikeReservations(bikeReservations);
        assertFalse(bike.hasBikeReservationBetween(new Date(9000000000L), new Date(10000000000L)));
        assertFalse(bike.hasBikeReservationBetween(new Date(11000000000L), new Date(12000000000L)));
    }

    /**
     * Tests the hasBikeReservationBetween method for the Bike class with reservations that slightly overlap.
     */
    @Test
    public void testReservationsOverlap() {
        Set<BikeReservation> bikeReservations = new HashSet<>();
        bikeReservations.add(bikeReservation);
        bike.setBikeReservations(bikeReservations);
        assertTrue(bike.hasBikeReservationBetween(new Date(10500000000L), new Date(12000000000L)));
        assertTrue(bike.hasBikeReservationBetween(new Date(10600000000L), new Date(11000000000L)));
    }

    /**
     * Tests the hasBikeReservationBetween method for the Bike class with invalid reservations.
     */
    @Test
    public void testReservationsIllegal() {
        Set<BikeReservation> bikeReservations = new HashSet<>();
        bikeReservations.add(bikeReservation);
        bike.setBikeReservations(bikeReservations);
        assertTrue(bike.hasBikeReservationBetween(new Date(10500000000L), new Date(10000000000L)));
        assertEquals(bikeReservations, bike.getBikeReservations());
    }

    /**
     * Tests the setting of the bikeReservations for an appUser.
     */
    @Test
    public void testSetBikeReservations() {
        Set<BikeReservation> bikeReservations = new HashSet<>();
        bikeReservations.add(bikeReservation);
        appUser.setBikeReservations(bikeReservations);
        assertEquals(bikeReservations, appUser.getBikeReservations());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        bikeReservationRepository.deleteAll();
        bikeRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}
