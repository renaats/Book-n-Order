package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DISH_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.MENU_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;

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

        @Bean
        public AllergyService allergyService() {
            return new AllergyService();
        }
    }

    @Autowired
    DishService dishService;

    @Autowired
    MenuService menuService;

    @Autowired
    AllergyService allergyService;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    AllergyRepository allergyRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    Menu menu1;
    Menu menu2;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;
    Restaurant restaurant1;
    Restaurant restaurant2;
    Building building;
    Building building2;

    Allergy allergy;
    Set<Allergy> allergySet;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        restaurant1 = new Restaurant(building, "Hangout", "restaurant@tudelft.nl");
        restaurantRepository.save(restaurant1);

        restaurant2 = new Restaurant(building2, "Food station", "restaurant@tudelft.nl");
        restaurantRepository.save(restaurant2);

        menu1 = new Menu("KFC menu", restaurant1);
        menu1.setDishes(dishes);
        menuRepository.save(menu1);

        menu2 = new Menu("BK menu", restaurant2);
        menuRepository.save(menu2);

        allergy = new Allergy("Lactose");
        allergySet = new HashSet<>();
        allergySet.add(allergy);

        dish = new Dish("Chicken", menu1, 300, "Cooked", "123");
        dish.setAllergies(allergySet);

        dish2 = new Dish("Spicy Chicken", menu2, 400, "Grilled", "234");
        dish2.setAllergies(allergySet);

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
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreate() {
        allergyRepository.save(allergy);
        assertEquals(ADDED, dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123"));
        int id = dishService.all().get(0).getId();
        dishService.find(id).setAllergies(allergySet);
        assertEquals(Collections.singletonList(dish), dishService.all());
    }

    /**
     * Tests the creation of an instance with an invalid menu id.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreateIllegalMenu() {
        assertEquals(MENU_NOT_FOUND, dishService.add(dish.getName(), 0, 300, "Cooked", "123"));
    }

    /**
     * Tests the creation of an instance with no permissions.
     */
    @Test
    @WithMockUser(username = "restaurant2@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreateNoPermissions() {
        assertEquals(WRONG_CREDENTIALS, dishService.add(dish.getName(), menu1.getId(), 300, "Cooked", "123"));
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
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindExisting() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        Assertions.assertNotNull(dishService.find(id));
    }

    /**
     * Tests the change of the name of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeName() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertNotEquals("Hamburger", dishService.find(id).getName());
        dishService.update(id, "name", "Hamburger");
        assertEquals("Hamburger", dishService.find(id).getName());
    }

    /**
     * Tests the change of the menu of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeMenu() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertNotEquals(menu2, dishService.find(id).getMenu());
        String menu2Id = Integer.toString(menu2.getId());
        dishService.update(id, "menu", menu2Id);
        assertEquals(menu2, dishService.find(id).getMenu());
    }

    /**
     * Tests the change of the price of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangePrice() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertNotEquals(500, dishService.find(id).getPrice());
        dishService.update(id, "price", "500");
        assertEquals(500, dishService.find(id).getPrice());
    }

    /**
     * Tests the change of the description of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeDescription() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertNotEquals("Grilled", dishService.find(id).getDescription());
        dishService.update(id, "description", "Grilled");
        assertEquals("Grilled", dishService.find(id).getDescription());
    }

    /**
     * Tests the change of the image of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeImage() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertNotEquals("234", dishService.find(id).getImage());
        dishService.update(id, "image", "234");
        assertEquals("234", dishService.find(id).getImage());
    }

    /**
     * Tests the change of a non-existing attribute of the dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeNonExisting() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        int id = dishService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, dishService.update(id, "illegal", "illegal"));
    }

    /**
     * Tests the addition of an allergy to a dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testAddAllergy() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        dish = dishService.all().get(0);
        dishService.addAllergy(dish.getId(), "Nuts");
        dishService.addAllergy(dish.getId(), "Lactose");
        Iterator<Allergy> allergies = dishService.find(dish.getId()).getAllergies().iterator();
        String allergy1 = allergies.next().getAllergyName();
        String allergy2 = allergies.next().getAllergyName();
        String swap;
        if (allergy2.equals("Lactose")) {
            swap = allergy2;
            allergy2 = allergy1;
            allergy1 = swap;
        }
        assertEquals(allergy1, "Lactose");
        assertEquals(allergy2,"Nuts");
    }

    /**
     * Tests the removal of an allergy from a dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testRemoveAllergy() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        dish = dishService.all().get(0);
        dishService.addAllergy(dish.getId(), "Nuts");
        dishService.addAllergy(dish.getId(), "Lactose");
        dishService.removeAllergy(dish.getId(), "Nuts");
        assertEquals(Collections.singletonList(allergy), allergyService.findAllByDishId(dish.getId()));
    }

    /**
     * Tests the retrieval of allergies to a dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetAllergies() {
        dishService.add("Tosti", menu1.getId(), 300, "Cooked", "123");
        dish = dishService.all().get(0);
        dishService.addAllergy(dish.getId(), "Nuts");
        dishService.addAllergy(dish.getId(), "Lactose");
        List<Allergy> allergies = new LinkedList<>();
        allergies.add(allergy);
        Allergy allergy2 = new Allergy("Nuts");
        allergies.add(allergy2);
        assertEquals(allergies, allergyService.findAllByDishId(dish.getId()));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testMultipleInstances() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        Assertions.assertEquals(2, dishService.all().size());
        int id1 = dishService.all().get(0).getId();
        int id2 = dishService.all().get(1).getId();
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dishService.find(id1));
        dishes.add(dishService.find(id2));
        Assertions.assertEquals(dishes, dishService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testDelete() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        int id = dishService.all().get(0).getId();
        Assertions.assertEquals(EXECUTED, dishService.delete(id));
        Assertions.assertEquals(1, dishService.all().size());
    }

    /**
     * Tests the search by menu id.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindByMenuId() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        assertEquals(Collections.singletonList(dish), dishService.findByMenu(dish.getMenu().getId()));
        assertEquals(Collections.singletonList(dish2), dishService.findByMenu(dish2.getMenu().getId()));
    }

    /**
     * Tests the searching of a dish by name.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testSearchByName() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        List<Dish> dishes = dishService.search("name:Chicken");
        Assertions.assertEquals(dishes.get(0).getName(), dish.getName());
        Assertions.assertEquals(dishes.get(1).getName(), dish2.getName());
    }

    /**
     * Tests the searching of a dish by part of the name.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testSearchByPartOfName() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        List<Dish> dishes = dishService.search("name:Spicy");
        assertEquals(1, dishes.size());
        Assertions.assertEquals(dishes.get(0).getName(), dish2.getName());
    }

    /**
     * Tests the searching of a nonexistent dish.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testSearchNonexistentDish() {
        dishService.add(dish.getName(), dish.getMenu().getId(), 300, "Cooked", "123");
        dishService.add(dish2.getName(), dish2.getMenu().getId(), 400, "Grilled", "234");
        List<Dish> dishes = dishService.search("name:soup");
        Assertions.assertFalse(dishes.contains(dish));
        Assertions.assertFalse(dishes.contains(dish2));
    }
    
    /**
     * Tests the deletion of a nonexistent dish.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(DISH_NOT_FOUND, dishService.delete(0));
    }
}