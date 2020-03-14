package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.services.BuildingHourService;
import nl.tudelft.oopp.demo.services.BuildingService;

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
public class BuildingHourServiceTest {
    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingService();
        }
    }

    @TestConfiguration
    static class BuildingHourServiceTestConfiguration {
        @Bean
        public BuildingHourService buildingHourService() {
            return new BuildingHourService();
        }
    }

    @Autowired
    BuildingHourService buildingHourService;

    @Autowired
    BuildingService buildingService;

    BuildingHours buildingHours;
    BuildingHours buildingHours2;

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

        assertEquals(201, buildingService.add("EWI", "Mekelweg", 4));
        assertEquals(201, buildingService.add("EWI2", "Mekelweg2", 42));

        building = buildingService.find("EWI");
        building2 = buildingService.find("EWI2");

        buildingHours = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHours2 = new BuildingHours(2, building2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
    }

    /**
     * Tests the constructor creating a new instance of the entity.
     */
    @Test
    public void testConstructor() {
        assertNotNull(buildingHourService);
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testInvalidBuilding() {
        assertEquals(422, buildingHourService.add(0, buildingHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the creation of an instance with a day not between 1 and 7.
     */
    @Test
    public void testInvalidDay() {
        assertEquals(425, buildingHourService.add(building.getId(), 8, 1000, 3000));
    }

    /**
     * Tests the creation of an instance with the end time before the start time.
     */
    @Test
    public void testEndBeforeStart() {
        assertEquals(426, buildingHourService.add(building.getId(), buildingHours.getDay(), 3000, 1000));
    }

    /**
     * Tests the creation of an instance that already exists.
     */
    @Test
    public void testSameBuildingTwice() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        assertEquals(427, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the saving and retrieval of an instance of BuildingHourService.
     */
    @Test
    public void testRetrieveOne() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));

        assertEquals(Collections.singletonList(buildingHours), buildingHourService.all());
    }

    /**
     * Tests the iterator of an empty list of BuildingHours.
     */
    @Test
    public void testEmptyIterator() {
        Iterator<BuildingHours> iterator = buildingHourService.all().iterator();
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the iterator of a non-empty list of BuildingHours.
     */
    @Test
    public void testNonEmptyIterator() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));

        Iterator<BuildingHours> iterator = buildingHourService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));

        buildingHours = buildingHourService.all().iterator().next();
        assertNull(buildingHourService.find(5, 1));
    }

    /**
     * Tests the retrieval of multiple instances of BuildingHours.
     */
    @Test
    public void testRetrieveMultiple() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertEquals(2, buildingHourList.size());

    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExisting() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        assertEquals(416, buildingHourService.update(1234, "attr", "val"));
    }

    /**
     * Tests the change of the day by using the service.
     */
    @Test
    public void testChangeDay() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        buildingHourService.update(buildingHours2.getId(), "day", ((Integer) buildingHours.getDay()).toString());

        assertEquals(buildingHours.getDay(), buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()).getDay());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    public void testChangeBuilding() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        buildingHourService.update(buildingHours2.getId(), "buildingid", ((Integer) buildingHours.getBuilding().getId()).toString());

        assertEquals(building, buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()).getBuilding());
    }

    /**
     * Tests the change of the start time by using the service.
     */
    @Test
    public void testChangeStartTime() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        buildingHourService.update(buildingHours2.getId(), "starttimes", ((Integer) buildingHours.getStartTime().toSecondOfDay()).toString());

        assertEquals(buildingHours.getStartTime(),
                buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()).getStartTime());
    }

    /**
     * Tests the change of the end time by using the service.
     */
    @Test
    public void testChangeEndTime() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        buildingHourService.update(buildingHours2.getId(), "endtimes", ((Integer) buildingHours.getEndTime().toSecondOfDay()).toString());

        assertEquals(buildingHours.getEndTime(), buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()).getEndTime());
    }

    /**
     * Tests the rejection of the modification of an instance that would make it equal to another instance.
     */
    @Test
    public void testRejectEqualInstances() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        buildingHourService.update(buildingHours2.getId(), "buildingid", ((Integer) buildingHours.getBuilding().getId()).toString());

        assertEquals(427, buildingHourService.update(buildingHours2.getId(), "day", ((Integer) buildingHours.getDay()).toString()));
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDeleteOne() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay());

        buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertEquals(1, buildingHourList.size());
    }

    /**
     * Tests the deletion of the correct instance.
     */
    @Test
    public void testDeleteSearch() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay());

        buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertNull(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()));
        assertNotNull(buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));
    }


    /**
     * Tests the deletion of a non-existent instance.
     */
    @Test
    public void testDeleteNonExistent() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertEquals(404, buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay() + 1));
    }
}