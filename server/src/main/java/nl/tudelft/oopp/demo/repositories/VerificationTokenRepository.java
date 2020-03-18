package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of VerificationToken entities by storing them in the database.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    VerificationToken findByToken(String token);
}
