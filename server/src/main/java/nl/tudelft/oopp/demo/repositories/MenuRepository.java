package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Supports the persistence of Menu entities by storing them in the database.
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Menu findByName(String name);

    Menu findByRestaurantId(int restaurantId);
}
