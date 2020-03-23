package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Building entities by storing them in the database.
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Building findByName(String name);

    boolean existsByName(String name);
}
