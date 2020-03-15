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
        appUser = new AppUser();
        appUser.setEmail("l.j.jongejans@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Liselotte");
        appUser.setSurname("Jongejans");
        appUser.setFaculty("EWI");
        appUser.setBikeReservations(new HashSet<>());
        userRepository.save(appUser);

        fromBuilding = new Building();
        fromBuilding.setName("Sporthal");
        fromBuilding.setStreet("Mekelweg");
        fromBuilding.setHouseNumber(8);
        buildingRepository.save(fromBuilding);

        toBuilding = new Building();
        toBuilding.setName("EWI");
        toBuilding.setStreet("Mekelweg");
        toBuilding.setHouseNumber(4);
        buildingRepository.save(toBuilding);

        toBuilding2 = new Building();
        toBuilding2.setName("Library");
        toBuilding2.setStreet("Prometheusplein");
        toBuilding2.setHouseNumber(1);
        buildingRepository.save(toBuilding2);

        bike = new Bike();
        bike.setLocation(fromBuilding);
        bike.setAvailable(true);
        bikeRepository.save(bike);

        fromTime = new Date(10000000000L);
        fromTimeMs = fromTime.getTime();

        toTime = new Date(11000000000L);
        toTimeMs = toTime.getTime();

        bikeReservation = new BikeReservation();
        bikeReservation.setAppUser(appUser);
        bikeReservation.setFromBuilding(fromBuilding);
        bikeReservation.setToBuilding(toBuilding);
        bikeReservation.setBike(bike);
        bikeReservation.setFromTime(fromTime);
        bikeReservation.setToTime(toTime);

        bikeReservation2 = new BikeReservation();
        bikeReservation2.setAppUser(appUser);
        bikeReservation2.setFromBuilding(fromBuilding);
        bikeReservation2.setToBuilding(toBuilding2);
        bikeReservation2.setBike(bike);
        bikeReservation2.setFromTime(fromTime);
        bikeReservation2.setToTime(toTime);
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
