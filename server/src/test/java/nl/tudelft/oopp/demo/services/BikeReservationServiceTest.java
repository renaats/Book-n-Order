package nl.tudelft.oopp.demo.services;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESERVATION_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_USER;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auth0.jwt.JWT;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;

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
    long fromTimeMilliseconds;
    long toTimeMilliseconds;
    Date fromTime2;
    Date toTime2;
    long fromTimeMilliseconds2;
    long toTimeMilliseconds2;
    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;

    MockHttpServletRequest request;
    MockHttpServletRequest request2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser.setBikeReservations(new HashSet<>());
        userRepository.save(appUser);

        appUser2 = new AppUser("l.j.jongejans@tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser2.setBikeReservations(new HashSet<>());
        userRepository.save(appUser2);

        fromBuilding = new Building("Sporthal", "Mekelweg", "None", 8);
        buildingRepository.save(fromBuilding);

        toBuilding = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(toBuilding);

        toBuilding2 = new Building("Library", "Prometheusplein", "None", 1);
        buildingRepository.save(toBuilding2);

        bike = new Bike(fromBuilding, true);
        bikeRepository.save(bike);

        fromTime = new Date(10000000000L);
        fromTimeMilliseconds = fromTime.getTime();

        toTime = new Date(11000000000L);
        toTimeMilliseconds = toTime.getTime();

        fromTime2 = new Date(1000000000000000L);
        fromTimeMilliseconds2 = fromTime2.getTime();

        toTime2 = new Date(1100000000000000L);
        toTimeMilliseconds2 = toTime2.getTime();

        bikeReservation = new BikeReservation(bike, appUser, fromBuilding, toBuilding, fromTime, toTime);

        bikeReservation2 = new BikeReservation(bike, appUser2, fromBuilding, toBuilding2, fromTime2, toTime2);

        request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);

        request2 = new MockHttpServletRequest();
        token = JWT.create()
                .withSubject(appUser2.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request2.addHeader(HEADER_STRING, token);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(bikeReservationService);
    }

    /**
     * Tests the creation of an instance with an invalid user email.
     */
    @Test
    public void testInvalidAppUser() {
        assertEquals(NOT_FOUND, bikeReservationService.add(
                new MockHttpServletRequest(), fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds));
    }

    /**
     * Tests the creation of an instance with an invalid fromBuilding.
     */
    @Test
    public void testInvalidFromBuilding() {
        assertEquals(BUILDING_NOT_FOUND,
                bikeReservationService.add(request, 0, toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds));
    }

    /**
     * Tests the creation of an instance with an invalid fromBuilding.
     */
    @Test
    public void testInvalidToBuilding() {
        assertEquals(BUILDING_NOT_FOUND,
                bikeReservationService.add(request, fromBuilding.getId(), 0, fromTimeMilliseconds, toTimeMilliseconds));
    }

    /**
     * Tests the saving and retrieval of an instance of BikeReservation.
     */
    @Test
    public void testAdd() {
        assertEquals(ADDED,
              bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds));
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        int id = bikeReservationService.all().get(0).getId();
        assertNotNull(bikeReservationService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(RESERVATION_NOT_FOUND, bikeReservationService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        int id = bikeReservationService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, bikeReservationService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the bike by using the service.
     */
    @Test
    public void testChangeBike() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        bike2 = new Bike(fromBuilding, false);
        bikeRepository.save(bike2);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(fromTime, bikeReservationService.find(id).getToTime());
        bikeReservationService.update(id, "totime", ((Long) fromTime.getTime()).toString());
        assertEquals(fromTime, bikeReservationService.find(id).getToTime());
    }

    /**
     * Tests the change of the active by using the service.
     */
    @Test
    public void testChangeActive() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        int id = bikeReservationService.all().get(0).getId();
        assertTrue(bikeReservationService.find(id).isActive());
        bikeReservationService.update(id, "active", "false");
        assertFalse(bikeReservationService.find(id).isActive());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        bikeReservationService.add(request2, fromBuilding.getId(), toBuilding2.getId(), fromTimeMilliseconds2, toTimeMilliseconds2);
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
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        int id = bikeReservationService.all().get(0).getId();
        assertEquals(EXECUTED, bikeReservationService.delete(id));
        assertEquals(0, bikeReservationService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(RESERVATION_NOT_FOUND, bikeReservationService.delete(0));
    }

    /**
     * Tests the retrieval of past bike reservations for the user that sends the request.
     */
    @Test
    public void testGetPastBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        assertEquals(Collections.singletonList(bikeReservation), bikeReservationService.past(request));
    }

    /**
     * Tests the retrieval of past bike reservations for a non-existent user.
     */
    @Test
    public void testGetNonExistentPastBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), bikeReservationService.past(request));
    }

    /**
     * Tests the retrieval of future bike reservations for the user that sends the request.
     */
    @Test
    public void testGetFutureBikeReservations() {
        bikeReservationService.add(request2, fromBuilding.getId(), toBuilding2.getId(), fromTimeMilliseconds2, toTimeMilliseconds2);
        assertEquals(Collections.singletonList(bikeReservation2), bikeReservationService.future(request2));
    }

    /**
     * Tests the retrieval of future bike reservations for a non-existent user.
     */
    @Test
    public void testGetNonExistentFutureBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), bikeReservationService.future(request));
    }

    /**
     * Tests the retrieval of active bike reservations for the user that sends the request.
     */
    @Test
    public void testActiveBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        assertEquals(Collections.singletonList(bikeReservation), bikeReservationService.active(request));
    }

    /**
     * Tests the retrieval of active bike reservations for a non-existent user.
     */
    @Test
    public void testGetNonExistentActiveBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), bikeReservationService.active(request));
    }

    /**
     * Tests the cancellation of bike reservations for the user that sends the request.
     */
    @Test
    public void testCancelBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        assertNotEquals(new ArrayList<>(), bikeReservationService.active(request));
        bikeReservationService.cancel(request, bikeReservationService.all().get(0).getId());
        assertEquals(new ArrayList<>(), bikeReservationService.active(request));
    }

    /**
     * Tests the cancellation of bike reservations for a non-existent room reservation.
     */
    @Test
    public void testCancelNonExistentBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(RESERVATION_NOT_FOUND, bikeReservationService.cancel(request, 0));
    }

    /**
     * Tests the cancellation of bike reservations for a non-existent user.
     */
    @Test
    public void testCancelNonExistentUserBikeReservations() {
        bikeReservationService.add(request, fromBuilding.getId(), toBuilding.getId(), fromTimeMilliseconds, toTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(WRONG_USER, bikeReservationService.cancel(request, bikeReservationService.all().get(0).getId()));
    }
}
