package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantHourRepository;
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
    @Autowired
    private RestaurantHourRepository restaurantHourRepository;

    Building building;
    Building building2;
    Restaurant restaurant;
    Restaurant restaurant2;
    Restaurant restaurant3;
    Restaurant restaurant4;
    Menu menu;
    RestaurantHours restaurantHours1;
    RestaurantHours restaurantHours2;
    LocalTime time1;
    LocalTime time2;
    Set<RestaurantHours> restaurantHours = new TreeSet<RestaurantHours>();

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", 42);
        buildingRepository.save(building2);

        restaurant = new Restaurant(building, "Hangout");
        restaurantRepository.save(restaurant);

        restaurantHours1 = new RestaurantHours(1, restaurant, time1, time2);
        restaurantHourRepository.save(restaurantHours1);

        restaurantHours2 = new RestaurantHours(2, restaurant, time1, time2);
        restaurantHourRepository.save(restaurantHours2);

        //restaurantHours.add(restaurantHours1);
        //restaurantHours.add(restaurantHours2);

        restaurant3 = new Restaurant(building, "Hangout", restaurantHours, menu);
        restaurantRepository.save(restaurant3);

    }

    /**
     * Tests the constructor of the Restaurant class
     */
    @Test
    public void testConstructor1() {
        restaurant2 = new Restaurant();
        assertNotNull(restaurant2);
    }

    /**
     * Tests the constructor of the Restaurant class
     */
    @Test
    public void testConstructor2() {
        restaurant4 = new Restaurant(building, "Hangout", restaurantHours, menu);
        assertNotNull(restaurant4);
    }

    /**
     * Tests the saving and retrieval of an instance of Restaurant.
     */
    @Test
    public void saveAndRetrieveRestaurant1() {
        restaurant2 = restaurantRepository.findAll().get(0);
        assertEquals(restaurant, restaurant2);
    }

    /**
     * Tests the saving and retrieval of an instance of Restaurant.
     */
    @Test
    public void saveAndRetrieveRestaurant2() {
        restaurant4 = restaurantRepository.findAll().get(1);
        assertEquals(restaurant4, restaurant3);
    }

    /**
     * Tests the getters of the Restaurant class.
     */
    @Test
    public void testGetters1() {
        restaurant4 = restaurantRepository.findAll().get(1);
        assertSame(menu,restaurant4.getMenu());
        assertEquals(restaurantHours, restaurant4.getRestaurantHours());
    }

    /**
     * Tests the getters of the Restaurant class.
     */
    @Test
    public void testGetters2() {
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
     * Tests the the change of the building by using a setter.
     */
    @Test
    public void testChangeBuilding() {
        assertNotEquals(building2, restaurant.getBuilding());
        restaurant.setBuilding(building2);
        assertEquals(building2, restaurant.getBuilding());
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