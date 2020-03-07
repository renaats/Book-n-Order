package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {

}
