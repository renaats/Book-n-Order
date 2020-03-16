package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;

import nl.tudelft.oopp.demo.entities.Building;
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

@DataJpaTest
public class RestaurantHourTest {
    @Autowired
    private RestaurantHourRepository restaurantHourRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    Building building;
    Restaurant restaurant;
    Restaurant restaurant2;
    RestaurantHours restaurantHours;
    RestaurantHours restaurantHours2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
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

        restaurant2 = new Restaurant();
        restaurant2.setBuilding(building);
        restaurant2.setName("Food Station");
        restaurantRepository.save(restaurant2);

        restaurantHours = new RestaurantHours(1, restaurant, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        restaurantHourRepository.save(restaurantHours);
    }

    /**
     * Tests the constructor of the RestaurantHours class
     */
    @Test
    public void testConstructor() {
        restaurantHours2 = new RestaurantHours();
        assertNotNull(restaurantHours2);
    }

    /**
     * Tests the saving and retrieval of an instance of RestaurantHours.
     */
    @Test
    public void saveAndRetrieveBuildingHours() {
        restaurantHours2 = restaurantHourRepository.findByRestaurant_IdAndDay(restaurant.getId(), 1);
        assertEquals(restaurantHours, restaurantHours2);
    }

    /**
     * Tests the getter for the day field.
     */
    @Test
    public void testDayGetter() {
        restaurantHours2 = restaurantHourRepository.findByRestaurant_IdAndDay(restaurant.getId(), 1);
        assertEquals(1, restaurantHours2.getDay());
    }

    /**
     * Tests the getter for the restaurant field.
     */
    @Test
    public void testRestaurantGetter() {
        restaurantHours2 = restaurantHourRepository.findByRestaurant_IdAndDay(restaurant.getId(), 1);
        assertEquals(restaurant, restaurantHours2.getRestaurant());
    }

    /**
     * Tests the getter for the startTime field.
     */
    @Test
    public void testStartTimeGetter() {
        restaurantHours2 = restaurantHourRepository.findByRestaurant_IdAndDay(restaurant.getId(), 1);
        assertEquals(LocalTime.ofSecondOfDay(1000), restaurantHours2.getStartTime());
    }

    /**
     * Tests the getter for the endTime field.
     */
    @Test
    public void testEndTimeGetters() {
        restaurantHours2 = restaurantHourRepository.findByRestaurant_IdAndDay(restaurant.getId(), 1);
        assertEquals(LocalTime.ofSecondOfDay(3000), restaurantHours2.getEndTime());
    }

    /**
     * Tests the the change of the day by using a setter.
     */
    @Test
    public void testChangeDay() {
        restaurantHours2 = new RestaurantHours();
        restaurantHours2.setDay(2);
        restaurantHours2.setRestaurant(restaurant);
        restaurantHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        restaurantHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertNotEquals(restaurantHours, restaurantHours2);
        restaurantHours2.setDay(1);
        assertEquals(restaurantHours, restaurantHours2);
    }

    /**
     * Tests the the change of the restaurant by using a setter.
     */
    @Test
    public void testChangeRestaurant() {
        restaurantHours2 = new RestaurantHours();
        restaurantHours2.setDay(1);
        restaurantHours2.setRestaurant(restaurant2);
        restaurantHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        restaurantHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertNotEquals(restaurantHours, restaurantHours2);
        restaurantHours2.setRestaurant(restaurant);
        assertEquals(restaurantHours, restaurantHours2);
    }

    /**
     * Tests the the change of the start time by using a setter.
     */
    @Test
    public void testChangeStartTime() {
        restaurantHours2 = new RestaurantHours();
        restaurantHours2.setDay(1);
        restaurantHours2.setRestaurant(restaurant);
        restaurantHours2.setStartTime(LocalTime.ofSecondOfDay(2000));
        restaurantHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertNotEquals(restaurantHours, restaurantHours2);
        restaurantHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        assertEquals(restaurantHours, restaurantHours2);
    }

    /**
     * Tests the the change of the ent time by using a setter.
     */
    @Test
    public void testChangeEndTime() {
        restaurantHours2 = new RestaurantHours();
        restaurantHours2.setDay(1);
        restaurantHours2.setRestaurant(restaurant);
        restaurantHours2.setStartTime(LocalTime.ofSecondOfDay(1000));
        restaurantHours2.setEndTime(LocalTime.ofSecondOfDay(4000));
        assertNotEquals(restaurantHours, restaurantHours2);
        restaurantHours2.setEndTime(LocalTime.ofSecondOfDay(3000));
        assertEquals(restaurantHours, restaurantHours2);
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        restaurantHourRepository.deleteAll();
        restaurantRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}