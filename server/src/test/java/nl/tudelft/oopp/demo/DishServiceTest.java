package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.services.DishService;
import nl.tudelft.oopp.demo.services.MenuService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the DishService service.
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

    /** Sets up the classes before executing the tests.
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

    @Test
    public void testCreate() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        assertEquals(dishService.all(), Collections.singletonList(dish));
    }

    @Test
    public void testCreate2() {
        assertEquals(201, dishService.add(dish.getName(), dish.getMenu().getId()));
    }

    @Test
    public void testAdded() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = new ArrayList<>();
        dishService.all().forEach(dishes::add);
        assertEquals(2, dishes.size());
    }

    @Test
    public void testErrorCode() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = new ArrayList<>();
        dishService.all().forEach(dishes::add);
        assertEquals(200, dishService.delete(dishes.get(0).getId()));
    }

    @Test
    public void testDelete() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        List<Dish> dishes = new ArrayList<>();
        dishService.all().forEach(dishes::add);
        dishService.delete(dishes.get(0).getId());
        dishes = new ArrayList<>();
        dishService.all().forEach(dishes::add);
        dishService.delete(dishes.get(0).getId());
    }
}