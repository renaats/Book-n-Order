package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the Bike service.
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

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        bike = new Bike(building, true);
        bike2 = new Bike(building2, false);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(bikeService);
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testCreateIllegalBuilding() {
        assertEquals(ID_NOT_FOUND, bikeService.add(0, true));
    }

    /**
     * Tests the saving and retrieval of an instance of Bike.
     */
    @Test
    public void testAdd() {
        assertEquals(ADDED, bikeService.add(building.getId(), true));
        assertEquals(Collections.singletonList(bike), bikeService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(bikeService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        bikeService.add(building.getId(), true);
        int id = bikeService.all().get(0).getId();
        assertNotNull(bikeService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(ID_NOT_FOUND, bikeService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        bikeService.add(building.getId(), true);
        int id = bikeService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, bikeService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the location by using the service.
     */
    @Test
    public void testChangeLocation() {
        bikeService.add(building.getId(), true);
        int id = bikeService.all().get(0).getId();
        assertNotEquals(building2, bikeService.find(id).getLocation());
        bikeService.update(id, "location", building2.getId().toString());
        assertEquals(building2, bikeService.find(id).getLocation());
    }

    /**
     * Tests the change of the availability by using the service.
     */
    @Test
    public void testChangeAvailability() {
        bikeService.add(building.getId(), true);
        int id = bikeService.all().get(0).getId();
        assertTrue(bikeService.find(id).isAvailable());
        bikeService.update(id, "available", "false");
        assertFalse(bikeService.find(id).isAvailable());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        bikeService.add(building.getId(), true);
        bikeService.add(building2.getId(), false);
        assertEquals(2, bikeService.all().size());
        ArrayList<Bike> bikes = new ArrayList<>();
        bikes.add(bike);
        bikes.add(bike2);
        assertEquals(bikes, bikeService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        bikeService.add(building.getId(), true);
        int id = bikeService.all().get(0).getId();
        assertEquals(EXECUTED, bikeService.delete(id));
        assertEquals(0, bikeService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(NOT_FOUND, bikeService.delete(0));
    }
}