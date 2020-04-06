package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Room entity.
 */
@DataJpaTest
public class RoomTest {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Room room;
    Room room2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        room = new Room("Ampere", building, "CSE", true, true, 300, 250, "Closed");

        roomRepository.save(room);
    }

    /**
     * Tests the constructor of the RoomReservation class
     */
    @Test
    public void testConstructor() {
        room2 = new Room();
        assertNotNull(room2);
    }

    /**
     * Tests the saving and retrieval of an instance of Room.
     */
    @Test
    public void testSaveAndRetrieveRoom() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(room, room2);
    }

    /**
     * Tests the getter for the name field.
     */
    @Test
    public void testNameGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals("Ampere", room2.getName());
    }

    /**
     * Tests the getter for the building field.
     */
    @Test
    public void testBuildingGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(building, room2.getBuilding());
    }

    /**
     * Tests the getter for the studySpecific field.
     */
    @Test
    public void testStudySpecificGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals("CSE", room2.getStudySpecific());
    }

    /**
     * Tests the getter for the projector field.
     */
    @Test
    public void testProjectorGetter() {
        room2 = roomRepository.findAll().get(0);
        assertTrue(room2.isProjector());
    }

    /**
     * Tests the getter for the screen field.
     */
    @Test
    public void testScreenGetter() {
        room2 = roomRepository.findAll().get(0);
        assertTrue(room2.isScreen());
    }

    /**
     * Tests the getter for the capacity field.
     */
    @Test
    public void testCapacityGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(300, room2.getCapacity());
    }

    /**
     * Tests the getter for the plugs field.
     */
    @Test
    public void testPlugsGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(250, room2.getPlugs());
    }

    /**
     * Tests the getter for the status field.
     */
    @Test
    public void testStatusGetter() {
        room2 = roomRepository.findAll().get(0);
        assertEquals("Closed", room2.getStatus());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals("Boole", room.getName());
        room.setName("Boole");
        assertEquals("Boole", room.getName());
    }

    /**
     * Tests the the change of the building by using a setter.
     */
    @Test
    public void testChangeBuilding() {
        assertNotNull(room.getBuilding());
        room.setBuilding(null);
        assertNull(room.getBuilding());
    }

    /**
     * Tests the the change of the studySpecific by using a setter.
     */
    @Test
    public void testChangeStudySpecific() {
        assertNotEquals("", room.getStudySpecific());
        room.setStudySpecific("");
        assertEquals("", room.getStudySpecific());
    }

    /**
     * Tests the the change of the projector by using a setter.
     */
    @Test
    public void testChangeProjector() {
        assertTrue(room.isProjector());
        room.setProjector(false);
        assertFalse(room.isProjector());
    }

    /**
     * Tests the the change of the screen by using a setter.
     */
    @Test
    public void testChangeScreen() {
        assertTrue(room.isScreen());
        room.setScreen(false);
        assertFalse(room.isScreen());
    }

    /**
     * Tests the the change of the capacity by using a setter.
     */
    @Test
    public void testChangeCapacity() {
        assertNotEquals(400, room.getCapacity());
        room.setCapacity(400);
        assertEquals(400, room.getCapacity());
    }

    /**
     * Tests the the change of the plugs by using a setter.
     */
    @Test
    public void testChangePlugs() {
        assertNotEquals(300, room.getPlugs());
        room.setPlugs(300);
        Set<RoomReservation> roomReservationSet = new HashSet<>();
        room.setRoomReservations(roomReservationSet);
        assertEquals(300, room.getPlugs());
    }

    /**
     * Tests the the change of the status by using a setter.
     */
    @Test
    public void testChangeStatus() {
        assertNotEquals("Open", room.getStatus());
        room.setStatus("Open");
        assertEquals("Open", room.getStatus());
    }

    /**
     * Tests the addition of a room for a building.
     */
    @Test
    public void testRoomReservations() {
        Set<RoomReservation> roomReservationSet = new HashSet<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservationSet.add(roomReservation);
        room.setRoomReservations(roomReservationSet);
        assertEquals(roomReservationSet, room.getRoomReservations());
        assertTrue(room.hasRoomReservations());
    }

    /**
     * Tests the removal of a room for a building.
     */
    @Test
    public void testRoomReservationsRemove() {
        Set<RoomReservation> roomReservationSet = new HashSet<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservationSet.add(roomReservation);
        room.setRoomReservations(roomReservationSet);
        room.getRoomReservations().remove(roomReservation);
        assertFalse(room.hasRoomReservations());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}