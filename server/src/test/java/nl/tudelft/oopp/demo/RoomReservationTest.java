package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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
    RoomReservation roomReservation3;
    Room room;
    User user;
    Building building;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.saveAndFlush(building);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setNrPeople(300);
        room.setPlugs(250);
        room.setRoomReservations(new HashSet<>());
        roomRepository.saveAndFlush(room);

        user = new User();
        user.setEmail("R.Jursevskis@student.tudelft.nl");
        user.setPassword("1234");
        user.setName("Renats");
        user.setSurname("Jursevskis");
        user.setFaculty("EWI");
        user.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(user);

        roomReservation = new RoomReservation();
        roomReservation.setUser(userRepository.findAll().get(0));
        roomReservation.setRoom(roomRepository.findAll().get(0));
        roomReservation.setFromTime(new Date(10000000000L));
        roomReservation.setToTime(new Date(11000000000L));
        roomReservationRepository.saveAndFlush(roomReservation);
        roomReservation = roomReservationRepository.findAll().get(0);
    }

    /** Tests the constructor of the RoomReservation class
     */
    @Test
    public void testConstructor() {
        assertNotNull(building);
        assertNotNull(room);
        assertNotNull(user);
        assertNotNull(roomReservation);
    }

    /** Tests the getters of the RoomReservation class
     */
    @Test
    public void testGetters() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(roomReservation.getUser(), roomReservation2.getUser());
        assertEquals(roomReservation.getRoom(), roomReservation2.getRoom());
        assertEquals(roomReservation.getFromTime(), roomReservation2.getFromTime());
        assertEquals(roomReservation.getToTime(), roomReservation2.getToTime());
    }

    /** Tests the setters of the RoomReservation class
     */
    @Test
    public void testSetters() {
        roomReservation3 = new RoomReservation();
        Room newRoom = new Room();
        User newUser = new User();
        Date newFromTime = new Date(9900000000L);
        Date newToTime = new Date(1060000000L);
        roomReservation3.setRoom(newRoom);
        roomReservation3.setUser(newUser);
        roomReservation3.setFromTime(newFromTime);
        roomReservation3.setToTime(newToTime);
        assertEquals(roomReservation3.getRoom(), newRoom);
        assertEquals(roomReservation3.getUser(), newUser);
        assertEquals(roomReservation3.getFromTime(), newFromTime);
        assertEquals(roomReservation3.getToTime(), newToTime);
    }

    /** Tests whether there exists a reservation between two dates.
     */
    @Test
    public void testOverlappingReservation() {
        room = roomRepository.findAll().get(0);
        Set<RoomReservation> setOfRoomReservations = new HashSet<RoomReservation>();
        setOfRoomReservations.add(roomReservationRepository.findAll().get(0));
        room.setRoomReservations(setOfRoomReservations);
        assertTrue(room.hasRoomReservations());
        assertTrue(room.getRoomReservations().contains(roomReservation));
        assertTrue(room.hasRoomReservationBetween(new Date(10500000000L), new Date(12000000000L)));
        assertTrue(room.hasRoomReservationBetween(new Date(9900000000L), new Date(10500000000L)));
        assertFalse(room.hasRoomReservationBetween(new Date(10000000000L), new Date(1100000000L)));
        assertTrue(room.hasRoomReservationBetween(new Date(10500000000L), new Date(10600000000L)));
        assertFalse(room.hasRoomReservationBetween(new Date(10500000000L), new Date(1060000000L)));
        assertFalse(room.hasRoomReservationBetween(new Date(11500000000L), new Date(1160000000L)));
        assertFalse(room.hasRoomReservationBetween(new Date(10900000000L), new Date(10500000000L)));
    }
    
    /** Tests retrieving and saving data from the RoomReservationRepository.
     */
    @Test
    public void saveAndRetrieveRoomReservation() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(roomReservation, roomReservation2);
    }

    /** Tests whether two objects are of instance RoomReservation and whether they are equal.
     */
    @Test
    public void testEqualRoomReservations() {
        roomReservation2 = new RoomReservation();
        roomReservation2.setUser(user);
        roomReservation2.setRoom(room);
        roomReservation2.setFromTime(new Date(10000000000L));
        roomReservation2.setToTime(new Date(11000000000L));
        assertEquals(roomReservation, roomReservation2);
        assertNotSame(roomReservation, roomReservation2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        roomReservationRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}