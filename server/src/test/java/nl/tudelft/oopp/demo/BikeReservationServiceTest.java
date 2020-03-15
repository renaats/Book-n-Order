package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.BikeReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@DataJpaTest
public class BikeReservationServiceTest {
    @TestConfiguration
    static class BikeReservationServiceTestConfiguration {
        @Bean
        public BikeReservationService bikeReservationService() {
            return new BikeReservationService();
        }
    }

    @Autowired
    BikeReservationService bikeReservationService;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    Building building;
    Building deliverLocation;
    Bike bike;
    AppUser appUser;
    
    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(8);
        buildingRepository.save(building);

        restaurant = new Restaurant();
        restaurant.setName("Cafe X");
        restaurant.setBuilding(building);
        restaurantRepository.save(restaurant);

        appUser = new AppUser();
        appUser.setEmail("l.j.jongejans@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Liselotte");
        appUser.setSurname("Jongejans");
        appUser.setFaculty("EWI");
        appUser.setFoodOrder(new HashSet<>());
        userRepository.save(appUser);

        deliverLocation = new Building();
        deliverLocation.setName("EWI");
        deliverLocation.setStreet("Mekelweg");
        deliverLocation.setHouseNumber(4);
        buildingRepository.save(deliverLocation);

        deliverTime = new Date(11000000000L);
        deliverTimeMs = deliverTime.getTime();

        deliverTime2 = new Date(10000000000L);
        deliverTimeMs2 = deliverTime2.getTime();

        foodOrder = new FoodOrder();
        foodOrder.setAppUser(appUser);
        foodOrder.setDeliveryLocation(deliverLocation);
        foodOrder.setDeliveryTime(deliverTime);
        foodOrder.setRestaurant(restaurant);

        foodOrder2 = new FoodOrder();
        foodOrder2.setAppUser(appUser);
        foodOrder2.setDeliveryLocation(deliverLocation);
        foodOrder2.setDeliveryTime(deliverTime2);
        foodOrder2.setRestaurant(restaurant);
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
        //foodOrderService.update(foodOrder2.getId(), "deliveryTime", Long.toString(deliverTimeMs));
        //assertEquals(foodOrderService.find(foodOrder.getId()), foodOrderService.find(foodOrder2.getId()));
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
