package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    /** Sets up the classes before executing the tests.
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

    @Test
    public void testConstructor() {
        assertNotNull(buildingService);
    }

    @Test
    public void testCreate() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        assertEquals(Arrays.asList(building), buildingService.all());
    }

    @Test
    public void testAll() {
        Iterator<Building> iterator = buildingService.all().iterator();
        assertFalse(iterator.hasNext());
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        iterator = buildingService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        assertEquals(buildingService.all().iterator().next(), buildingService.find(buildingService.all().iterator().next().getId()));
        assertNull(buildingService.find(-3));
    }

    @Test
    public void testUpdate() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        buildingService.add(building2.getName(), building2.getStreet(), building2.getHouseNumber());
        List<Building> buildings = new ArrayList<>();
        buildingService.all().forEach(buildings::add);
        assertEquals(2, buildings.size());
        assertEquals(416, buildingService.update(1234, "attr", "val"));
        building = buildings.get(0);
        building2 = buildings.get(1);

        assertNotEquals(buildingService.find(building.getId()), buildingService.find(building2.getId()));
        buildingService.update(building2.getId(), "name", building.getName());
        assertNotEquals(buildingService.find(building.getId()), buildingService.find(building2.getId()));
        buildingService.update(building2.getId(), "street", building.getStreet());
        assertNotEquals(buildingService.find(building.getId()), buildingService.find(building2.getId()));
        buildingService.update(building2.getId(), "housenumber", ((Integer) building.getHouseNumber()).toString());
        assertEquals(buildingService.find(building.getId()), buildingService.find(building2.getId()));

    }

    @Test
    public void testDelete() {
        buildingService.add(building.getName(), building.getStreet(), building.getHouseNumber());
        buildingService.add(building2.getName(), building2.getStreet(), building2.getHouseNumber());
        List<Building> buildings = new ArrayList<>();
        buildingService.all().forEach(buildings::add);
        assertEquals(2, buildings.size());
        building = buildings.get(0);
        building2 = buildings.get(1);
        buildingService.delete(buildings.get(0).getId());
        buildings = new ArrayList<>();
        buildingService.all().forEach(buildings::add);
        assertEquals(1, buildings.size());
        assertNull(buildingService.find(building.getId()));
        assertNotNull(buildingService.find(building2.getId()));
    }
}