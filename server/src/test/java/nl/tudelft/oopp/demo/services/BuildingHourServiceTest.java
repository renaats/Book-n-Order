package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_DAY;
import static nl.tudelft.oopp.demo.config.Constants.END_BEFORE_START;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_DAY;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the BuildingHourService service.
 */
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
    BuildingHours buildingHoursSpecial;
    BuildingHours buildingHoursSpecial2;

    Building building;
    Building building2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);

        buildingService.add("EWI", "Mekelweg", "EWI", 4);
        buildingService.add("EWI2", "Mekelweg2", "EWI", 42);

        building = buildingService.find("EWI");
        building2 = buildingService.find("EWI2");

        buildingHours = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHours2 = new BuildingHours(2, building2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
        buildingHoursSpecial =
                new BuildingHours(BuildingHourService.parse(939600000), building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHoursSpecial2 =
                new BuildingHours(BuildingHourService.parse(1026000000), building2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
    }

    /**
     * Tests the constructor creating a new instance of the service.
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
        assertEquals(BUILDING_NOT_FOUND, buildingHourService.add(0, buildingHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the creation of an instance with a day not between 1 and 7.
     */
    @Test
    public void testInvalidDay() {
        assertEquals(INVALID_DAY, buildingHourService.add(building.getId(), 8, 1000, 3000));
    }

    /**
     * Tests the creation of an instance with the end time before the start time.
     */
    @Test
    public void testEndBeforeStart() {
        assertEquals(END_BEFORE_START, buildingHourService.add(building.getId(), buildingHours.getDay(), 3000, 1000));
    }

    /**
     * Tests the creation of an instance that already exists.
     */
    @Test
    public void testSameBuildingTwice() {
        assertEquals(ADDED, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        assertEquals(DUPLICATE_DAY, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the saving and retrieval of an instance of BuildingHours.
     */
    @Test
    public void testRetrieveOne() {
        assertEquals(ADDED, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
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
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);

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
        assertEquals(ADDED, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));

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

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        assertEquals(2, buildingHourList.size());

    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExisting() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);
        assertEquals(ID_NOT_FOUND, buildingHourService.update(1234, "attr", "val"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        int id = buildingHourService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, buildingHourService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the day by using the service.
     */
    @Test
    public void testChangeDay() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNull(buildingHourService.find(buildingHours2.getBuilding().getId(), 939600000));
        buildingHourService.update(buildingHours2.getId(), "day", ((Long) buildingHours.getDay()).toString());
        assertEquals(buildingHourService.find(buildingHours.getBuilding().getId(),939600000).getDay(),
                buildingHourService.find(buildingHours2.getBuilding().getId(), 939600000).getDay());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    public void testChangeBuilding() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), 939600000),
                buildingHourService.find(buildingHours2.getBuilding().getId(), 1026000000));

        buildingHourService.update(buildingHours2.getId(), "buildingid", buildingHours.getBuilding().getId().toString());

        assertEquals(building, buildingHourService.find(buildingHours.getBuilding().getId(),939600000).getBuilding());
    }

    /**
     * Tests the change of the start time by using the service.
     */
    @Test
    public void testChangeStartTime() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), 939600000),
                buildingHourService.find(buildingHours2.getBuilding().getId(), 1026000000));

        buildingHourService.update(buildingHours2.getId(), "starttimes", ((Integer) buildingHours.getStartTime().toSecondOfDay()).toString());

        assertEquals(buildingHours.getStartTime(),
                buildingHourService.find(buildingHours.getBuilding().getId(), 939600000).getStartTime());
    }

    /**
     * Tests the change of the end time by using the service.
     */
    @Test
    public void testChangeEndTime() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), 939600000),
                buildingHourService.find(buildingHours2.getBuilding().getId(), 1026000000));

        buildingHourService.update(buildingHours2.getId(), "endtimes", ((Integer) buildingHours.getEndTime().toSecondOfDay()).toString());

        assertEquals(buildingHours.getEndTime(), buildingHourService.find(buildingHours.getBuilding().getId(), 939600000).getEndTime());
    }

    /**
     * Tests the rejection of the modification of an instance that would make it equal to another instance.
     */
    @Test
    public void testRejectEqualInstances() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), 939600000),
                buildingHourService.find(buildingHours2.getBuilding().getId(), 1026000000));

        buildingHourService.update(buildingHours2.getId(), "buildingid", buildingHours.getBuilding().getId().toString());

        assertEquals(DUPLICATE_DAY, buildingHourService.update(buildingHours2.getId(), "day", ((Long) buildingHours.getDay()).toString()));
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDeleteOne() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay());

        buildingHourList = new ArrayList<>(buildingHourService.all());
        assertEquals(1, buildingHourList.size());
    }

    /**
     * Tests the deletion of the correct instance.
     */
    @Test
    public void testDeleteSearch() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay());

        assertNull(buildingHourService.find(buildingHours.getBuilding().getId(), 939600000));
        assertNotNull(buildingHourService.find(buildingHours2.getBuilding().getId(), 1026000000));
    }


    /**
     * Tests the deletion of a non-existent instance.
     */
    @Test
    public void testDeleteNonExistent() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertEquals(NOT_FOUND, buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay() + 1));
    }

    /**
     * Tests the saving and retrieval of a special hour instance of BuildingHours.
     */
    @Test
    public void testRetrieveSpecial() {
        assertEquals(ADDED, buildingHourService.add(building.getId(), 939600000, 1000, 3000));
        assertEquals(Collections.singletonList(buildingHoursSpecial), buildingHourService.all());
    }

    /**
     * Tests the saving and retrieval of an instance of BuildingHours by building id and day.
     */
    @Test
    public void testFindByBuildingAndDay() {
        assertEquals(ADDED, buildingHourService.add(building.getId(), buildingHoursSpecial.getDay(), 1000, 3000));
        assertEquals(buildingHoursSpecial, buildingHourService.findAdmin(building.getId(), buildingHoursSpecial.getDay()));
    }

    /**
     * Tests the change of the day to a special day by using the service.
     */
    @Test
    public void testChangeDaySpecial() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        buildingHourService.add(building2.getId(), buildingHours2.getDay(), 1000, 3000);

        List<BuildingHours> buildingHourList = new ArrayList<>(buildingHourService.all());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNull(buildingHourService.find(buildingHours2.getBuilding().getId(), 939600000));
        buildingHourService.update(buildingHours2.getId(), "day", ((Long) 939600000L).toString());
        assertEquals(BuildingHourService.parse(939600000), buildingHourService.find(buildingHours2.getBuilding().getId(), 939600000).getDay());
    }

    /**
     * Tests the deletion of a special hour instance.
     */
    @Test
    public void testDeleteSpecial() {
        buildingHourService.add(building.getId(), 939600000, 1000, 3000);
        buildingHoursSpecial = buildingHourService.all().get(0);
        assertEquals(EXECUTED, buildingHourService.delete(buildingHoursSpecial.getBuilding().getId(), buildingHoursSpecial.getDay()));
        assertEquals(0, buildingHourService.all().size());
    }

    /**
     * Tests the returned object when the date has both normal and special hours.
     */
    @Test
    public void testHasBothTypes() {
        buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000);
        assertEquals(buildingHours, buildingHourService.find(building.getId(), 939600000));
        buildingHourService.add(building.getId(), 939600000, 1000, 3000);
        assertEquals(buildingHoursSpecial, buildingHourService.find(building.getId(), 939600000));
    }

    /**
     * Tests the adding of BuildingHours for the current day.
     */
    @Test
    public void testToday() {
        Date date = new Date();
        assertNull(buildingHourService.find(building.getId(), date.getTime()));
        buildingHourService.add(building.getId(), date.getTime(), 1000, 3000);
        assertNotNull(buildingHourService.find(building.getId(), date.getTime()));
    }
}