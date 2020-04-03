package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Menu entity.
 */
@DataJpaTest
public class MenuTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private DishRepository dishRepository;

    Menu menu1;
    Menu menu2;
    Menu menu3;
    Building building;
    Restaurant restaurant1;
    Restaurant restaurant2;
    Restaurant restaurant3;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        restaurant1 = new Restaurant(building, "KFC", "");
        restaurantRepository.saveAndFlush(restaurant1);

        restaurant2 = new Restaurant(building, "Burger King", "");
        restaurantRepository.saveAndFlush(restaurant2);

        dish = new Dish("Chicken", menu1, 300, "Cooked", "123");
        dishRepository.saveAndFlush(dish);

        dish2 = new Dish("Spicy Chicken", menu2, 400, "Grilled", "234");
        dishRepository.saveAndFlush(dish2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);

        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);
        menuRepository.saveAndFlush(menu1);

        menu2 = new Menu("BK menu", restaurant2);

        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.saveAndFlush(building);
    }

    /**
     * Tests the constructor of the Menu class
     */
    @Test
    public void testConstructor() {
        assertNotNull(menu1);
    }

    /**
     * Tests the saving and retrieval of an instance of Menu.
     */
    @Test
    public void testSaveAndRetrieveMenu() {
        assertNotEquals(menu1, menu2);
        menu2 = menuRepository.findAll().get(0);
        assertEquals(menu1, menu2);
    }

    /**
     * Tests the getter for the name field.
     */
    @Test
    public void testNameGetter() {
        menu2 = menuRepository.findAll().get(0);
        assertEquals("KFC menu", menu2.getName());
    }

    /**
     * Tests the getter for the restaurant field.
     */
    @Test
    public void testRestaurantGetter() {
        menu2 = menuRepository.findAll().get(0);
        assertEquals(restaurant1, menu2.getRestaurant());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals("Menu1", menu1.getName());
        menu1.setName("Menu1");
        assertEquals("Menu1", menu1.getName());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeRestaurant() {
        assertNotEquals(restaurant2, menu1.getRestaurant());
        menu1.setRestaurant(restaurant2);
        assertEquals(restaurant2, menu1.getRestaurant());
    }

    /**
     * Tests the equals method for non-equal menus.
     */
    @Test
    public void testNotEqualMenus() {
        assertNotEquals(menu1, menu2);
    }

    /**
     * Tests the retrieval of dishes for some menu.
     */
    @Test
    public void testDishes() {
        assertEquals(dishes, menu1.getDishes());
    }

    /**
     * Tests the setter and getter for the Restaurant menu field.
     */
    @Test
    public void testRestaurantSetMenu() {
        restaurant1.setMenu(menu1);
        assertEquals(menu1, restaurant1.getMenu());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        dishRepository.deleteAll();
        menuRepository.deleteAll();
    }
}
