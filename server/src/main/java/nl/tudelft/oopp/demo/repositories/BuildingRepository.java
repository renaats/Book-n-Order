package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    List<Building> findByid(int id);
    List<Building> findByname(String Name);
    List<Building> findByroomsid(int id);
}
