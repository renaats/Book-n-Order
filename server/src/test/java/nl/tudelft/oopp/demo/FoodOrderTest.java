package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;

import nl.tudelft.oopp.demo.repositories.BuildingRepository;
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
    @Autowired
    private DishRepository dishRepository;

    FoodOrder foodOrder;
    FoodOrder foodOrder2;
    Restaurant restaurant;
    AppUser appUser;
    Building building;
    Building deliveryLocation;
    Menu menu;
    Dish dish1;
    Dish dish2;
    Set<Dish> dishes;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("XTUDelft", "Mekelweg", 8);
        buildingRepository.saveAndFlush(building);

        restaurant = new Restaurant(building, "CafeX");
        restaurantRepository.saveAndFlush(restaurant);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.saveAndFlush(appUser);

        deliveryLocation = new Building("EWI", "Mekelweg", 4);
        buildingRepository.saveAndFlush(deliveryLocation);

        menu = new Menu("Lunch Menu", restaurant);
        menuRepository.saveAndFlush(menu);

        dish1 = new Dish("Pizza", menu);
        dishRepository.saveAndFlush(dish1);
        dish2 = new Dish("Salad", menu);
        dishRepository.saveAndFlush(dish2);
        dishes = new HashSet<>();
        dishes.add(dish1);
        dishes.add(dish2);

        foodOrder = new FoodOrder(restaurantRepository.findAll().get(0), userRepository.findAll().get(0), buildingRepository.findAll().get(1),
                new Date(10000000000L), dishes);
        foodOrderRepository.saveAndFlush(foodOrder);
        foodOrder = foodOrderRepository.findAll().get(0);
    }


    @Test
    public void testGetAppUser() {
        foodOrder2 = foodOrderRepository.findAll().get(0);
        assertEquals(foodOrder.getAppUser(), foodOrder2.getAppUser());
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
        dishRepository.deleteAll();
    }
}