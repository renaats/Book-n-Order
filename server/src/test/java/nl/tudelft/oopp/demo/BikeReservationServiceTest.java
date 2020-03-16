package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BikeRepository;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.BikeReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Tests the BikeReservationService service.
 */
@DataJpaTest
public class BikeReservationServiceTest {
    @TestConfiguration
    static class BikeReservationServiceTestConfiguration {
        @Bean
        public BikeReservationService bikeReservationService() {
            return new BikeReservationService();
        }
    }

    @Autowired
    BikeReservationService bikeReservationService;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    Building fromBuilding;
    Building toBuilding;
    Building toBuilding2;
    Bike bike;
    AppUser appUser;
    Date fromTime;
    Date toTime;
    long fromTimeMs;
    long toTimeMs;
    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;
    
    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI");
        appUser.setBikeReservations(new HashSet<>());
        userRepository.save(appUser);

        fromBuilding = new Building("Sporthal", "Mekelweg", 8);
        buildingRepository.save(fromBuilding);

        toBuilding = new Building("EWI", "Mekelweg", 4);
        buildingRepository.save(toBuilding);

        toBuilding2 = new Building("Library", "Prometheusplein", 1);
        buildingRepository.save(toBuilding2);

        bike = new Bike(fromBuilding, true);
        bikeRepository.save(bike);

        fromTime = new Date(10000000000L);
        fromTimeMs = fromTime.getTime();

        toTime = new Date(11000000000L);
        toTimeMs = toTime.getTime();

        bikeReservation = new BikeReservation(bike, appUser, fromBuilding, toBuilding, fromTime, toTime);

        bikeReservation2 = new BikeReservation(bike, appUser, fromBuilding, toBuilding2, fromTime, toTime);
    }

    @Test
    public void testAdd() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        assertEquals(bikeReservationService.all(), Collections.singletonList(bikeReservation));
    }

    @Test
    public void testUpdate() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding2.getId(), fromTimeMs, toTimeMs);
        List<BikeReservation> bikeReservations = new ArrayList<>();
        bikeReservationService.all().forEach(bikeReservations::add);
        assertEquals(2, bikeReservations.size());
        bikeReservation = bikeReservations.get(0);
        bikeReservation2 = bikeReservations.get(1);
        assertNotEquals(bikeReservationService.find(bikeReservation.getId()), bikeReservationService.find(bikeReservation2.getId()));
        bikeReservationService.update(bikeReservation2.getId(), "toBuilding", toBuilding.getId().toString());
        assertEquals(bikeReservationService.find(bikeReservation.getId()), bikeReservationService.find(bikeReservation2.getId()));
    }

    @Test
    public void testDelete() {
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding.getId(), fromTimeMs, toTimeMs);
        bikeReservationService.add(bike.getId(), appUser.getEmail(), fromBuilding.getId(), toBuilding2.getId(), fromTimeMs, toTimeMs);
        List<BikeReservation> bikeReservations = new ArrayList<>();
        bikeReservationService.all().forEach(bikeReservations::add);
        assertEquals(2, bikeReservations.size());
        bikeReservationService.delete(bikeReservations.get(0).getId());
        bikeReservations = new ArrayList<>();
        bikeReservationService.all().forEach(bikeReservations::add);
        assertEquals(1, bikeReservations.size());
    }
}
