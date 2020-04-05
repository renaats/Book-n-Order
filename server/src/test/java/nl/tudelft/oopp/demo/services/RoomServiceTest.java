package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ROOM_NOT_FOUND;

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
import nl.tudelft.oopp.demo.specifications.ServiceHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
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

    @TestConfiguration
    static class ServiceHelperTestConfiguration {
        @Bean
        public ServiceHelper serviceHelper() {
            return new ServiceHelper();
        }
    }

    @TestConfiguration
    static class MenuServiceTestConfiguration {
        @Bean
        public MenuService menuService() {
            return new MenuService();
        }
    }

    @TestConfiguration
    static class UserTestConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    RoomService roomService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    ServiceHelper serviceHelper;

    @Autowired
    UserService userService;


    Building building;
    Building building2;
    Room room;
    Room room2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        buildingService.add("EWI", "Mekelweg", "EWI", 4);
        buildingService.add("EWI2", "Mekelweg2", "EWI", 42);

        List<Building> buildings = new ArrayList<>(buildingService.all());
        building = buildings.get(0);
        building2 = buildings.get(1);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setStudySpecific("CSE");
        room.setScreen(true);
        room.setProjector(true);
        room.setCapacity(200);
        room.setPlugs(200);
        room.setStatus("Open");

        room2 = new Room();
        room2.setName("Boole");
        room2.setBuilding(building2);
        room2.setStudySpecific("CSE2");
        room2.setScreen(true);
        room2.setProjector(true);
        room2.setCapacity(200);
        room2.setPlugs(200);
        room2.setStatus("Closed");
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
        assertEquals(ADDED, roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open"));
        assertEquals(Collections.singletonList(room), roomService.all());
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testCreateIllegalBuilding() {
        assertEquals(BUILDING_NOT_FOUND, roomService.add("Not an actual room", "Study", true, true, -3, 300, 200, "Open"));
    }

    /**
     * Tests the creation of an instance with an duplicate room.
     */
    @Test
    public void testCreateDuplicateRoom() {
        assertEquals(ADDED, roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open"));
        Set<Room> rooms = new HashSet<>();
        rooms.add(roomService.all().get(0));
        building.setRooms(rooms);
        assertEquals(DUPLICATE_NAME, roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open"));
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotNull(roomService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(ROOM_NOT_FOUND, roomService.update(-3, "This is not an actual id of a room.", "value"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, roomService.update(id, "Non existent attribute", "value"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotEquals("Boole", roomService.all().get(0).getName());
        roomService.update(id, "name", "Boole");
        assertEquals("Boole", roomService.all().get(0).getName());
    }

    /**
     * Tests the change of the studySpecific by using the service.
     */
    @Test
    public void testChangeStudySpecific() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotEquals("EE", roomService.all().get(0).getStudySpecific());
        roomService.update(id, "studyspecific", "EE");
        assertEquals("EE", roomService.all().get(0).getStudySpecific());
    }

    /**
     * Tests the change of screen by using the service.
     */
    @Test
    public void testChangeScreen() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotEquals(400, roomService.all().get(0).getCapacity());
        roomService.update(id, "capacity", "400");
        assertEquals(400, roomService.all().get(0).getCapacity());
    }

    /**
     * Tests the change of the plugs by using the service.
     */
    @Test
    public void testChangePlugs() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotEquals(400, roomService.all().get(0).getPlugs());
        roomService.update(id, "plugs", "400");
        assertEquals(400, roomService.all().get(0).getPlugs());
    }

    /**
     * Tests the change of the plugs by using the service.
     */
    @Test
    public void testChangeStatus() {
        roomService.add("Ampere", "EWI", false, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertNotEquals("Closed", roomService.all().get(0).getStatus());
        roomService.update(id, "status", "Closed");
        assertEquals("Closed", roomService.all().get(0).getStatus());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    public void testChangeBuilding() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertEquals(BUILDING_NOT_FOUND, roomService.update(id, "buildingid", "-3"));
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
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
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        int id = roomService.all().get(0).getId();
        assertEquals(EXECUTED, roomService.delete(id));
        assertEquals(0, roomService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(ROOM_NOT_FOUND, roomService.delete(0));
    }

    /**
     * Tests the finding of a room by name.
     */
    @Test
    public void testFindByName() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        Room newRoom = roomService.findByName("Ampere");
        assertEquals(room, newRoom);
    }

    /**
     * Tests the searching of a room by name.
     */
    @Test
    public void testSearchByName() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("name:Ampere");
        assertTrue(rooms.contains(room));
        assertFalse(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by studySpecific.
     */
    @Test
    public void testSearchByStudySpecific() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("studySpecific:CSE2");
        assertFalse(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by the boolean screen.
     */
    @Test
    public void testSearchByScreen() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("screen:true");
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by the boolean projector.
     */
    @Test
    public void testSearchByProjector() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("projector:true");
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room in a specific building.
     */
    @Test
    public void testSearchByBuildingId() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("building:" + building.getId());
        assertTrue(rooms.contains(room));
        assertFalse(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by the capacity.
     */
    @Test
    public void testSearchByCapacity() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("capacity>150");
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by the number of plugs in a room.
     */
    @Test
    public void testSearchByNumberOfPlugs() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("plugs>150");
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by status.
     */
    @Test
    public void testSearchByStatus() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("status:Open");
        assertTrue(rooms.contains(room));
        assertFalse(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by two parameters.
     */
    @Test
    public void testCompoundSearch() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("name:Boole,plugs>150");
        assertFalse(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room by many parameters.
     */
    @Test
    public void testCompoundSearchWithManyArguments() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("name:Boole,plugs>150,studySpecific:CSE2,projector:true,plugs<300");
        assertFalse(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Tests the searching of a room with the staff role.
     */
    @Test
    @WithMockUser(username = "user@tudelft.nl", roles = {"USER", "STAFF"})
    public void testSearchStaffRole() {
        userService.add("user@tudelft.nl", "123", "User", "User", "1", "CSE2");
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Closed");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Staff-Only");
        room2.setStatus("Staff-Only");
        assertEquals(Collections.singletonList(room2), roomService.search(""));
    }

    /**
     * Tests the searching of a room with the staff role.
     */
    @Test
    @WithMockUser(username = "user@student.tudelft.nl")
    public void testSearchUserRole() {
        userService.add("user@student.tudelft.nl", "123", "User", "User", "1", "CSE");
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Staff-Only");
        assertEquals(Collections.singletonList(room), roomService.search(""));
    }

    /**
     * Tests the searching of a nonexistent room.
     */
    @Test
    public void testSearchOfNonexistentRoom() {
        roomService.add("Ampere", "CSE", true, true, building.getId(), 200, 200, "Open");
        roomService.add("Boole", "CSE2", true, true, building2.getId(), 200, 200, "Closed");
        List<Room> rooms = roomService.search("plugs<150");
        assertEquals(rooms.size(), 0);
    }
}