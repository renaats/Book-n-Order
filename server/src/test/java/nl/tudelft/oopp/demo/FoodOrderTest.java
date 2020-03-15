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
        building = new Building();
        building.setName("XTUDelft");
        building.setStreet("Mekelweg");
        building.setHouseNumber(8);
        buildingRepository.saveAndFlush(building);

        restaurant = new Restaurant();
        restaurant.setName("CafeX");
        restaurant.setBuilding(building);
        restaurantRepository.saveAndFlush(restaurant);

        deliveryLocation = new Building();
        deliveryLocation.setName("EWI");
        deliveryLocation.setStreet("Mekelweg");
        deliveryLocation.setHouseNumber(4);
        buildingRepository.saveAndFlush(deliveryLocation);

        appUser = new AppUser();
        appUser.setEmail("l.j.jongejans@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Liselotte");
        appUser.setSurname("Jongejans");
        appUser.setFaculty("EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        foodOrder = new FoodOrder();
        foodOrder.setAppUser(userRepository.findAll().get(0));
        foodOrder.setRestaurant(restaurantRepository.findAll().get(0));
        foodOrder.setDeliveryLocation(buildingRepository.findAll().get(1));
        foodOrder.setDeliveryTime(new Date(11000000000L));
        foodOrderRepository.saveAndFlush(foodOrder);
        foodOrder = foodOrderRepository.findAll().get(0);
    }

    @Test
    public void saveAndRetrieveFoodOrder() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder, foodOrder2);
    }

    @Test
    public void testGetters() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getAppUser(), foodOrder2.getAppUser());
        assertEquals(foodOrder.getRestaurant(), foodOrder2.getRestaurant());
        assertEquals(foodOrder.getDeliveryLocation(), foodOrder2.getDeliveryLocation());
        assertEquals(foodOrder.getDeliveryTime(), foodOrder2.getDeliveryTime());
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

    /*@Test
    public void testGetDeliveryTime() {
        assertEquals(foodOrder.getDeliveryTime(), foodOrder2.getDeliveryTime());
    }*/

    @Test
    public void testEqualFoodOrder() {
        foodOrder2 = new FoodOrder();
        foodOrder2.setAppUser(appUser);
        foodOrder2.setRestaurant(restaurant);
        foodOrder2.setDeliveryLocation(deliveryLocation);
        foodOrder2.setDeliveryTime(new Date(11000000000L));
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
