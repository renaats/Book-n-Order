package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Allergy entities by storing them in the database.
 */
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, String> {
    Allergy findByAllergyName(String allergyName);

    void deleteByAllergyName(String allergyName);

    boolean existsByAllergyName(String allergyName);
}