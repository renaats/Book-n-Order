package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Bike entity.
 */
@DataJpaTest
public class BikeTest {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Building building2;
    Bike bike;
    Bike bike2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        bike = new Bike(building, true);
        bikeRepository.save(bike);
    }

    /**
     * Tests the constructor of the Bike class
     */
    @Test
    public void testConstructor() {
        bike2 = new Bike();
        assertNotNull(bike2);
    }

    /**
     * Tests the saving and retrieval of an instance of Bike.
     */
    @Test
    public void testSaveAndRetrieveBike() {
        bike2 = bikeRepository.findAll().get(0);
        assertEquals(bike, bike2);
    }

    /**
     * Tests the getters of the Bike class.
     */
    @Test
    public void testGetters() {
        bike2 = bikeRepository.findAll().get(0);
        assertTrue(bike2.isAvailable());
        assertSame(building, bike2.getLocation());
    }

    /**
     * Tests the change of the availability field.
     */
    @Test
    public void testChangeAvailability() {
        assertTrue(bike.isAvailable());
        bike.setAvailable(false);
        assertFalse(bike.isAvailable());
    }

    /**
     * Tests the change of the location field.
     */
    @Test
    public void testChangeLocation() {
        assertNotEquals(building2, bike.getLocation());
        bike.setLocation(building2);
        assertEquals(building2, bike.getLocation());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        bikeRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}