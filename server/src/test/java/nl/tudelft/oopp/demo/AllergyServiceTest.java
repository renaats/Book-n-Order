package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;
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
        assertEquals(201, allergyService.add(allergy1.getAllergyName()));
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
        assertEquals(200, allergyService.delete(name));
        assertEquals(0, allergyService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(416, allergyService.delete("Vegan"));
    }
}
