package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    AppUser findByEmail(String email);
}
