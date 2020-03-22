package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Supports the persistence of Dish entities by storing them in the database.
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {
    Dish findByName(String name);
}
