package nl.tudelft.oopp.demo.specifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.repositories.AllergyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Transactional
@DataJpaTest
public class AllergiesJpaSpecificationsTest {
    @Autowired
    private AllergyRepository allergyRepository;

    private Allergy allergy;
    private Allergy allergy2;

    /**
     * Initializes variables before each test.
     */
    @BeforeEach
    public void setup() {
        allergy = new Allergy();
        allergy.setAllergyName("Nuts");
        allergyRepository.save(allergy);

        allergy2 = new Allergy();
        allergy2.setAllergyName("Lactose");
        allergyRepository.save(allergy2);
    }

    /**
     * Tests querying on a name of the allergy.
     */
    @Test
    public void testNameSearch() {
        AllergySpecification spec = new AllergySpecification(new SearchCriteria("allergyName", ":", "Nuts"));
        List<Allergy> allergies = allergyRepository.findAll(spec);
        assertEquals(1, allergies.size());
        assertEquals(allergy, allergies.get(0));
    }

    /**
     * Tests querying on two specifications.
     */
    @Test
    public void testCompoundSearch() {
        AllergySpecification spec = new AllergySpecification(new SearchCriteria("allergyName", ":", "Nuts"));
        AllergySpecification spec2 = new AllergySpecification(new SearchCriteria("allergyName", ":", "Lactose"));
        List<Allergy> allergies = allergyRepository.findAll(spec.or(spec2));
        assertTrue(allergies.contains(allergy));
        assertTrue(allergies.contains(allergy2));
    }

    /**
     * Tests querying on a nonexistent allergy name.
     */
    @Test
    public void testNonexistentAllergySearch() {
        AllergySpecification spec = new AllergySpecification(new SearchCriteria("allergyName", ":", "Gluten"));
        assertNotNull(spec);
        List<Allergy> allergies = allergyRepository.findAll(spec);
        assertEquals(0, allergies.size());
    }
}
