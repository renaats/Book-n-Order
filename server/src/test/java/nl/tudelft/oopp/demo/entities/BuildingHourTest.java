package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.repositories.BuildingHourRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the BuildingHour entity.
 */
@DataJpaTest
public class BuildingHourTest {
    @Autowired
    private BuildingHourRepository buildingHourRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Building building2;
    BuildingHours buildingHours;
    BuildingHours buildingHours2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        buildingHours = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHourRepository.save(buildingHours);
    }

    /**
     * Tests the constructor of the BuildingHours class
     */
    @Test
    public void testConstructor() {
        buildingHours2 = new BuildingHours();
        assertNotNull(buildingHours2);
    }

    /**
     * Tests the saving and retrieval of an instance of BuildingHours.
     */
    @Test
    public void testSaveAndRetrieveBuildingHours() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(buildingHours, buildingHours2);
    }

    /**
     * Tests the getter for the day field.
     */
    @Test
    public void testDayGetter() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(1, buildingHours2.getDay());
    }

    /**
     * Tests the getter for the building field.
     */
    @Test
    public void testBuildingGetter() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(building, buildingHours2.getBuilding());
    }

    /**
     * Tests the getter for the startTime field.
     */
    @Test
    public void testStartTimeGetter() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(LocalTime.ofSecondOfDay(1000), buildingHours2.getStartTime());
    }

    /**
     * Tests the getter for the endTime field.
     */
    @Test
    public void testEndTimeGetter() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(LocalTime.ofSecondOfDay(3000), buildingHours2.getEndTime());
    }

    /**
     * Tests the the change of the day by using a setter.
     */
    @Test
    public void testChangeDay() {
        buildingHours2 = new BuildingHours(2, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setDay(1);
        assertEquals(buildingHours, buildingHours2);
    }

    /**
     * Tests the the change of the building by using a setter.
     */
    @Test
    public void testChangeBuilding() {
        buildingHours2 = new BuildingHours(1, building2, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setBuilding(building);
        assertEquals(buildingHours, buildingHours2);
    }

    /**
     * Tests the the change of the start time by using a setter.
     */
    @Test
    public void testChangeStartTime() {
        buildingHours2 = new BuildingHours(1, building, LocalTime.ofSecondOfDay(2000), LocalTime.ofSecondOfDay(3000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        assertEquals(buildingHours, buildingHours2);
    }


    /**
     * Tests the the change of the end time by using a setter.
     */
    @Test
    public void testChangeEndTime() {
        buildingHours2 = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(4000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertEquals(buildingHours, buildingHours2);
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        buildingHourRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}