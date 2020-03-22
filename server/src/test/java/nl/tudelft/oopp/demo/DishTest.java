package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;

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
    Menu menu3;
    Restaurant restaurant1;
    Restaurant restaurant2;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;
    Set<Dish> dishes1;
    Set<Dish> dishes2;

    /**
     * Setup for the tests
     */
    @BeforeEach
    public void setup() {
        dish = new Dish("Chicken", menu1);
        dishRepository.saveAndFlush(dish);

        dish2 = new Dish("Spicy Chicken", menu2);
        dishRepository.saveAndFlush(dish2);

        dishes = new HashSet<>();
        dishes1 = new HashSet<>();
        dishes2 = new HashSet<>();

        dishes.add(dish);
        dishes.add(dish2);
        dishes1.add(dish);

        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);
        menuRepository.saveAndFlush(menu1);

        menu2 = new Menu("BK menu", restaurant2);
        menuRepository.saveAndFlush(menu2);
    }

    @Test
    public void testDishesFalse() {
        assertNotEquals(dishes, dishes1);
    }

    @Test
    public void testDishesTrue() {
        dishes2.add(dishRepository.findAll().get(0));
        dishes2.add(dishRepository.findAll().get(1));
        assertEquals(dishes, dishes2);
    }

    @Test
    public void testGetters() {
        menu3 = menuRepository.findAll().get(1);
        assertEquals(menu3, menu2);
    }

    @Test
    public void testNotEqualsDishes() {
        assertNotEquals(menu1.getDishes(), menu2.getDishes());
    }
}
