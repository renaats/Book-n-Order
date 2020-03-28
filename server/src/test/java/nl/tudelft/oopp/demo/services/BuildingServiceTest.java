package nl.tudelft.oopp.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
 * Tests the Building service.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BuildingServiceTest {
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
    BuildingService buildingService;

    @Autowired
    RoomService roomService;

    Building building;
    Building building2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);

        building2 = new Building();
        building2.setName("EWI2");
        building2.setStreet("Mekelweg2");
        building2.setHouseNumber(42);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(buildingService);
    }

    /**
     * Tests the saving and retrieval of an instance of Building.
     */
    @Test
    public void testCreate() {
        assertEquals(201, buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber()));
        assertEquals(Collections.singletonList(building), buildingService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(buildingService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertNotNull(buildingService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(416, buildingService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertEquals(412, buildingService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertNotEquals("Aula", buildingService.find(id).getName());
        buildingService.update(id, "name", "Aula");
        assertEquals("Aula", buildingService.find(id).getName());
    }

    /**
     * Tests duplicate building names.
     */
    @Test
    public void testChangeNameToAlreadyExistingOne() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        buildingService.add(building2.getName(), building2.getStreet(), building2.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertEquals("EWI", buildingService.find(id).getName());
        assertEquals(309, buildingService.update(id, "name", "EWI2"));
    }

    /**
     * Tests the change of the street by using the service.
     */
    @Test
    public void testChangeStreet() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertNotEquals("Drebelweg", buildingService.find(id).getStreet());
        buildingService.update(id, "street", "Drebelweg");
        assertEquals("Drebelweg", buildingService.find(id).getStreet());
    }

    /**
     * Tests the change of the house number by using the service.
     */
    @Test
    public void testChangeHouseNumber() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertNotEquals(7, buildingService.find(id).getHouseNumber());
        buildingService.update(id, "housenumber", "7");
        assertEquals(7, buildingService.find(id).getHouseNumber());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        buildingService.add(building2.getName(), building2.getStreet(), building2.getHouseNumber());
        assertEquals(2, buildingService.all().size());
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(building);
        buildings.add(building2);
        assertEquals(buildings, buildingService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        assertEquals(200, buildingService.delete(id));
        assertEquals(0, buildingService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(404, buildingService.delete(0));
    }

    /**
     * Tests the retrieval of rooms for a building.
     */
    @Test
    public void testHasRooms() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        int id = buildingService.all().get(0).getId();
        roomService.add("Ampere", "EWI", false, true, true, id, 300, 50);
        Set<Room> rooms = new HashSet<>();
        rooms.add(roomService.all().get(0));
        buildingService.find(id).setRooms(rooms);
        assertEquals(rooms, buildingService.rooms(id));
        assertEquals(417, buildingService.delete(id));
    }

    /**
     * Tests the retrieval of rooms for a non-existent building.
     */
    @Test
    public void testRoomsIllegal() {
        assertNull(buildingService.rooms(0));
    }
}