package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    Allergy allergy3;
    Dish dish1;
    Dish dish2;
    Menu menu;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {

        menu = new Menu();
        menuRepository.save(menu);

        dish1 = new Dish("Hamburger", menu);
        dishRepository.save(dish1);

        dish2 = new Dish("Tosti", menu);
        dishRepository.save(dish2);

        allergy1 = new Allergy("Lactose", dish1);
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
    public void saveAndRetrieveBike() {
        allergy2 = allergyRepository.findAll().get(0);
        assertEquals(allergy1, allergy2);
    }

    /**
     * Tests the getters of the Allergy class.
     */
    @Test
    public void testGetters() {
        allergy3 = allergyRepository.findAll().get(0);
        assertEquals(allergy3, allergy1);
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
