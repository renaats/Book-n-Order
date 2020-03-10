package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.services.BuildingService;
import nl.tudelft.oopp.demo.services.BuildingServiceImpl;
import nl.tudelft.oopp.demo.services.RoomService;
import nl.tudelft.oopp.demo.services.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomServiceTest {
    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingServiceImpl();
        }
    }

    @TestConfiguration
    static class RoomServiceTestConfiguration {
        @Bean
        public RoomService roomService() {
            return new RoomServiceImpl();
        }
    }

    @Autowired
    RoomService roomService;

    @Autowired
    BuildingService buildingService;


    Building building;
    Building building2;
    Room room;
    Room room2;
    RoomReservation roomReservation;
    Set<RoomReservation> roomReservationSet;
    Set<Room> roomSet;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        buildingService.add("EWI", "Mekelweg", 4);
        buildingService.add("EWI2", "Mekelweg2", 42);

        List<Building> buildings = new ArrayList<>();
        buildingService.all().forEach(buildings::add);
        building = buildings.get(0);
        building2 = buildings.get(1);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setNrPeople(200);
        room.setPlugs(200);

    }

    @Test
    public void testConstructor() {
        assertNotNull(roomService);
    }

    @Test
    public void testCreate() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        assertEquals(Arrays.asList(room), roomService.all());
        assertEquals(422, roomService.add("Not an actual room", "Faculty", false, true, true, -3, 300, 200));
        roomSet = new HashSet<>();
        roomSet.add(room);
        building.setRooms(roomSet);
        assertEquals(309, roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200));
    }

    @Test
    public void testAll() {
        Iterator<Room> iterator = roomService.all().iterator();
        assertFalse(iterator.hasNext());
        roomService.add(room.getName(), room.getFaculty(), false, true, true, building.getId(), room.getNrPeople(), room.getPlugs());
        iterator = roomService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        roomService.add(room.getName(), room.getFaculty(), false, true, true, building.getId(), room.getNrPeople(), room.getPlugs());
        assertEquals(roomService.all().iterator().next(), roomService.find(roomService.all().iterator().next().getId()));
        assertNull(roomService.find(-3));
    }

    @Test
    public void testUpdate() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        roomService.add("Auditorium", "Aula", true, false, false, building2.getId(), 500, 100);
        List<Room> rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(2, rooms.size());
        room = rooms.get(0);
        room2 = rooms.get(1);

        assertEquals(418, roomService.update(-3, "This is not an actual id of a room.", "value"));
        assertEquals(422, roomService.update(room.getId(), "buildingid", "-3"));
        assertEquals(412, roomService.update(room.getId(), "Non existent attribute", "value"));
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "name", room.getName());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "faculty", room.getFaculty());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "facultyspecific", ((Boolean) room.isFacultySpecific()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "screen", ((Boolean) room.isScreen()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "projector", ((Boolean) room.isProjector()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "buildingid", ((Integer) room.getBuilding().getId()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "amountofpeople", ((Integer) room.getNrPeople()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "plugs", ((Integer) room.getPlugs()).toString());
        assertEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
    }

    @Test
    public void testDelete() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        roomService.add("Auditorium", "Aula", true, false, false, building2.getId(), 500, 100);
        List<Room> rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(2, rooms.size());
        room = rooms.get(0);
        room2 = rooms.get(1);
        assertEquals(200, roomService.delete(rooms.get(0).getId()));
        rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(1, rooms.size());
        assertNull(roomService.find(room.getId()));
        assertNotNull(roomService.find(room2.getId()));
        assertEquals(418, roomService.delete(-3));
    }

//    @Test
//    public void testReservations() {
//        roomReservationSet = new HashSet<>();
//        roomReservation = new RoomReservation();
//        roomReservation.setRoom(room);
//        roomReservationSet.add(roomReservation);
//        room.setRoomReservations(roomReservationSet);
//        assertEquals(roomService.reservations(room.getId()), roomReservationSet);
//    }
}