package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Room Feedback entities by storing them in the database.
 */
@Repository
public interface BikeFeedbackRepository extends JpaRepository<BikeFeedback, Integer> {

}