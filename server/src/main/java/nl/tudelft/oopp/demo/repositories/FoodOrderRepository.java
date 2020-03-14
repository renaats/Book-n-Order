package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {
}