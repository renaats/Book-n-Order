package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BuildingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of BuildingHours entities by storing them in the database.
 */
@Repository
public interface BuildingHourRepository extends JpaRepository<BuildingHours, Integer> {
    BuildingHours findByBuilding_IdAndDay(int buildingId, long day);

    boolean existsByBuilding_IdAndDay(int buildingId, long day);

    void deleteByBuilding_IdAndDay(int buildingId, long day);
}
