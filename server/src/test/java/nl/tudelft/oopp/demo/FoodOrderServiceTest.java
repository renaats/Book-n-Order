package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.FoodOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the FoodOrderService service.
 */
@DataJpaTest
public class FoodOrderServiceTest {
    @TestConfiguration
    static class FoodOrderServiceTestConfiguration {
        @Bean
        public FoodOrderService foodOrderService() {
            return new FoodOrderService();
        }
    }

    @Autowired
    FoodOrderService foodOrderService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    Building building;
    Building deliverLocation;
    Restaurant restaurant;
    AppUser appUser;
    Date deliverTime;
    Date deliverTime2;
    long deliverTimeMs;
    long deliverTimeMs2;
    FoodOrder foodOrder;
    FoodOrder foodOrder2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building("Sporthal", "Mekelweg", 8);
        buildingRepository.save(building);

        restaurant = new Restaurant(building, "Cafe X");
        restaurantRepository.save(restaurant);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser.setFoodOrder(new HashSet<>());
        userRepository.save(appUser);

        deliverLocation = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(deliverLocation);

        deliverTime = new Date(11000000000L);
        deliverTimeMs = deliverTime.getTime();

        deliverTime2 = new Date(10000000000L);
        deliverTimeMs2 = deliverTime2.getTime();

        foodOrder = new FoodOrder(restaurant, appUser, deliverLocation, deliverTime);

        foodOrder2 = new FoodOrder(restaurant, appUser, deliverLocation, deliverTime2);
    }

    @Test
    public void testAdd() {
        foodOrderService.add(restaurant.getId(), appUser.getEmail(), deliverLocation.getId(), deliverTimeMs);
        assertEquals(foodOrderService.all(), Collections.singletonList(foodOrder));
    }

    @Test
    public void testUpdate() {
        foodOrderService.add(restaurant.getId(), appUser.getEmail(), deliverLocation.getId(), deliverTimeMs);
        foodOrderService.add(restaurant.getId(), appUser.getEmail(), deliverLocation.getId(), deliverTimeMs2);
        List<FoodOrder> foodOrders = new ArrayList<>();
        foodOrderService.all().forEach(foodOrders::add);
        assertEquals(2, foodOrders.size());
        foodOrder = foodOrders.get(0);
        foodOrder2 = foodOrders.get(1);
        assertNotEquals(foodOrderService.find(foodOrder.getId()), foodOrderService.find(foodOrder2.getId()));
        foodOrderService.update(foodOrder2.getId(), "deliveryTime", Long.toString(deliverTimeMs));
        assertEquals(foodOrderService.find(foodOrder.getId()), foodOrderService.find(foodOrder2.getId()));
    }

    @Test
    public void testDelete() {
        foodOrderService.add(restaurant.getId(), appUser.getEmail(), deliverLocation.getId(), deliverTimeMs);
        foodOrderService.add(restaurant.getId(), appUser.getEmail(), deliverLocation.getId(), deliverTimeMs2);
        List<FoodOrder> foodOrders = new ArrayList<>();
        foodOrderService.all().forEach(foodOrders::add);
        assertEquals(2, foodOrders.size());
        foodOrderService.delete(foodOrders.get(0).getId());
        foodOrders = new ArrayList<>();
        foodOrderService.all().forEach(foodOrders::add);
        assertEquals(1, foodOrders.size());
    }
}
