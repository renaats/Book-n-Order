package nl.tudelft.oopp.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.BuildingService;
import nl.tudelft.oopp.demo.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the Room service.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomServiceTest {
    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingService();
        }
    }

    @TestConfiguration
    static class RoomServiceTestConfiguration {
        @Bean
        public RoomService roomService() {
            return new RoomService();
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

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        buildingService.add("EWI", "Mekelweg", 4);
        buildingService.add("EWI2", "Mekelweg2", 42);

        List<Building> buildings = new ArrayList<>(buildingService.all());
        building = buildings.get(0);
        building2 = buildings.get(1);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setCapacity(200);
        room.setPlugs(200);

        room2 = new Room();
        room2.setName("Boole");
        room2.setBuilding(building2);
        room2.setFaculty("EWI2");
        room2.setFacultySpecific(false);
        room2.setScreen(true);
        room2.setProjector(true);
        room2.setCapacity(200);
        room2.setPlugs(200);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(roomService);
    }

    /**
     * Tests the saving and retrieval of an instance of Room.
     */
    @Test
    public void testCreate() {
        assertEquals(201, roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200));
        assertEquals(Collections.singletonList(room), roomService.all());
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testCreateIllegalBuilding() {
        assertEquals(422, roomService.add("Not an actual room", "Faculty", false, true, true, -3, 300, 200));
    }

    /**
     * Tests the creation of an instance with an duplicate room.
     */
    @Test
    public void testCreateDuplicateRoom() {
        assertEquals(201, roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200));
        Set<Room> rooms = new HashSet<>();
        rooms.add(roomService.all().get(0));
        building.setRooms(rooms);
        assertEquals(309, roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(roomService.find(-3));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotNull(roomService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(418, roomService.update(-3, "This is not an actual id of a room.", "value"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertEquals(412, roomService.update(id, "Non existent attribute", "value"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotEquals("Boole", roomService.all().get(0).getName());
        roomService.update(id, "name", "Boole");
        assertEquals("Boole", roomService.all().get(0).getName());
    }

    /**
     * Tests the change of the faculty by using the service.
     */
    @Test
    public void testChangeFaculty() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotEquals("3M", roomService.all().get(0).getFaculty());
        roomService.update(id, "faculty", "3M");
        assertEquals("3M", roomService.all().get(0).getFaculty());
    }

    /**
     * Tests the change of facultySpecific by using the service.
     */
    @Test
    public void testChangeFacultySpecific() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertFalse(roomService.all().get(0).isFacultySpecific());
        roomService.update(id, "facultyspecific", "true");
        assertTrue(roomService.all().get(0).isFacultySpecific());
    }

    /**
     * Tests the change of screen by using the service.
     */
    @Test
    public void testChangeScreen() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertTrue(roomService.all().get(0).isScreen());
        roomService.update(id, "screen", "false");
        assertFalse(roomService.all().get(0).isScreen());
    }

    /**
     * Tests the change of projector by using the service.
     */
    @Test
    public void testChangeProjector() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertTrue(roomService.all().get(0).isProjector());
        roomService.update(id, "projector", "false");
        assertFalse(roomService.all().get(0).isProjector());
    }

    /**
     * Tests the change of the capacity by using the service.
     */
    @Test
    public void testChangeCapacity() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotEquals(400, roomService.all().get(0).getCapacity());
        roomService.update(id, "amountofpeople", "400");
        assertEquals(400, roomService.all().get(0).getCapacity());
    }

    /**
     * Tests the change of the plugs by using the service.
     */
    @Test
    public void testChangePlugs() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotEquals(400, roomService.all().get(0).getPlugs());
        roomService.update(id, "plugs", "400");
        assertEquals(400, roomService.all().get(0).getPlugs());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    public void testChangeBuilding() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertNotEquals(building2, roomService.all().get(0).getBuilding());
        roomService.update(id, "buildingid", building2.getId().toString());
        assertEquals(building2, roomService.all().get(0).getBuilding());
    }

    /**
     * Tests the change of the building to a non-existing building by using the service.
     */
    @Test
    public void testChangeBuildingNonExisting() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertEquals(422, roomService.update(id, "buildingid", "-3"));
    }

    /**
     * Tests the retrieval of room reservations for a non-existing room.
     */
    @Test
    public void testReservationsNonExistentRoom() {
        assertNull(roomService.reservations(0));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        roomService.add("Boole", "EWI2", false, true, true, building2.getId(), 200, 200);
        assertEquals(2, roomService.all().size());
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room2);
        assertEquals(rooms, roomService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        int id = roomService.all().get(0).getId();
        assertEquals(200, roomService.delete(id));
        assertEquals(0, roomService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(418, roomService.delete(0));
    }
}