package nl.tudelft.oopp.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.specifications.DishSpecification;
import nl.tudelft.oopp.demo.specifications.SearchCriteria;

import org.junit.jupiter.api.AfterEach;
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
    private Menu menu;

    /**
     * Initializes variables before each test.
     */
    @BeforeEach
    public void init() {
        menu = new Menu();
        menuRepository.save(menu);

        dish1 = new Dish("Tosti", menu);
        dishRepository.save(dish1);

        dish2 = new Dish("Doner Kebab", menu);
        dishRepository.save(dish2);
    }

    @Test
    public void nameSearchTest() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Tosti"));
        List<Dish> results = dishRepository.findAll(spec);
        assertThat(dish1, in(results));
        assertEquals(dish1, results.get(0));
    }

    @Test
    public void compoundSearch() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Tosti"));
        DishSpecification spec2 = new DishSpecification(new SearchCriteria("name", ":","Doner Kebab"));
        List<Dish> results = dishRepository.findAll(spec.or(spec2));
        assertThat(dish1, in(results));
        assertThat(dish2, in(results));
    }

    @Test
    public void nonexistentDishSearch() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Vla"));
        List<Dish> results = dishRepository.findAll(spec);
        assertThat(dish1, not(in(results)));
        assertEquals(0, results.size());
    }
}

