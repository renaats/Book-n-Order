package nl.tudelft.oopp.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.services.DishService;
import nl.tudelft.oopp.demo.services.MenuService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the Dish service.
 */
@DataJpaTest
public class DishServiceTest {
    @TestConfiguration
    static class DishServiceTestConfiguration {
        @Bean
        public DishService dishService() {
            return new DishService();
        }

        @Bean
        public MenuService menuService() {
            return new MenuService();
        }
    }

    @Autowired
    DishService dishService;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    Menu menu1;
    Menu menu2;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;
    Restaurant restaurant1;
    Restaurant restaurant2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);
        menuRepository.save(menu1);

        menu2 = new Menu("BK menu", restaurant2);
        menuRepository.save(menu2);

        dish = new Dish("Chicken", menu1);

        dish2 = new Dish("Spicy Chicken", menu2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        Assertions.assertNotNull(dishService);
    }

    /**
     * Tests the saving and retrieval of an instance of Dish.
     */
    @Test
    public void testCreate() {
        Assertions.assertEquals(201, dishService.add(dish.getName(), dish.getMenu().getId()));
        Assertions.assertEquals(Collections.singletonList(dish), dishService.all());
    }

    /**
     * Tests the creation of an instance with an invalid menu id.
     */
    @Test
    public void testCreateIllegalMenu() {
        Assertions.assertEquals(429, dishService.add(dish.getName(), 0));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        Assertions.assertNull(dishService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        int id = dishService.all().get(0).getId();
        Assertions.assertNotNull(dishService.find(id));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        Assertions.assertEquals(2, dishService.all().size());
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish);
        dishes.add(dish2);
        Assertions.assertEquals(dishes, dishService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        int id = dishService.all().get(0).getId();
        Assertions.assertEquals(200, dishService.delete(id));
        Assertions.assertEquals(1, dishService.all().size());
    }

    /**
     * Tests the searching of a specific dish by name.
     */
    @Test
    public void testSearchByName() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = dishService.search("name:Chicken");
        Assertions.assertEquals(dishes.get(0), dish);
        Assertions.assertTrue(dishes.contains(dish2));
    }

    /**
     * Tests the searching of a specific dish by part of the name.
     */
    @Test
    public void testSearchByPartOfName() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = dishService.search("name:Spicy");
        Assertions.assertEquals(dishes.get(0), dish2);
        Assertions.assertFalse(dishes.contains(dish));
    }

    /**
     * Tests the searching of a specific dish by part of the name.
     */
    @Test
    public void testSearchNonexistentDish() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = dishService.search("name:soup");
        Assertions.assertFalse(dishes.contains(dish));
        Assertions.assertFalse(dishes.contains(dish2));
    }
}