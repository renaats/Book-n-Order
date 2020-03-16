package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

        restaurant = new Restaurant();
        restaurant.setName("Hangout");
        restaurant.setBuilding(building);

        restaurant2 = new Restaurant();
        restaurant2.setName("Food station");
        restaurant2.setBuilding(building2);
    }

    @Test
    public void testCreate() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        assertEquals(restaurantService.all(), Collections.singletonList(restaurant));
    }

    @Test
    public void testUpdate() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        restaurantService.add(restaurant2.getBuilding().getId(), restaurant2.getName());
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantService.all().forEach(restaurants::add);
        assertEquals(2, restaurants.size());
        restaurant = restaurants.get(0);
        restaurant2 = restaurants.get(1);

        assertNotEquals(restaurantService.find(restaurant.getId()), restaurantService.find(restaurant2.getId()));
        restaurantService.update(restaurant2.getId(), "building", building.getId().toString());
        assertNotEquals(restaurantService.find(restaurant.getId()), restaurantService.find(restaurant2.getId()));
        restaurantService.update(restaurant2.getId(), "name", restaurant.getName());
        assertEquals(restaurantService.find(restaurant.getId()), restaurantService.find(restaurant2.getId()));
    }

    @Test
    public void testDelete() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName());
        restaurantService.add(restaurant2.getBuilding().getId(), restaurant2.getName());
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantService.all().forEach(restaurants::add);
        assertEquals(2, restaurants.size());
        restaurantService.delete(restaurants.get(0).getId());
        restaurants = new ArrayList<>();
        restaurantService.all().forEach(restaurants::add);
        assertEquals(1, restaurants.size());
    }

}