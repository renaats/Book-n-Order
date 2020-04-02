package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Building entity.
 */
@DataJpaTest
public class BuildingTest {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RoomRepository roomRepository;

    Building building;
    Building building2;
    Room room;
    Set<Room> rooms;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        room = new Room("Ampere", building, "CSE", true, true, 300, 250, "Open");
        roomRepository.save(room);

        rooms = new HashSet<>();
        rooms.add(room);
    }

    /**
     * Tests the constructor of the Building class
     */
    @Test
    public void testConstructor() {
        building2 = new Building();
        assertNotNull(building2);
    }

    /**
     * Tests the saving and retrieval of an instance of Bike.
     */
    @Test
    public void testSaveAndRetrieveBuilding() {
        assertNotEquals(building, building2);
        building2 = buildingRepository.findAll().get(0);
        assertEquals(building, building2);
    }

    /**
     * Tests the getter for the name field.
     */
    @Test
    public void testNameGetter() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals("EWI", building2.getName());
    }

    /**
     * Tests the getter for the street field.
     */
    @Test
    public void testStreetGetter() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals("Mekelweg", building2.getStreet());
    }

    /**
     * Tests the getter for the houseNumber field.
     */
    @Test
    public void testHouseNumberGetter() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals(4, building2.getHouseNumber());
    }

    /**
     * Tests the getter for the room field.
     */
    @Test
    public void testRoomGetter() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals(new HashSet<>(), building2.getRooms());
        assertFalse(building.hasRooms());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals(building.getName(),"Pulse");
        building.setName("Pulse");
        assertEquals(building.getName(),"Pulse");
    }

    /**
     * Tests the the change of the street by using a setter.
     */
    @Test
    public void testChangeStreet() {
        assertNotEquals(building.getStreet(),"Landbergstraat");
        building.setStreet("Landbergstraat");
        assertEquals(building.getStreet(),"Landbergstraat");
    }

    /**
     * Tests the the change of the house number by using a setter.
     */
    @Test
    public void testChangeHouseNumber() {
        assertNotEquals(building.getHouseNumber(),19);
        building.setHouseNumber(19);
        assertEquals(building.getHouseNumber(),19);
    }

    /**
     * Tests the the change of the rooms by using a setter.
     */
    @Test
    public void testChangeRooms() {
        assertNotEquals(rooms, building.getRooms());
        building.setRooms(rooms);
        assertEquals(rooms, building.getRooms());
    }

    /**
     * Tests the hasRoomWithName method.
     */
    @Test
    public void testHasRoom() {
        building.setRooms(rooms);
        assertTrue(building.hasRoomWithName("Ampere"));
        assertFalse(building.hasRoomWithName("Boole"));
        building.getRooms().remove(roomRepository.findAll().get(0));
    }

    /**
     * Tests the removal of rooms.
     */
    @Test
    public void testRemoveRoom() {
        building.setRooms(rooms);
        assertTrue(building.hasRooms());
        building.getRooms().remove(roomRepository.findAll().get(0));
        assertFalse(building.hasRooms());
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