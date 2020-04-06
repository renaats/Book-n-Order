package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;

import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Dish entity.
 */
@DataJpaTest
public class DishTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private DishRepository dishRepository;

    Menu menu1;
    Menu menu2;
    Restaurant restaurant1;
    Restaurant restaurant2;
    Dish dish;
    Dish dish2;
    Dish dish3;
    Set<Dish> dishes;
    Set<Dish> dishes1;
    Set<Dish> dishes2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        menu1 = new Menu("KFC menu", restaurant1);
        menuRepository.saveAndFlush(menu1);
        menu2 = new Menu("BK menu", restaurant2);
        menuRepository.saveAndFlush(menu2);

        dish = new Dish("Chicken", menu1, 300, "Cooked", "123");
        dish.setAllergies(new HashSet<>());
        dishRepository.saveAndFlush(dish);

        dish2 = new Dish("Spicy Chicken", menu2, 400, "Grilled", "234");

        dishes = new HashSet<>();
        dishes1 = new HashSet<>();
        dishes2 = new HashSet<>();

        dishes.add(dish);
        dishes.add(dish2);
        dishes1.add(dish);

        menu1.setDishes(dishes);
        menuRepository.saveAndFlush(menu1);
        menuRepository.saveAndFlush(menu2);
    }

    /**
     * Tests the constructor of the Dish class.
     */
    @Test
    public void testConstructor() {
        dish3 = new Dish();
        assertNotNull(dish3);
    }

    /**
     * Tests the saving and retrieval of an instance of Dish.
     */
    @Test
    public void testSaveAndRetrieveDish() {
        assertNotEquals(dish, dish2);
        dish2 = dishRepository.findAll().get(0);
        assertEquals(dish, dish2);
    }

    /**
     * Tests the getter for the name field.
     */
    @Test
    public void testNameGetter() {
        dish2 = dishRepository.findAll().get(0);
        assertEquals("Chicken", dish2.getName());
    }

    /**
     * Tests the getter for the menu field.
     */
    @Test
    public void testMenuGetter() {
        dish2 = dishRepository.findAll().get(0);
        assertEquals(menu1, dish2.getMenu());
    }

    /**
     * Tests the getter for the price field.
     */
    @Test
    public void testPriceGetter() {
        dish2 = dishRepository.findAll().get(0);
        assertEquals(300, dish2.getPrice());
    }

    /**
     * Tests the getter for the description field.
     */
    @Test
    public void testDescriptionGetter() {
        dish2 = dishRepository.findAll().get(0);
        assertEquals("Cooked", dish2.getDescription());
    }

    /**
     * Tests the getter for the image field.
     */
    @Test
    public void testImageGetter() {
        dish2 = dishRepository.findAll().get(0);
        assertEquals("123", dish2.getImage());
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals("Dish1", dish.getName());
        dish.setName("Dish1");
        assertEquals("Dish1", dish.getName());
    }

    /**
     * Tests the the change of the menu by using a setter.
     */
    @Test
    public void testChangeMenu() {
        assertNotEquals(menu2, dish.getMenu());
        dish.setMenu(menu2);
        assertEquals(menu2, dish.getMenu());
    }

    /**
     * Tests the the change of the price by using a setter.
     */
    @Test
    public void testChangePrice() {
        assertNotEquals(500, dish.getPrice());
        dish.setPrice(500);
        assertEquals(500, dish.getPrice());
    }

    /**
     * Tests the the change of the description by using a setter.
     */
    @Test
    public void testChangeDescription() {
        assertNotEquals("Grilled", dish.getDescription());
        dish.setDescription("Grilled");
        assertEquals("Grilled", dish.getDescription());
    }

    /**
     * Tests the the change of the image by using a setter.
     */
    @Test
    public void testChangeImage() {
        assertNotEquals("234", dish.getImage());
        dish.setImage("234");
        assertEquals("234", dish.getImage());
    }

    /**
     * Tests the equals method for non-equal dishes.
     */
    @Test
    public void testNonEqualDishes() {
        assertNotEquals(dish, dish2);
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