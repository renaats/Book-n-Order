package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Restaurant entity.
 */
@DataJpaTest
public class RestaurantTest {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building;
    Building building2;
    Restaurant restaurant;
    Restaurant restaurant2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        restaurant = new Restaurant(building, "Hangout", "");
        restaurantRepository.save(restaurant);
    }

    /**
     * Tests the constructor of the Restaurant class
     */
    @Test
    public void testConstructor() {
        restaurant2 = new Restaurant();
        assertNotNull(restaurant2);
    }

    /**
     * Tests the saving and retrieval of an instance of Restaurant.
     */
    @Test
    public void testSaveAndRetrieveRestaurant() {
        restaurant2 = restaurantRepository.findAll().get(0);
        assertEquals(restaurant, restaurant2);
    }

    /**
     * Tests the getters of the Restaurant class.
     */
    @Test
    public void testGetters() {
        restaurant2 = restaurantRepository.findAll().get(0);
        assertEquals("Hangout", restaurant2.getName());
        assertSame(building, restaurant2.getBuilding());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals("Food Station", restaurant.getName());
        restaurant.setName("Food Station");
        assertEquals("Food Station", restaurant.getName());
    }

    /**
     * Tests the change of the building by using a setter.
     */
    @Test
    public void testChangeBuilding() {
        assertNotEquals(building2, restaurant.getBuilding());
        restaurant.setBuilding(building2);
        assertEquals(building2, restaurant.getBuilding());
    }

    /**
     * Test adding feedback to a restaurant.
     */
    @Test
    public void testAddFeedback() {
        assertEquals(0, restaurant.getFeedbackCounter());
        restaurant.addFeedback(true);
        assertEquals(1, restaurant.getFeedbackCounter());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        restaurantRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}