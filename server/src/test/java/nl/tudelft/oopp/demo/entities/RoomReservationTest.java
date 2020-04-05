package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the RoomReservation entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomReservationTest {
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    RoomReservation roomReservation;
    RoomReservation roomReservation2;
    Room room;
    AppUser appUser;
    Building building;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.saveAndFlush(building);
        room = new Room("Ampere", building, "CSE", true, true, 300, 250, "OPEN");
        room.setRoomReservations(new HashSet<>());
        roomRepository.saveAndFlush(room);

        appUser = new AppUser("R.Jursevskis@student.tudelft.nl", "1234", "Renats", "Jursevskis", "EWI", "CSE");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        roomReservation = new RoomReservation(roomRepository.findAll().get(0), userRepository.findAll().get(0),
                new Date(10000000000L), new Date(11000000000L));
        roomReservationRepository.saveAndFlush(roomReservation);
        roomReservation = roomReservationRepository.findAll().get(0);
    }

    /**
     * Tests the constructor of the RoomReservation class
     */
    @Test
    public void testConstructor() {
        assertNotNull(roomReservation);
    }

    /**
     * Tests the saving and retrieval of an instance of RoomReservation.
     */
    @Test
    public void testSaveAndRetrieveRoomReservation() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(roomReservation, roomReservation2);
    }

    /**
     * Tests the getter for the user field.
     */
    @Test
    public void testUserGetter() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(appUser, roomReservation2.getAppUser());
    }

    /**
     * Tests the getter for the room field.
     */
    @Test
    public void testRoomGetter() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(room, roomReservation2.getRoom());
    }

    /**
     * Tests the getter for the fromTime field.
     */
    @Test
    public void testFromTimeGetter() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(new Date(10000000000L), roomReservation2.getFromTime());
    }

    /**
     * Tests the getter for the toTime field.
     */
    @Test
    public void testToTimeGetter() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(new Date(11000000000L), roomReservation2.getToTime());
    }

    /**
     * Tests the getter for the active field.
     */
    @Test
    public void testActiveGetter() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertTrue(roomReservation2.isActive());
    }

    /**
     * Tests the the change of the user by using a setter.
     */
    @Test
    public void testChangeUser() {
        roomReservation2 = new RoomReservation();
        assertNull(roomReservation2.getAppUser());
        roomReservation2.setAppUser(appUser);
        assertEquals(appUser, roomReservation2.getAppUser());
    }

    /**
     * Tests the the change of the room by using a setter.
     */
    @Test
    public void testChangeRoom() {
        roomReservation2 = new RoomReservation();
        assertNull(roomReservation2.getRoom());
        roomReservation2.setRoom(room);
        assertEquals(room, roomReservation2.getRoom());
    }

    /**
     * Tests the the change of the fromTime by using a setter.
     */
    @Test
    public void testChangeFromTime() {
        roomReservation2 = new RoomReservation();
        assertNull(roomReservation2.getFromTime());
        roomReservation2.setFromTime(new Date(10000000000L));
        assertEquals(new Date(10000000000L), roomReservation2.getFromTime());
    }

    /**
     * Tests the change of the toTime by using a setter.
     */
    @Test
    public void testChangeToTime() {
        roomReservation2 = new RoomReservation();
        assertNull(roomReservation2.getToTime());
        roomReservation2.setToTime(new Date(11000000000L));
        assertEquals(new Date(11000000000L), roomReservation2.getToTime());
    }

    /**
     * Tests the change of active by using a setter.
     */
    @Test
    public void testChangeActive() {
        roomReservation2 = new RoomReservation();
        assertTrue(roomReservation2.isActive());
        roomReservation2.setActive(false);
        assertFalse(roomReservation2.isActive());
    }

    /**
     * Tests the roomReservation setter and getter for a room.
     */
    @Test
    public void testRoomHasReservations() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertTrue(room.hasRoomReservations());
        assertTrue(room.getRoomReservations().contains(roomReservation));
    }

    /**
     * Tests whether a reservation would be overlapping.
     */
    @Test
    public void testOverlappingReservation() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertTrue(room.hasRoomReservationBetween(new Date(10500000000L), new Date(12000000000L)));
        assertTrue(room.hasRoomReservationBetween(new Date(9900000000L), new Date(10500000000L)));
    }

    /**
     * Tests whether two reservation would be inside each other.
     */
    @Test
    public void testReservationInsideAnother() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertTrue(room.hasRoomReservationBetween(new Date(10500000000L), new Date(10600000000L)));
        assertTrue(room.hasRoomReservationBetween(new Date(9900000000L), new Date(12000000000L)));
    }

    /**
     * Tests the case where two reservations share an end point.
     */
    @Test
    public void testReservationsShareEndPoint() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertFalse(room.hasRoomReservationBetween(new Date(9000000000L), new Date(10000000000L)));
        assertFalse(room.hasRoomReservationBetween(new Date(11000000000L), new Date(12000000000L)));
    }

    /**
     * Tests the cases where fromTime is later than toTime and a reservation completely overlaps.
     */
    @Test
    public void testIllegalReservation() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertTrue(room.hasRoomReservationBetween(new Date(10500000000L), new Date(1050000L)));
        assertTrue(room.hasRoomReservationBetween(new Date(10000000000L), new Date(11000000000L)));
    }

    /**
     * Tests whether two instances of RoomReservation are equal.
     */
    @Test
    public void testEqualRoomReservations() {
        roomReservation2 = new RoomReservation(room, appUser, new Date(10000000000L), new Date(11000000000L));
        assertEquals(roomReservation, roomReservation2);
        assertNotSame(roomReservation, roomReservation2);
    }

    /**
     * Tests the addition and retrieval of room reservations for a user.
     */
    @Test
    public void testUserReservations() {
        HashSet<RoomReservation> roomReservations = new HashSet<>();
        roomReservations.add(roomReservation);
        appUser.setRoomReservations(roomReservations);
        assertEquals(1, appUser.getRoomReservations().size());
        assertTrue(appUser.getRoomReservations().contains(roomReservation));
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        roomReservationRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}