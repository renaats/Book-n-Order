package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.services.MenuService;
import nl.tudelft.oopp.demo.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the MenuService service.
 */
@DataJpaTest
public class MenuServiceTest {
    @TestConfiguration
    static class MenuServiceTestConfiguration {
        @Bean
        public MenuService menuService() {
            return new MenuService();
        }

        @Bean
        public RestaurantService restaurantService() {
            return new RestaurantService();
        }
    }

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishRepository dishRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    Menu menu1;
    Menu menu2;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;
    Building building;
    Building building2;
    Restaurant restaurant1;
    Restaurant restaurant2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building2 = new Building("EWI2", "Mekelweg2", 42);
        buildingRepository.save(building2);

        restaurant1 = new Restaurant(building, "Hangout");
        restaurantRepository.save(restaurant1);

        restaurant2 = new Restaurant(building2, "Food station");
        restaurantRepository.save(restaurant2);

        dish = new Dish("Chicken", menu1);
        dishRepository.save(dish);

        dish2 = new Dish("Spicy Chicken", menu2);
        dishRepository.save(dish2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);

        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);

        menu2 = new Menu("BK menu", restaurant2);

        building = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(building);
    }

    @Test
    public void testCreate() {
        assertEquals(201, menuService.add(menu1.getName(), menu1.getRestaurant().getId()));
    }

    @Test
    public void testAdded() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        menuService.add(menu2.getName(), menu2.getRestaurant().getId());
        List<Menu> menus = new ArrayList<>();
        menuService.all().forEach(menus::add);
        assertEquals(2, menus.size());
    }

    @Test
    public void testErrorCode() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        menuService.add(menu2.getName(), menu2.getRestaurant().getId());
        List<Menu> menus = new ArrayList<>();
        menuService.all().forEach(menus::add);
        assertEquals(200, menuService.delete(menus.get(0).getId()));
    }

    @Test
    public void testDelete() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        menuService.add(menu2.getName(), menu2.getRestaurant().getId());
        List<Menu> menus = new ArrayList<>();
        menuService.all().forEach(menus::add);
        menuService.delete(menus.get(0).getId());
        List<Menu> menus2 = new ArrayList<>();
        menuService.all().forEach(menus2::add);
        assertEquals(1, menus2.size());
    }
}
