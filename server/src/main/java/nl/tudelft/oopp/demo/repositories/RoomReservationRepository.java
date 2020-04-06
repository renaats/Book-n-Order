package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.RoomReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of RoomReservation entities by storing them in the database.
 */
@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
    List<RoomReservation> findAllByRoomId(int roomId);
}
