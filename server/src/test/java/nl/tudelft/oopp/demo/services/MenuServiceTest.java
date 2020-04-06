package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.MENU_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;

/**
 * Tests the Menu service.
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

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        restaurant1 = new Restaurant(building, "Hangout", "restaurant@tudelft.nl");
        restaurantRepository.save(restaurant1);

        restaurant2 = new Restaurant(building2, "Food station", "restaurant@tudelft.nl");
        restaurantRepository.save(restaurant2);

        dish = new Dish("Chicken", menu1, 300, "Cooked", "123");
        dishRepository.save(dish);

        dish2 = new Dish("Spicy Chicken", menu2, 400, "Grilled", "234");
        dishRepository.save(dish2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);

        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);

        menu2 = new Menu("BK menu", restaurant2);

        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(menuService);
    }

    /**
     * Tests the creation of a valid instance of Menu.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreate() {
        assertEquals(ADDED, menuService.add(menu1.getName(), menu1.getRestaurant().getId()));
        menuService.all().get(0).setDishes(dishes);
        assertEquals(Collections.singletonList(menu1), menuService.all());
    }

    /**
     * Tests the creation of an instance with an invalid restaurant id.
     */
    @Test
    public void testCreateIllegalRestaurant() {
        assertEquals(RESTAURANT_NOT_FOUND, menuService.add(menu1.getName(), 0));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(menuService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindExisting() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        int id = menuService.all().get(0).getId();
        assertNotNull(menuService.find(id));
    }

    /**
     * Tests the search by name for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindExistingByName() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        assertNotNull(menuService.find(menu1.getName()));
    }

    /**
     * Tests the search by restaurant for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindExistingByRestaurant() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        assertNotNull(menuService.findRestaurant(menu1.getRestaurant().getId()));
    }

    /**
     * Tests the change of name for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeName() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        int id = menuService.all().get(0).getId();
        assertNotEquals("NewName", menuService.find(id).getName());
        menuService.changeMenuName(id, "NewName");
        assertEquals("NewName", menuService.find(id).getName());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testMultipleInstances() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        menuService.all().get(0).setDishes(dishes);
        menuService.add(menu2.getName(), menu2.getRestaurant().getId());
        assertEquals(2, menuService.all().size());
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);
        assertEquals(menus, menuService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testDelete() {
        menuService.add(menu1.getName(), menu1.getRestaurant().getId());
        menuService.add(menu2.getName(), menu2.getRestaurant().getId());
        int id = menuService.all().get(0).getId();
        menuService.delete(id);
        assertEquals(1, menuService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(MENU_NOT_FOUND, menuService.delete(0));
    }
}
