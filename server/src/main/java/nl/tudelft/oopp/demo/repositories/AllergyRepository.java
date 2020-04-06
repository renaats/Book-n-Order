package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Allergy entities by storing them in the database.
 */
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, String>, JpaSpecificationExecutor<Allergy> {
    Allergy findByAllergyName(String allergyName);

    void deleteByAllergyName(String allergyName);

    boolean existsByAllergyName(String allergyName);

    List<Allergy> findAllByDishesId(int dishId);
}