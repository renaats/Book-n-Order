package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.HashSet;

import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishOrderRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
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
 * Tests the DishOrder entity.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DishOrderTest {
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
    @Autowired
    private DishOrderRepository dishOrderRepository;
    @Autowired
    private DishRepository dishRepository;

    FoodOrder foodOrder;
    Restaurant restaurant;
    Building building;
    Building deliveryLocation;
    AppUser appUser;
    Menu menu;
    DishOrder dishOrder;
    DishOrder dishOrder2;
    Dish dish;

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
                buildingRepository.findAll().get(1), new Date(11000000000L),menuRepository.findAll().get(0));
        foodOrderRepository.saveAndFlush(foodOrder);
        foodOrder = foodOrderRepository.findAll().get(0);

        dish = new Dish("Food", menuRepository.findAll().get(0), 100, "123", "123");
        dishRepository.save(dish);

        dishOrder = new DishOrder(dish, foodOrder, 2);
        dishOrderRepository.save(dishOrder);
    }

    /**
     * Tests the constructor of the FoodOrder class
     */
    @Test
    public void testConstructor() {
        assertNotNull(dishOrder);
    }

    /**
     * Tests the saving and retrieval of an instance of FoodOrder.
     */
    @Test
    public void testSaveAndRetrieveFoodOrder() {
        dishOrder2 = dishOrderRepository.findAll().get(0);
        assertEquals(dishOrder, dishOrder2);
    }

    /**
     * Tests the getter for the foodOrder field.
     */
    @Test
    public void testGetFoodOrder() {
        assertEquals(foodOrder, dishOrder.getFoodOrder());
    }

    /**
     * Tests the getter for the dish field.
     */
    @Test
    public void testGetDish() {
        assertEquals(dish, dishOrder.getDish());
    }

    /**
     * Tests the getter for the amount field.
     */
    @Test
    public void testGetDeliveryLocation() {
        assertEquals(2, dishOrder.getAmount());
    }

    /**
     * Tests the equals method for 2 equal dish orders.
     */
    @Test
    public void testEqualDishOrder() {
        dishOrder2 = new DishOrder(dish, foodOrder, 2);
        assertEquals(dishOrder, dishOrder2);
        assertNotSame(dishOrder, dishOrder2);
    }

    /**
     * Tests the setting of the foodOrder for a dishOrder.
     */
    @Test
    public void testSetFoodOrder() {
        assertEquals(foodOrder, dishOrder.getFoodOrder());
        dishOrder.setFoodOrder(null);
        assertNull(dishOrder.getFoodOrder());
    }

    /**
     * Tests the setting of the dish for a dishOrder.
     */
    @Test
    public void testSetDish() {
        assertEquals(dish, dishOrder.getDish());
        dishOrder.setDish(null);
        assertNull(dishOrder.getDish());
    }

    /**
     * Tests the setting of the amount for a dishOrder.
     */
    @Test
    public void testSetAmount() {
        assertEquals(2, dishOrder.getAmount());
        dishOrder.setAmount(3);
        assertEquals(3, dishOrder.getAmount());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        dishOrderRepository.deleteAll();
        dishRepository.deleteAll();
        foodOrderRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        buildingRepository.deleteAll();
        menuRepository.deleteAll();
    }
}