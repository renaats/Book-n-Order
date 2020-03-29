package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Allergy entity.
 */
@DataJpaTest
public class AllergyTest {
    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;

    Allergy allergy1;
    Allergy allergy2;
    Menu menu;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        menu = new Menu();

        allergy1 = new Allergy("Lactose");
        allergy1.setDish(new HashSet<>());
        allergyRepository.save(allergy1);
    }

    /**
     * Tests the constructor of the Allergy class
     */
    @Test
    public void testConstructor() {
        allergy2 = new Allergy();
        assertNotNull(allergy2);
    }

    /**
     * Tests the saving and retrieval of an instance of Allergy.
     */
    @Test
    public void saveAndRetrieveAllergy() {
        allergy2 = allergyRepository.findAll().get(0);
        assertEquals(allergy1.getAllergyName(), allergy2.getAllergyName());
    }

    /**
     * Tests the getters of the Allergy class.
     */
    @Test
    public void testGetters() {
        allergy2 = allergyRepository.findAll().get(0);
        Set<Dish> dishSet = new HashSet<>();
        assertEquals(allergy1.getAllergyName(), allergy2.getAllergyName());
        assertEquals(dishSet, allergy1.getDish());
    }

    /**
     * Tests the setters of the Allergy class
     */
    @Test
    public void testSetters() {
        Dish dish = new Dish();
        Set<Dish> dishSet = new HashSet<>();
        dishSet.add(dish);
        allergy2 = new Allergy("Lactose");
        allergy2.setDish(dishSet);
        assertEquals(allergy2.getAllergyName(), "Lactose");
        assertEquals(allergy2.getDish(), dishSet);
    }

    /**
     * Tests allergy addition to a dish.
     */
    @Test
    public void testDishAllergy() {
        Dish dish = new Dish("Tosti", menu);
        dish.setAllergies(new HashSet<>());

        Set<Allergy> allergySet = new HashSet<>();
        allergySet.add(allergy1);

        dish.setAllergies(new HashSet<>());
        dish.addAllergy(allergy1);
        assertEquals(allergySet, dish.getAllergies());
    }

    /**
     * Tests the addDish method of the Allergy class.
     */
    @Test
    public void testAddDish() {
        Dish dish = new Dish();
        allergy1.addDish(dish);
        assertTrue(allergy1.getDish().contains(dish));
    }

    /**
     * Tests the deleteDish method of the Allergy class.
     */
    @Test
    public void testDeleteDish() {
        Dish dish = new Dish();
        Set<Dish> dishSet = new HashSet<>();
        dishSet.add(dish);
        allergy1.setDish(dishSet);
        allergy1.deleteDish(dish);
        assertFalse(allergy1.getDish().contains(dish));
    }

    /**
     * Tests the deleteAllDish method of the Allergy class.
     */
    @Test
    public void testDeleteAllDish() {
        Dish dish = new Dish();
        Set<Dish> dishSet = new HashSet<>();
        dishSet.add(dish);
        allergy1.setDish(dishSet);
        allergy1.deleteAllDish();
        assertTrue(allergy1.getDish().isEmpty());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        allergyRepository.deleteAll();
        dishRepository.deleteAll();
        menuRepository.deleteAll();
    }
}
