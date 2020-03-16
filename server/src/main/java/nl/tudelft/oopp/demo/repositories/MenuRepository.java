package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
