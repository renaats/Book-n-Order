package nl.tudelft.oopp.demo;

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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

    /** Tests the constructor of the Room class.
     */
    @Test
    public void testConstructor(){
        room2 = new Room();
        assertNotNull(room2);
    }

    /** Tests the getters of the Room class.
     */
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

    /** Tests the setters of the Room class.
     */
    @Test
    public void testSetters(){
        Set<RoomReservation> roomReservationSet = new HashSet<RoomReservation>();
        room2 = new Room();
        room2.setName("Boole");
        room2.setBuilding(buildingRepository.findAll().get(0));
        room2.setFaculty("EEMCS");
        room2.setFacultySpecific(false);
        room2.setProjector(true);
        room2.setScreen(true);
        room2.setNrPeople(300);
        room2.setPlugs(250);
        room2.setRoomReservations(roomReservationSet);
        assertEquals(room2.getName(),"Boole");
        assertEquals(room2.getBuilding(), buildingRepository.findAll().get(0));
        assertEquals(room2.getFaculty(),"EEMCS");
        assertFalse(room2.isFacultySpecific());
        assertTrue(room2.isProjector());
        assertTrue(room2.isScreen());
        assertEquals(room2.getNrPeople(),300);
        assertEquals(room2.getPlugs(), 250);
        assertEquals(room2.getRoomReservations(), roomReservationSet);
    }
    
    /** Tests retrieving and saving data from the RoomRepository.
     */
    @Test
    public void saveAndRetrieveRoom() {
        room2 = roomRepository.findAll().get(0);
        assertEquals(room, room2);
    }

    /** Tests the methods connected with the Set of roomReservations of the class Building.
     */
    @Test
    public void testRoomReservations(){
        Set<RoomReservation> roomReservationSet = new HashSet<RoomReservation>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservationSet.add(roomReservation);
        room.setRoomReservations(roomReservationSet);
        assertEquals(room.getRoomReservations(),roomReservationSet);
        assertTrue(room.hasRoomReservations());
        room.getRoomReservations().remove(roomReservation);
        assertFalse(room.hasRoomReservations());
    }

    /** Tests whether two objects are of instance Room and whether they are equal.
     */
    @Test
    public void testEqualRooms() {
        room2 = new Room();
        room2.setName("Ampere");
        assertNotEquals(room, room2);
        room2.setBuilding(building);
        room2.setFaculty("EWI");
        assertNotEquals(room, room2);
        room2.setFacultySpecific(false);
        room2.setScreen(true);
        room2.setProjector(true);
        assertNotEquals(room, room2);
        room2.setNrPeople(300);
        room2.setPlugs(250);
        assertEquals(room, room2);
        assertNotSame(room, room2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}