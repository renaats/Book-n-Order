package nl.tudelft.oopp.demo.specifications;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Transactional
@DataJpaTest
public class DishesJpaSpecificationsTest {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;

    private Dish dish1;
    private Dish dish2;

    /**
     * Initializes variables before each test.
     */
    @BeforeEach
    public void setup() {
        Menu menu = new Menu();
        menuRepository.save(menu);

        dish1 = new Dish("Tosti", menu, 300, "Cooked", "123");
        dishRepository.save(dish1);

        dish2 = new Dish("Doner Kebab", menu, 400, "Grilled", "234");
        dishRepository.save(dish2);
    }

    /**
     * Tests querying on a name of the dish.
     */
    @Test
    public void testNameSearch() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Tosti"));
        List<Dish> results = dishRepository.findAll(spec);
        assertThat(dish1, in(results));
        assertEquals(dish1, results.get(0));
    }

    /**
     * Tests querying on two specifications.
     */
    @Test
    public void testCompoundSearch() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Tosti"));
        DishSpecification spec2 = new DishSpecification(new SearchCriteria("name", ":","Doner Kebab"));
        List<Dish> results = dishRepository.findAll(spec.or(spec2));
        assertThat(dish1, in(results));
        assertThat(dish2, in(results));
    }

    /**
     * Tests querying on a nonexistent dish name.
     */
    @Test
    public void testNonexistentDishSearch() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Vla"));
        List<Dish> results = dishRepository.findAll(spec);
        assertEquals(0, results.size());
    }
}

