package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BikeTest {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
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

        bike = new Bike();
        bike.setAvailable(true);
        bike.setLocation(building);
        bikeRepository.save(bike);
    }

    @Test
    public void testConstructor() {
        assertNotNull(bike);
    }

    @Test
    public void saveAndRetrieveBike() {
        bike2 = bikeRepository.findAll().get(0);
        assertEquals(bike, bike2);
    }

    @Test
    public void testGetters() {
        bike2 = bikeRepository.findAll().get(0);
        assertTrue(bike2.isAvailable());
        assertSame(bike.getLocation(), bike2.getLocation());
    }

    @Test
    public void testSetters() {
        bike2 = new Bike();
        bike2.setAvailable(false);
        building = new Building();
        bike2.setLocation(building);
        assertEquals(bike2.isAvailable(), false);
        assertEquals(bike2.getLocation(), building);
    }

    @Test
    public void testEqualBikes() {
        bike2 = new Bike();
        bike2.setAvailable(true);
        bike2.setLocation(building);
        assertEquals(bike, bike2);
        assertNotSame(bike, bike2);
        assertNotEquals(bike.getId(), bike2.getId());
    }

    @AfterEach
    public void cleanup() {
        bikeRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}