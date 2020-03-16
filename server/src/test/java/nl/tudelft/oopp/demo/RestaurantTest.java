package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
    Restaurant restaurant;
    Restaurant restaurant2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.save(building);

        restaurant = new Restaurant();
        restaurant.setBuilding(building);
        restaurant.setName("Hangout");
        restaurantRepository.save(restaurant);
    }

    @Test
    public void saveAndRetrieveBike() {
        restaurant2 = restaurantRepository.findAll().get(0);
        assertEquals(restaurant, restaurant2);
    }

    @Test
    public void testGetters() {
        restaurant2 = restaurantRepository.findAll().get(0);
        assertEquals(restaurant2.getName(), restaurant.getName());
        assertSame(restaurant.getBuilding(), restaurant2.getBuilding());
    }

    @Test
    public void testEqualBikes() {
        restaurant2 = new Restaurant();
        restaurant2.setBuilding(building);
        restaurant2.setName("Hangout");
        assertEquals(restaurant, restaurant2);
        assertNotSame(restaurant, restaurant2);
        assertNotEquals(restaurant.getId(), restaurant2.getId());
    }

    @AfterEach
    public void cleanup() {
        restaurantRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}