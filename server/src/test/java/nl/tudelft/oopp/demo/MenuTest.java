package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    Set<Dish> dishes1;

    /**
     * Setup for all the tests
     */
    @BeforeEach
    public void setup() {
        dish = new Dish();
        dish.setMenu(menu1);
        dish.setName("Chicken");
        dishRepository.saveAndFlush(dish);

        dish2 = new Dish();
        dish.setMenu(menu2);
        dish.setName("Spicy Chicken");
        dishRepository.saveAndFlush(dish2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);

        menu1 = new Menu();
        menu1.setName("KFC menu");
        menu1.setRestaurant(restaurant1);
        menu1.setDishes(dishes);
        menuRepository.saveAndFlush(menu1);

        menu2 = new Menu();
        menu2.setName("BK menu");
        menu2.setRestaurant(restaurant2);
        menuRepository.saveAndFlush(menu2);

        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);
        buildingRepository.saveAndFlush(building);

        restaurant1 = new Restaurant();
        restaurant1.setBuilding(building);
        restaurant1.setName("KFC");
        restaurant1.setMenu(menu1);
        restaurantRepository.saveAndFlush(restaurant1);

        restaurant2 = new Restaurant();
        restaurant2.setBuilding(building);
        restaurant2.setName("Burger King");
        restaurant2.setMenu(menu2);
        restaurantRepository.saveAndFlush(restaurant2);
    }

    @Test
    public void testRestaurant() {
        restaurant3 = restaurantRepository.findAll().get(1);
        assertEquals(restaurant3, restaurant2);
    }

    @Test
    public void testRestaurantFalse() {
        restaurant3 = restaurantRepository.findAll().get(1);
        assertNotEquals(restaurant3, restaurant1);
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
