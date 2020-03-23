package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of Role entities by storing them in the database.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    boolean existsByName(String roleName);
}
