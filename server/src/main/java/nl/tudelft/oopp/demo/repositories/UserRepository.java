package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of AppUser entities by storing them in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    AppUser findByEmail(String email);
}
