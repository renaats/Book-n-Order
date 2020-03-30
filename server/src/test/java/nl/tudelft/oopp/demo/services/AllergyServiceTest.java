package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the AllergyService.
 */
@DataJpaTest
public class AllergyServiceTest {
    @TestConfiguration
    static class AllergyServiceTestConfiguration {
        @Bean
        public AllergyService allergyService() {
            return new AllergyService();
        }

        @Bean
        public DishService dishService() {
            return new DishService();
        }

        @Bean
        public MenuService menuService() {
            return new MenuService();
        }

        @Bean
        public RestaurantService restaurantService() {
            return new RestaurantService();
        }

        @Bean
        public BuildingService buildingService() {
            return new BuildingService();
        }
    }

    @Autowired
    AllergyService allergyService;
    @Autowired
    DishService dishService;
    @Autowired
    MenuService menuService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    BuildingService buildingService;

    Allergy allergy1;
    Allergy allergy2;
    Restaurant restaurant;
    Menu menu;
    Dish dish;
    Building building;


    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        allergy1 = new Allergy();
        allergy1.setAllergyName("Lactose");

        allergy2 = new Allergy();
        allergy2.setAllergyName("Nuts");

        building = new Building("Building Name", "street", 1);
        buildingService.add(building.getName(), building.getStreet(), building.getId());
        restaurant = new Restaurant(building,"restaurant");
        restaurantService.add((buildingService.all().get(0).getId()), restaurant.getName());
        menu = new Menu("Menu", restaurant);
        menuService.add(menu.getName(), restaurantService.all().get(0).getId());
        dish = new Dish("dish", menu);
        dishService.add("dish",menuService.all().get(0).getId());
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(allergyService);
    }

    /**
     * Tests the saving and retrieval of an instance of Allergy.
     */
    @Test
    public void testCreate() {
        assertEquals(ADDED, allergyService.add(allergy1.getAllergyName()));
        assertEquals(Collections.singletonList(allergy1), allergyService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(allergyService.findByAllergyName("Peanuts"));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        allergyService.add(allergy1.getAllergyName());
        String name = allergyService.all().get(0).getAllergyName();
        assertNotNull(allergyService.findByAllergyName(name));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        allergyService.add(allergy1.getAllergyName());
        allergyService.add(allergy2.getAllergyName());
        assertEquals(2, allergyService.all().size());
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(allergy1);
        allergies.add(allergy2);
        assertEquals(allergies, allergyService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        allergyService.add(allergy2.getAllergyName());
        String name = allergyService.all().get(0).getAllergyName();
        assertEquals(EXECUTED, allergyService.delete(name));
        assertEquals(0, allergyService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(ID_NOT_FOUND, allergyService.delete("Vegan"));
    }

    /**
     * Tests the adding of a dish to an allergy.
     */
    @Test
    public void testAddDish() {
        allergyService.add(allergy1.getAllergyName());
        String id = allergyService.all().get(0).getAllergyName();
        assertNull(allergyService.findByAllergyName(id).getDish());
        allergyService.update(id, "dishAdd", "" + dishService.all().get(0).getId());
        assertTrue(allergyService.findByAllergyName(id).getDish().contains(dishService.all().get(0)));
    }

    /**
     * Tests the deleting of a dish of an allergy.
     */
    @Test
    public void testDeleteDish() {
        allergyService.add(allergy1.getAllergyName());
        String id = allergyService.all().get(0).getAllergyName();
        assertNull(allergyService.findByAllergyName(id).getDish());
        allergyService.update(id, "dishAdd", "" + dishService.all().get(0).getId());
        assertTrue(allergyService.findByAllergyName(id).getDish().contains(dishService.all().get(0)));
        allergyService.update(id, "dishDelete", "" + dishService.all().get(0).getId());
        assertFalse(allergyService.findByAllergyName(id).getDish().contains(dishService.all().get(0)));
    }

    /**
     * Tests the deleting all dishes of an allergy.
     */
    @Test
    public void testDeleteAllDish() {
        allergyService.add(allergy1.getAllergyName());
        String id = allergyService.all().get(0).getAllergyName();
        assertNull(allergyService.findByAllergyName(id).getDish());
        allergyService.update(id, "dishAdd", "" + dishService.all().get(0).getId());
        assertTrue(allergyService.findByAllergyName(id).getDish().contains(dishService.all().get(0)));
        allergyService.update(id, "dishAll", "");
        assertFalse(allergyService.findByAllergyName(id).getDish().isEmpty());
    }
}
