package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.services.AllergyService;
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
    }

    @Autowired
    AllergyService allergyService;

    @Autowired
    DishRepository dishRepository;

    @Autowired
    MenuRepository menuRepository;

    Dish dish1;
    Dish dish2;
    Allergy allergy1;
    Allergy allergy2;
    Menu menu;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {

        menu = new Menu();
        menuRepository.save(menu);

        dish1 = new Dish("Tosti", menu);
        dishRepository.save(dish1);

        dish2 = new Dish("Hamburger", menu);
        dishRepository.save(dish2);

        allergy1 = new Allergy("lactose", dish1);
        allergy2 = new Allergy("nuts", dish2);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(allergyService);
    }

    /**
     * Tests the creation of an instance with an invalid dish id.
     */
    @Test
    public void testCreateIllegalDish() {
        assertEquals(416, allergyService.add("nuts", 0));
    }

    /**
     * Tests the saving and retrieval of an instance of Allergy.
     */
    @Test
    public void testAdd() {
        assertEquals(201, allergyService.add("lactose", dish1.getId()));
        assertEquals(Collections.singletonList(allergy1), allergyService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(allergyService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        allergyService.add("lactose", dish1.getId());
        int id = allergyService.all().get(0).getId();
        assertNotNull(allergyService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(416, allergyService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        allergyService.add("lactose", dish1.getId());
        int id = allergyService.all().get(0).getId();
        assertEquals(412, allergyService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the availability by using the service.
     */
    @Test
    public void testChangeAllergyName() {
        allergyService.add("lactose", dish1.getId());
        int id = allergyService.all().get(0).getId();
        allergyService.update(id, "allergyName", "nuts");
        assertNotEquals("lactose", allergyService.find(id).getAllergyName());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        allergyService.add("lactose", dish1.getId());
        allergyService.add("nuts", dish2.getId());
        assertEquals(2, allergyService.all().size());
        ArrayList<Allergy> allergies = new ArrayList<>();
        allergies.add(allergy1);
        allergies.add(allergy2);
        assertEquals(allergies, allergyService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        allergyService.add("lactose", dish1.getId());
        int id = allergyService.all().get(0).getId();
        assertEquals(200, allergyService.delete(id));
        assertEquals(0, allergyService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(404, allergyService.delete(0));
    }
}
