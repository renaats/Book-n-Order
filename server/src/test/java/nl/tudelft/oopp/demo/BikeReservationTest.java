package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Date;
import java.util.HashSet;

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

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(building);

        bike = new Bike();
        bike.setLocation(building);
        bike.setAvailable(true);
        bikeRepository.saveAndFlush(bike);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
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
    }

    @Test
    public void saveAndRetrieveBikeReservation() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation, bikeReservation2);
    }

    @Test
    public void testGetAppUser() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getAppUser(), bikeReservation2.getAppUser());
    }

    @Test
    public void testGetBike() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getBike(), bikeReservation2.getBike());
    }

    @Test
    public void testGetFromTime() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getFromTime(), bikeReservation2.getFromTime());
    }

    @Test
    public void testToTime() {
        bikeReservation2 = bikeReservationRepository.findAll().get(0);
        assertEquals(bikeReservation.getToTime(), bikeReservation2.getToTime());
    }

    @Test
    public void testEqualBikeReservations() {
        bikeReservation2 = new BikeReservation(bike, appUser, fromBuilding, toBuilding, new Date(10000000000L), new Date(11000000000L));
        assertEquals(bikeReservation, bikeReservation2);
        assertNotSame(bikeReservation, bikeReservation2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        bikeReservationRepository.deleteAll();
        bikeRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}
