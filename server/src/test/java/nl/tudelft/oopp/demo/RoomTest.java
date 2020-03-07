package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomTest {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Room room;
    Room room2;

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

        roomRepository.save(room);
    }

    @Test
    public void saveAndRetrieveRoom() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(room, room2);
    }

    @Test
    public void testGetters() {
        room2 = roomRepository.findAll().get(0);
        assertEquals("Ampere", room2.getName());
        assertEquals(building, room2.getBuilding());
        assertEquals("EWI", room2.getFaculty());
        assertFalse(room2.isFacultySpecific());
        assertTrue(room2.isProjector());
        assertTrue(room2.isScreen());
        assertEquals(300, room2.getNrPeople());
        assertEquals(250, room2.getPlugs());
        assertTrue(room2.getId() > 0);
    }

    @Test
    public void testEqualRooms() {
        room2 = new Room();
        room2.setName("Ampere");
        room2.setBuilding(building);
        room2.setFaculty("EWI");
        room2.setFacultySpecific(false);
        room2.setScreen(true);
        room2.setProjector(true);
        room2.setNrPeople(300);
        room2.setPlugs(250);
        assertEquals(room, room2);
        assertNotSame(room, room2);
    }

    @AfterEach
    public void cleanup() {
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}