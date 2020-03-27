package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.RestaurantHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Supports the persistence of RestaurantHour entities by storing them in the database.
 */
@Repository
public interface RestaurantHourRepository extends JpaRepository<RestaurantHours, Integer> {
    RestaurantHours findByRestaurant_IdAndDay(int restaurantId, long day);

    boolean existsByRestaurant_IdAndDay(int restaurantId, long day);

    void deleteByRestaurant_IdAndDay(int restaurantId, long day);
}
