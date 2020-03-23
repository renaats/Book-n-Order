package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
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

    @Autowired
    AllergyRepository allergyRepository;

    @Autowired
    DishRepository dishRepository;

    Menu menu1;
    Menu menu2;
    Dish dish;
    Dish dish2;
    Set<Dish> dishes;
    Restaurant restaurant1;
    Restaurant restaurant2;

    Allergy allergy;
    Set<Allergy> allergySet;

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

        allergy = new Allergy("Lactose");
        allergySet = new HashSet<>();
        allergySet.add(allergy);

        dish = new Dish("Chicken", menu1);
        dish.setAllergies(allergySet);
        //dishRepository.save(dish);

        dish2 = new Dish("Spicy Chicken", menu2);
        dish2.setAllergies(allergySet);
        //dishRepository.save(dish2);

        dishes = new HashSet<>();
        dishes.add(dish);
        dishes.add(dish2);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(dishService);
    }

    /**
     * Tests the saving and retrieval of an instance of Dish.
     */
    @Test
    public void testCreate() {
        allergyRepository.save(allergy);
        assertEquals(201, dishService.add(dish.getName(), dish.getMenu().getId()));
        int id = dishService.all().get(0).getId();
        dishService.find(id).setAllergies(allergySet);
        assertEquals(Collections.singletonList(dish), dishService.all());
    }

    /**
     * Tests the creation of an instance with an invalid menu id.
     */
    @Test
    public void testCreateIllegalMenu() {
        assertEquals(429, dishService.add(dish.getName(), 0));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(dishService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        int id = dishService.all().get(0).getId();
        assertNotNull(dishService.find(id));
    }

    /**
     * Tests the change of the name of the dish.
     */
    @Test
    public void testChangeName() {
        dishService.add("Tosti", menu1.getId());
        int id = dishService.all().get(0).getId();
        assertNotEquals("Hamburger", dishService.find(id).getName());
        dishService.update(id, "name", "Hamburger");
        assertEquals("Hamburger", dishService.find(id).getName());
    }

    /**
     * Tests the addition of an allergy to a dish.
     */
    @Test
    public void testAddAllergy() {
        dishService.add("Tosti", menu1.getId());
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
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        assertEquals(2, dishService.all().size());
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish);
        dishes.add(dish2);
        assertEquals(dishes.size(), dishService.all().size());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        dishService.add(dish.getName(), dish.getMenu().getId());
        dishService.add(dish2.getName(), dish2.getMenu().getId());
        int id = dishService.all().get(0).getId();
        assertEquals(200, dishService.delete(id));
        assertEquals(1, dishService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(430, dishService.delete(0));
    }
}