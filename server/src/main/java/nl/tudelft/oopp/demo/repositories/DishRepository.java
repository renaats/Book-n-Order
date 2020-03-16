package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
