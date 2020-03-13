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

        assertEquals(201, buildingService.add("EWI", "Mekelweg", 4));
        assertEquals(201, buildingService.add("EWI2", "Mekelweg2", 42));

        building = buildingService.find("EWI");
        building2 = buildingService.find("EWI2");

        buildingHours = new BuildingHours(1, building, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        buildingHours2 = new BuildingHours(2, building2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
    }

    @Test
    public void testConstructor() {
        assertNotNull(buildingHourService);
    }

    @Test
    public void testCreate() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        assertEquals(Collections.singletonList(buildingHours), buildingHourService.all());
    }

    @Test
    public void testAll() {
        Iterator<BuildingHours> iterator = buildingHourService.all().iterator();
        assertFalse(iterator.hasNext());
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        iterator = buildingHourService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        buildingHours = buildingHourService.all().iterator().next();
        assertEquals(buildingHours, buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()));
        assertNull(buildingHourService.find(5, 1));
    }

    @Test
    public void testUpdate() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        assertEquals(201, buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000));

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertEquals(2, buildingHourList.size());
        assertEquals(416, buildingHourService.update(1234, "attr", "val"));
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        assertEquals(201, buildingHourService.update(buildingHours2.getId(), "starttimes",
                ((Integer) buildingHours.getStartTime().toSecondOfDay()).toString()));

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        assertEquals(201, buildingHourService.update(buildingHours2.getId(), "endtimes",
                ((Integer) buildingHours.getEndTime().toSecondOfDay()).toString()));

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        assertEquals(201, buildingHourService.update(buildingHours2.getId(), "buildingid",
                ((Integer) buildingHours.getBuilding().getId()).toString()));

        assertNotEquals(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()),
                buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));

        assertEquals(427, buildingHourService.update(buildingHours2.getId(), "day", ((Integer) buildingHours.getDay()).toString()));
    }

    @Test
    public void testDelete() {
        assertEquals(201, buildingHourService.add(building.getId(), buildingHours.getDay(), 1000, 3000));
        assertEquals(201, buildingHourService.add(building2.getId(), buildingHours2.getDay(), 2000, 4000));

        List<BuildingHours> buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertEquals(2, buildingHourList.size());
        buildingHours = buildingHourList.get(0);
        buildingHours2 = buildingHourList.get(1);
        buildingHourService.delete(buildingHourList.get(0).getBuilding().getId(), buildingHourList.get(0).getDay());

        buildingHourList = new ArrayList<>();
        buildingHourService.all().forEach(buildingHourList::add);
        assertEquals(1, buildingHourList.size());
        assertNull(buildingHourService.find(buildingHours.getBuilding().getId(), buildingHours.getDay()));
        assertNotNull(buildingHourService.find(buildingHours2.getBuilding().getId(), buildingHours2.getDay()));
    }

}