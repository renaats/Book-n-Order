package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.services.BikeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the BikeService service.
 */
@DataJpaTest
public class BikeServiceTest {
    @TestConfiguration
    static class BikeServiceTestConfiguration {
        @Bean
        public BikeService bikeService() {
            return new BikeService();
        }
    }

    @Autowired
    BikeService bikeService;

    @Autowired
    BuildingRepository buildingRepository;

    Building building;
    Building building2;
    Bike bike;
    Bike bike2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", 42);
        buildingRepository.save(building2);

        bike = new Bike();
        bike.setAvailable(true);
        bike.setLocation(building);

        bike2 = new Bike();
        bike2.setAvailable(false);
        bike2.setLocation(building2);
    }

    @Test
    public void testConstructor() {
        assertNotNull(bikeService);
    }

    @Test
    public void testCreate() {
        bikeService.add(building.getId(), true);
        assertEquals(bikeService.all(), Collections.singletonList(bike));
        assertEquals(416,bikeService.add(137, true));
    }

    @Test
    public void testAll() {
        Iterator<Bike> iterator = bikeService.all().iterator();
        assertFalse(iterator.hasNext());
        bikeService.add(building.getId(), true);
        iterator = bikeService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        bikeService.add(building.getId(), true);
        assertEquals(bikeService.all().iterator().next(), bikeService.find(bikeService.all().iterator().next().getId()));
        assertNull(bikeService.find(-3));
    }

    @Test
    public void testUpdate() {
        bikeService.add(building.getId(), true);
        bikeService.add(building2.getId(), false);
        List<Bike> bikes = new ArrayList<>();
        bikeService.all().forEach(bikes::add);
        assertEquals(2, bikes.size());
        bike = bikes.get(0);
        bike2 = bikes.get(1);

        assertEquals(412, bikeService.update(bike.getId(), "nonexistent attribute", "random value"));
        assertNotEquals(bikeService.find(bike.getId()), bikeService.find(bike2.getId()));
        bikeService.update(bike2.getId(), "location", building.getId().toString());
        assertNotEquals(bikeService.find(bike.getId()), bikeService.find(bike2.getId()));
        bikeService.update(bike2.getId(), "available", "true");
        assertEquals(bikeService.find(bike.getId()), bikeService.find(bike2.getId()));
    }

    @Test
    public void testDelete() {
        bikeService.add(building.getId(), true);
        bikeService.add(building2.getId(), false);
        List<Bike> bikes = new ArrayList<>();
        bikeService.all().forEach(bikes::add);
        assertEquals(2, bikes.size());
        bike = bikes.get(0);
        bike2 = bikes.get(1);
        bikeService.delete(bikes.get(0).getId());
        bikes = new ArrayList<>();
        bikeService.all().forEach(bikes::add);
        assertEquals(1, bikes.size());
        assertNull(bikeService.find(bike.getId()));
        assertFalse(bikes.contains(bike));
        assertNotNull(bikeService.find(bike2.getId()));
        assertTrue(bikes.contains(bike2));
    }

    @AfterEach
    public void cleanup() {
        buildingRepository.deleteAll();
    }
}