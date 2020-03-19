package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the Restaurant service.
 */
@DataJpaTest
public class RestaurantServiceTest {
    @TestConfiguration
    static class RestaurantServiceTestConfiguration {
        @Bean
        public RestaurantService restaurantService() {
            return new RestaurantService();
        }
    }

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    BuildingRepository buildingRepository;

    Building building;
    Building building2;
    Restaurant restaurant;
    Restaurant restaurant2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", 42);
        buildingRepository.save(building2);

        restaurant = new Restaurant(building, "Hangout");

        restaurant2 = new Restaurant(building2, "Food station");
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(restaurantService);
    }

    /**
     * Tests the saving and retrieval of an instance of Restaurant.
     */
    @Test
    public void testCreate() {
        assertEquals(201, restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName()));
        assertEquals(Collections.singletonList(restaurant), restaurantService.all());
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testCreateIllegalBuilding() {
        assertEquals(422, restaurantService.add(-3,"The Ghost Restaurant"));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(restaurantService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        int id = restaurantService.all().get(0).getId();
        assertNotNull(restaurantService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(428, restaurantService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        int id = restaurantService.all().get(0).getId();
        assertEquals(412, restaurantService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        int id = restaurantService.all().get(0).getId();
        assertNotEquals("Food Station", restaurantService.find(id).getName());
        restaurantService.update(id, "name", "Food Station");
        assertEquals("Food Station", restaurantService.find(id).getName());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    public void testChangeBuilding() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        int id = restaurantService.all().get(0).getId();
        assertNotEquals(building2, restaurantService.find(id).getBuilding());
        restaurantService.update(id, "building", building2.getId().toString());
        assertEquals(building2, restaurantService.find(id).getBuilding());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        restaurantService.add(restaurant2.getBuilding().getId(), restaurant2.getName());
        assertEquals(2, restaurantService.all().size());
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        restaurants.add(restaurant2);
        assertEquals(restaurants, restaurantService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        int id = restaurantService.all().get(0).getId();
        assertEquals(200, restaurantService.delete(id));
        assertEquals(0, restaurantService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(428, restaurantService.delete(0));
    }
}