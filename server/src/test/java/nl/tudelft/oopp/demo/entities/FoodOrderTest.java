package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
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
    @Autowired
    private MenuRepository menuRepository;

    FoodOrder foodOrder;
    FoodOrder foodOrder2;
    Restaurant restaurant;
    Building building;
    Building deliveryLocation;
    AppUser appUser;
    Menu menu;
    Menu menu2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("XTUDelft", "Mekelweg", "None", 8);
        buildingRepository.saveAndFlush(building);

        restaurant = new Restaurant(building, "CafeX", "");
        restaurantRepository.saveAndFlush(restaurant);

        deliveryLocation = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.saveAndFlush(deliveryLocation);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        menu = new Menu("Lunch", restaurant);
        menuRepository.saveAndFlush(menu);

        foodOrder = new FoodOrder(restaurantRepository.findAll().get(0), userRepository.findAll().get(0),
                buildingRepository.findAll().get(1), new Date(11000000000L), menuRepository.findAll().get(0));
        foodOrderRepository.saveAndFlush(foodOrder);
        foodOrder = foodOrderRepository.findAll().get(0);
    }

    /**
     * Tests the constructor of the FoodOrder class
     */
    @Test
    public void testConstructor() {
        assertNotNull(foodOrder);
    }

    /**
     * Tests the saving and retrieval of an instance of FoodOrder.
     */
    @Test
    public void testSaveAndRetrieveFoodOrder() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder, foodOrder2);
    }

    /**
     * Tests the getter for the appUser field.
     */
    @Test
    public void testGetAppUser() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getAppUser(), foodOrder2.getAppUser());
    }

    /**
     * Tests the getter for the restaurant field.
     */
    @Test
    public void testGetRestaurant() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getRestaurant(), foodOrder2.getRestaurant());
    }

    /**
     * Tests the getter for the menu field.
     */
    @Test
    public void testGetMenu() {
        assertEquals(menuRepository.findAll().get(0), foodOrder.getMenu());
    }

    /**
     * Tests the setter for the menu field.
     */
    @Test
    public void testSetMenu() {
        assertEquals(menuRepository.findAll().get(0), foodOrder.getMenu());
        foodOrder.setMenu(menu2);
        assertEquals(menu2, foodOrder.getMenu());
    }

    /**
     * Tests the getter for the deliveryLocation field.
     */
    @Test
    public void testGetDeliveryLocation() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getDeliveryLocation(), foodOrder2.getDeliveryLocation());
    }

    /**
     * Tests the getter for the deliveryTime field.
     */
    @Test
    public void testGetDeliveryTime() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getDeliveryTime(), foodOrder2.getDeliveryTime());
    }

    /**
     * Tests the getter for the active field.
     */
    @Test
    public void testGetActive() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertTrue(foodOrder2.isActive());
    }

    /**
     * Tests the getter for the DishOrders.
     */
    @Test
    public void testGetDishOrder() {
        foodOrder = foodOrderRepository.findAll().get(0);
        foodOrder.setDishOrders(new HashSet<>());
        assertNotNull(foodOrder.getDishOrders());
    }

    /**
     * Tests the addition of a DishOrders.
     */
    @Test
    public void testAddDishOrder() {
        foodOrder = foodOrderRepository.findAll().get(0);
        foodOrder.setDishOrders(new HashSet<>());
        foodOrder.addDishOrder(new DishOrder());
        assertNotEquals(new HashSet<>(), foodOrder.getDishOrders());
    }

    /**
     * Tests the equals method for 2 equal food orders.
     */
    @Test
    public void testEqualFoodOrder() {
        foodOrder2 = new FoodOrder(restaurant, appUser, deliveryLocation, new Date(11000000000L), menuRepository.findAll().get(0));
        assertEquals(foodOrder, foodOrder2);
        assertNotSame(foodOrder, foodOrder2);
    }

    /**
     * Test the setter for feedback.
     */
    @Test
    public void testSetFeedback() {
        assertNotEquals(true, foodOrder.isFeedback());
        foodOrder.setFeedback(true);
        assertEquals(true, foodOrder.isFeedback());
    }

    /**
     * Test the getter for feedback.
     */
    @Test
    public void testIsFeedback() {
        foodOrder.setFeedback(false);
        assertEquals(false, foodOrder.isFeedbackHasBeenGiven());
    }

    /**
     * Test adding feedback to a restaurant.
     */
    @Test
    public void testAddFeedback() {
        assertNotEquals(true, foodOrder.isFeedbackHasBeenGiven());
        foodOrder.setFeedbackHasBeenGiven(true);
        assertEquals(true, foodOrder.isFeedbackHasBeenGiven());
    }

    /**
     * Tests the setting of the foodOrders for an appUser.
     */
    @Test
    public void testSetFoodOrders() {
        Set<FoodOrder> foodOrders = new HashSet<>();
        foodOrders.add(foodOrder);
        appUser.setFoodOrder(foodOrders);
        assertEquals(foodOrders, appUser.getFoodOrders());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        foodOrderRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
        menuRepository.deleteAll();
    }
}