package nl.tudelft.oopp.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.services.BikeService;
import nl.tudelft.oopp.demo.services.BikeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BikeServiceTest {
    @TestConfiguration
    static class BikeServiceTestConfiguration {
        @Bean
        public BikeService bikeService() {
            return new BikeServiceImpl();
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
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.save(building);

        building2 = new Building();
        building2.setName("EWI2");
        building2.setStreet("Mekelweg2");
        building2.setHouseNumber(42);
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
        assertEquals(bikeService.all(), Arrays.asList(bike));
        assertEquals("Could not find building with id 137!",bikeService.add(137, true));
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
        assertNotNull(bikeService.find(bike2.getId()));
    }

    @AfterEach
    public void cleanup() {
        buildingRepository.deleteAll();
    }

}