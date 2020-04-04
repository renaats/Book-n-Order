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
import org.springframework.security.test.context.support.WithMockUser;

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
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT", "ADMIN"})
    public void setup() {
        allergy1 = new Allergy();
        allergy1.setAllergyName("Lactose");

        allergy2 = new Allergy();
        allergy2.setAllergyName("Nuts");

        building = new Building("Building Name", "street", "faculty", 1);
        buildingService.add(building.getName(), building.getStreet(), building.getFaculty(), building.getId());
        restaurant = new Restaurant(building,"restaurant","email@tudelft.nl");
        restaurantService.add((buildingService.all().get(0).getId()), restaurant.getName(), restaurant.getEmail());
        menu = new Menu("Menu", restaurant);
        menuService.add(menu.getName(), restaurantService.all().get(0).getId());
        dish = new Dish("dish", menu, 1, "pasta", "NA");
        dishService.add(dish.getName(), menuService.all().get(0).getId(), 1, "pasta", "NA");
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testConstructor() {
        assertNotNull(allergyService);
    }

    /**
     * Tests the saving and retrieval of an instance of Allergy.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testCreate() {
        assertEquals(ADDED, allergyService.add(allergy1.getAllergyName()));
        assertEquals(Collections.singletonList(allergy1), allergyService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testFindNonExisting() {
        assertNull(allergyService.findByAllergyName("Peanuts"));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testFindExisting() {
        allergyService.add(allergy1.getAllergyName());
        String name = allergyService.all().get(0).getAllergyName();
        assertNotNull(allergyService.findByAllergyName(name));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
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
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
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
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testDeleteIllegal() {
        assertEquals(ID_NOT_FOUND, allergyService.delete("Vegan"));
    }

    
    /**
     * Tests the searching of allergies by name.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testSearchByName() {
        allergyService.add(allergy1.getAllergyName());
        allergyService.add(allergy2.getAllergyName());
        List<Allergy> allergies = allergyService.search("allergyName:Lactose");
        assertEquals(1, allergies.size());
        assertEquals(allergy1, allergies.get(0));
    }

    /**
     * Test the searching of allergies by part of the name.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testSearchByPartOfTheName() {
        allergy2 = new Allergy("Crustacean Shellfish");
        allergyService.add(allergy1.getAllergyName());
        allergyService.add(allergy2.getAllergyName());
        List<Allergy> allergies = allergyService.search("allergyName:Shellfish");
        assertEquals(1, allergies.size());
        assertEquals(allergy2, allergies.get(0));
    }

    /**
     * Tests the searching of a nonexistent allergy.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"ADMIN", "STAFF", "RESTAURANT"})
    public void testSearchNonExistentAllergy() {
        allergyService.add(allergy1.getAllergyName());
        allergyService.add(allergy2.getAllergyName());
        List<Allergy> allergies = allergyService.search("allergyName:Gluten");
        assertEquals(0, allergies.size());
    }
}
