package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BuildingTest {
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Building building2;
    Room room;
    Set<Room> rooms;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.save(building);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setNrPeople(300);
        room.setPlugs(250);

        rooms = new HashSet<>();
        rooms.add(room);
    }

    @Test
    public void saveAndRetrieveBuilding() {
        building2 = buildingRepository.getOne(1);
        assertSame(building, building2);
    }

    @Test
    public void testGetters() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals("EWI", building2.getName());
        assertEquals("Mekelweg", building2.getStreet());
        assertEquals(4, building2.getHouseNumber());
        assertEquals(building.getId(), building2.getId());
        assertFalse(building.hasRooms());
    }

    @Test
    public void testRooms() {
        building.setRooms(rooms);
        assertEquals(rooms, building.getRooms());
        assertTrue(building.hasRooms());
        assertTrue(building.hasRoomWithName("Ampere"));
        assertFalse(building.hasRoomWithName("Boole"));
    }

    @Test
    public void testEqualBuildings() {
        building2 = new Building();
        building2.setName("EWI");
        building2.setStreet("Mekelweg");
        building2.setHouseNumber(4);
        assertEquals(building, building2);
        assertNotSame(building, building2);
    }

    @AfterEach
    public void cleanup() {
        buildingRepository.deleteAll();
    }
}