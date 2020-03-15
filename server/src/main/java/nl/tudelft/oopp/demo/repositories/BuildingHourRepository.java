package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BuildingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingHourRepository extends JpaRepository<BuildingHours, Integer> {
    BuildingHours findByBuilding_IdAndDay(int buildingId, int day);

    boolean existsByBuilding_IdAndDay(int buildingId, int day);

    void deleteByBuilding_IdAndDay(int buildingId, int day);
}
