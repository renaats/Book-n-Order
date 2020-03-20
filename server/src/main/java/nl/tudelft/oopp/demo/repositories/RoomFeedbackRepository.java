package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.RoomFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Room Feedback entities by storing them in the database.
 */
@Repository
public interface RoomFeedbackRepository extends JpaRepository<RoomFeedback, Integer> {

}
