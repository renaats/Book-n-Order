package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Date;
import java.util.HashSet;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
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

        appUser = new AppUser();
        appUser.setEmail("R.Jursevskis@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Renats");
        appUser.setSurname("Jursevskis");
        appUser.setFaculty("EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        roomReservation = new RoomReservation();
        roomReservation.setAppUser(userRepository.findAll().get(0));
        roomReservation.setRoom(roomRepository.findAll().get(0));
        roomReservation.setFromTime(new Date(10000000000L));
        roomReservation.setToTime(new Date(11000000000L));
        roomReservationRepository.saveAndFlush(roomReservation);
        roomReservation = roomReservationRepository.findAll().get(0);
    }

    @Test
    public void saveAndRetrieveRoomReservation() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(roomReservation, roomReservation2);
    }

    @Test
    public void testGetters() {
        roomReservation2 = roomReservationRepository.findAll().get(0);
        assertEquals(roomReservation.getAppUser(), roomReservation2.getAppUser());
        assertEquals(roomReservation.getRoom(), roomReservation2.getRoom());
        assertEquals(roomReservation.getFromTime(), roomReservation2.getFromTime());
        assertEquals(roomReservation.getToTime(), roomReservation2.getToTime());
    }

    @Test
    public void testEqualRoomReservations() {
        roomReservation2 = new RoomReservation();
        roomReservation2.setAppUser(appUser);
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