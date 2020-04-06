package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Dish entities by storing them in the database.
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Integer>, JpaSpecificationExecutor<Dish> {
    Dish findByName(String name);

    boolean existsByName(String allergyName);

    List<Dish> findAllByMenuId(int menuId);

    boolean existsByNameAndMenuId(String name, int menuId);
}
