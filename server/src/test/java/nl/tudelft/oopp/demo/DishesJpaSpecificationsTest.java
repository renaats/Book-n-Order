package nl.tudelft.oopp.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;

import java.util.List;
import javax.transaction.Transactional;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.specifications.DishSpecification;
import nl.tudelft.oopp.demo.specifications.SearchCriteria;

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

        dish1 = new Dish();
        dish1.setName("Tosti");
        dish1.setMenu(menu);
        dishRepository.save(dish1);

        dish2 = new Dish();
        dish2.setName("Doner Kebab");
        dish2.setMenu(menu);
        dishRepository.save(dish2);
    }

    @Test
    public void nameSearchTest() {
        DishSpecification spec = new DishSpecification(new SearchCriteria("name", ":", "Tosti"));

        List<Dish> results = dishRepository.findAll(spec);
        assertThat(dish1, in(results));
        assertThat(dish2, not(in(results)));
    }

}

