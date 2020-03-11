package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.services.BikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
    public void testCreate() {
        bikeService.add(building.getId(), true);
        assertEquals(bikeService.all(), Collections.singletonList(bike));
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
        bikeService.delete(bikes.get(0).getId());
        bikes = new ArrayList<>();
        bikeService.all().forEach(bikes::add);
        assertEquals(1, bikes.size());
    }

}