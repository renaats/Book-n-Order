package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Date;
import java.util.HashSet;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the FoodOrder entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FoodOrderTest {
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    FoodOrder foodOrder;
    FoodOrder foodOrder2;
    Restaurant restaurant;
    Building building;
    Building deliveryLocation;
    AppUser appUser;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building("XTUDelft", "Mekelweg", 8);
        buildingRepository.saveAndFlush(building);

        restaurant = new Restaurant(building, "CafeX");
        restaurantRepository.saveAndFlush(restaurant);

        deliveryLocation = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(deliveryLocation);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        foodOrder = new FoodOrder(restaurantRepository.findAll().get(0), userRepository.findAll().get(0),
                buildingRepository.findAll().get(1), new Date(11000000000L));
        foodOrderRepository.saveAndFlush(foodOrder);
        foodOrder = foodOrderRepository.findAll().get(0);
    }

    @Test
    public void saveAndRetrieveFoodOrder() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder, foodOrder2);
    }

    @Test
    public void testGetAppUser() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getAppUser(), foodOrder2.getAppUser());
    }

    @Test
    public void testGetRestaurant() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getRestaurant(), foodOrder2.getRestaurant());
    }

    @Test
    public void testGetDeliveryLocation() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getDeliveryLocation(), foodOrder2.getDeliveryLocation());
    }

    @Test
    public void testGetDeliveryTime() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getDeliveryTime(), foodOrder2.getDeliveryTime());
    }

    @Test
    public void testEqualFoodOrder() {
        foodOrder2 = new FoodOrder(restaurant, appUser, deliveryLocation, new Date(11000000000L));
        assertEquals(foodOrder, foodOrder2);
        assertNotSame(foodOrder, foodOrder2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        foodOrderRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
    }
}
