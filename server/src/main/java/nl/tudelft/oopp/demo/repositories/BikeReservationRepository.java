package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.BikeReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of BikeReservation entities by storing them in the database.
 */
@Repository
public interface BikeReservationRepository extends JpaRepository<BikeReservation, Integer> {
    List<BikeReservation> findAllByBikeId(int bikeId);
}
