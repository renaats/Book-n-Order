package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.DishOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Building entities by storing them in the database.
 */
@Repository
public interface DishOrderRepository extends JpaRepository<DishOrder, Integer> {
    List<DishOrder> findAllByFoodOrderId(int foodOrderId);
}
