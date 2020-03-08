package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

        rooms = new HashSet<>();
        rooms.add(room);
    }

    /** Tests the constructor of the Building class
     */
    @Test
    public void testConstructor(){
        building2 = new Building();
        assertNotNull(building2);
    }

    /** Tests the getters of the Building class
     */
    @Test
    public void testGetters() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals("EWI", building2.getName());
        assertEquals("Mekelweg", building2.getStreet());
        assertEquals(4, building2.getHouseNumber());
        assertEquals(building.getId(), building2.getId());
        assertEquals(building.getRooms(), building2.getRooms());
        assertFalse(building.hasRooms());
    }

    /** Tests the setters of the Building class
     */
    @Test
    public void testSetters(){
        building2 = new Building();
        building2.setName("Pulse");
        building2.setStreet("Landbergstraat");
        building2.setHouseNumber(19);
        building2.setRooms(new HashSet<Room>());
        assertEquals(building2.getName(),"Pulse");
        assertEquals(building2.getStreet(),"Landbergstraat");
        assertEquals(building2.getHouseNumber(),19);
        assertNotNull(building2.getRooms());
    }

    /** Tests retrieving and saving data from the BuildingRepository.
     */
    @Test
    public void saveAndRetrieveBuilding() {
        building2 = buildingRepository.findAll().get(0);
        assertEquals(building, building2);
    }

    /** Tests the methods connected with the Set of rooms of the class Building.
     */
    @Test
    public void testRooms() {
        building.setRooms(rooms);
        assertEquals(rooms, building.getRooms());
        assertTrue(building.hasRooms());
        assertTrue(building.hasRoomWithName("Ampere"));
        assertFalse(building.hasRoomWithName("Boole"));
        building.getRooms().remove(roomRepository.findAll().get(0));
        assertFalse(building.hasRooms());
    }

    /** Tests whether two objects are of instance Building and whether they are equal.
     */
    @Test
    public void testEqualBuildings() {
        building2 = new Building();
        assertNotEquals(building,building2);
        building2.setName("EWI");
        assertNotEquals(building,building2);
        building2.setStreet("Mekelweg");
        assertNotEquals(building,building2);
        building2.setHouseNumber(4);
        assertEquals(building, building2);
        assertNotSame(building, building2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}