package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.save(building);

        building2 = new Building();
        building2.setName("EWI");
        building2.setStreet("Mekelweg");
        building2.setHouseNumber(4);
        buildingRepository.save(building2);

        buildingHours = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHourRepository.save(buildingHours);
    }

    @Test
    public void saveAndRetrieveBuildingHours() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(buildingHours, buildingHours2);
    }

    @Test
    public void testGetters() {
        buildingHours2 = buildingHourRepository.findByBuilding_IdAndDay(building.getId(), 1);
        assertEquals(building, buildingHours2.getBuilding());
        assertEquals(1, buildingHours2.getDay());
        assertEquals(LocalTime.ofSecondOfDay(1000), buildingHours2.getStartTime());
        assertEquals(LocalTime.ofSecondOfDay(3000), buildingHours2.getEndTime());
    }

    @Test
    public void testEqualBuildingHours() {
        buildingHours2 = new BuildingHours();
        buildingHours2.setDay(2);
        buildingHours2.setBuilding(building2);
        buildingHours2.setStartTime(LocalTime.ofSecondOfDay(2000));
        buildingHours2.setEndTime(LocalTime.ofSecondOfDay(4000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setDay(1);
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setBuilding(building);
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        assertNotEquals(buildingHours, buildingHours2);
        buildingHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertEquals(buildingHours, buildingHours2);
        assertNotSame(buildingHours, buildingHours2);
    }

    @AfterEach
    public void cleanup() {
        buildingHourRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}