package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.BikeReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the BikeReservation service.
 */
@DataJpaTest
public class BikeReservationServiceTest {
    @TestConfiguration
    static class BikeReservationServiceTestConfiguration {
        @Bean
        public BikeReservationService bikeReservationService() {
            return new BikeReservationService();
        }
    }

    @Autowired
    BikeReservationService bikeReservationService;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    Building fromBuilding;
    Building toBuilding;
    Building toBuilding2;
    Bike bike;
    Bike bike2;
    AppUser appUser;
    AppUser appUser2;
    Date fromTime;
    Date toTime;
    long fromTimeMs;
    long toTimeMs;
    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser.setBikeReservations(new HashSet<>());
        userRepository.save(appUser);

        appUser2 = new AppUser("l.j.jongejans@tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser2.setBikeReservations(new HashSet<>());
        userRepository.save(appUser2);

        fromBuilding = new Building("Sporthal", "Mekelweg", 8);
        buildingRepository.save(fromBuilding);

        toBuilding = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(toBuilding);

        toBuilding2 = new Building("Library", "Prometheusplein", 1);
        buildingRepository.save(toBuilding2);

        bike = new Bike(fromBuilding, true);
        bikeRepository.save(bike);

        bike2 = new Bike(fromBuilding, false);
        bikeRepository.save(bike2);

        fromTime = new Date(10000000000L);
        fromTimeMs = fromTime.getTime();

        toTime = new Date(11000000000L);
        toTimeMs = toTime.getTime();

        bikeReservation = new BikeReservation(bike, appUser, fromBuilding, toBuilding, fromTime, toTime);

        bikeReservation2 = new BikeReservation(bike2, appUser2, fromBuilding, toBuilding2, fromTime, toTime);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(bikeReservationService);
    }

    /**
     * Tests the creation of an instance with an invalid bike id.
     */
    @Test
    public void testInvalidBike() {
        assertEquals(416, bikeReservationService.add(0, appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs));
    }

    /**
     * Tests the creation of an instance with an invalid user email.
     */
    @Test
    public void testInvalidAppUser() {
        assertEquals(404, bikeReservationService.add(bike.getId(), "aa", fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs));
    }

    /**
     * Tests the creation of an instance with an invalid fromBuilding.
     */
    @Test
    public void testInvalidFromBuilding() {
        assertEquals(422, bikeReservationService.add(bike.getId(), appUser.getEmail(), 0, toBuilding.getId(), fromTimeMs, toTimeMs));
    }

    /**
     * Tests the creation of an instance with an invalid fromBuilding.
     */
    @Test
    public void testInvalidToBuilding() {
        assertEquals(422, bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), 0, fromTimeMs, toTimeMs));
    }

    /**
     * Tests the saving and retrieval of an instance of BikeReservation.
     */
    @Test
    public void testAdd() {
        assertEquals(201,
                bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs));
        assertEquals(Collections.singletonList(bikeReservation), bikeReservationService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(bikeReservationService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotNull(bikeReservationService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(421, bikeReservationService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertEquals(420, bikeReservationService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the bike by using the service.
     */
    @Test
    public void testChangeBike() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(bike2, bikeReservationService.find(id).getBike());
        bikeReservationService.update(id, "bike", ((Integer) bike2.getId()).toString());
        assertEquals(bike2, bikeReservationService.find(id).getBike());
    }

    /**
     * Tests the change of the user by using the service.
     */
    @Test
    public void testChangeUser() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(appUser2, bikeReservationService.find(id).getAppUser());
        bikeReservationService.update(id, "useremail", appUser2.getEmail());
        assertEquals(appUser2, bikeReservationService.find(id).getAppUser());
    }

    /**
     * Tests the change of the from building by using the service.
     */
    @Test
    public void testChangeFromBuilding() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(toBuilding, bikeReservationService.find(id).getFromBuilding());
        bikeReservationService.update(id, "frombuilding", toBuilding.getId().toString());
        assertEquals(toBuilding, bikeReservationService.find(id).getFromBuilding());
    }

    /**
     * Tests the change of the to building by using the service.
     */
    @Test
    public void testChangeToBuilding() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(toBuilding2, bikeReservationService.find(id).getToBuilding());
        bikeReservationService.update(id, "tobuilding", toBuilding2.getId().toString());
        assertEquals(toBuilding2, bikeReservationService.find(id).getToBuilding());
    }

    /**
     * Tests the change of the from time by using the service.
     */
    @Test
    public void testChangeFromTime() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(toTime, bikeReservationService.find(id).getFromTime());
        bikeReservationService.update(id, "fromtime", ((Long) toTime.getTime()).toString());
        assertEquals(toTime, bikeReservationService.find(id).getFromTime());
    }

    /**
     * Tests the change of the to time by using the service.
     */
    @Test
    public void testChangeToTime() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(fromTime, bikeReservationService.find(id).getToTime());
        bikeReservationService.update(id, "totime", ((Long) fromTime.getTime()).toString());
        assertEquals(fromTime, bikeReservationService.find(id).getToTime());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        bikeReservationService.add(bike2.getId(), appUser2.getEmail(), fromBuilding.getId(), toBuilding2.getId(), fromTimeMs, toTimeMs);
        assertEquals(2, bikeReservationService.all().size());
        ArrayList<BikeReservation> bikeReservations = new ArrayList<>();
        bikeReservations.add(bikeReservation);
        bikeReservations.add(bikeReservation2);
        assertEquals(bikeReservations, bikeReservationService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        int id = bikeReservationService.all().get(0).getId();
        assertEquals(200, bikeReservationService.delete(id));
        assertEquals(0, bikeReservationService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(421, bikeReservationService.delete(0));
    }
}
