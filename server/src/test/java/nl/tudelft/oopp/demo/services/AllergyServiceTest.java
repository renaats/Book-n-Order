package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import nl.tudelft.oopp.demo.entities.Allergy;

import nl.tudelft.oopp.demo.entities.Dish;
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

    Allergy allergy1;
    Allergy allergy2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        allergy1 = new Allergy();
        allergy1.setAllergyName("Lactose");

        allergy2 = new Allergy();
        allergy2.setAllergyName("Nuts");
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
        Dish dish = new Dish();
        int dishId = dish.getId();
        allergyService.add(allergy1.getAllergyName());
        String id = allergyService.all().get(0).getAllergyName();
        assertFalse(allergyService.findByAllergyName(id).getDish().contains(dish));
        allergyService.update(id, "dishAdd", "" + dishId);
        assertTrue(allergyService.findByAllergyName(id).getDish().contains(dish));
    }

    /**
     * Tests the adding of a dish to an allergy.
     */
    @Test
    public void testDeleteDish() {
        Dish dish = new Dish();
        int dishId = dish.getId();
        Set<Dish> dishSet = new HashSet<>();
        allergy1.setDish(dishSet);
        allergyService.add(allergy1.getAllergyName());
        String id = allergyService.all().get(0).getAllergyName();
        assertTrue(allergyService.findByAllergyName(id).getDish().contains(dish));
        allergyService.update(id, "dishDelete", "" + dishId);
        assertFalse(allergyService.findByAllergyName(id).getDish().contains(dish));
    }
}
