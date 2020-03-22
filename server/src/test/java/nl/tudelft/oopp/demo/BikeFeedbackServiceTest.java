package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.repositories.*;
import nl.tudelft.oopp.demo.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the Role service.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BikeFeedbackServiceTest {
    @TestConfiguration
    static class BikeReservationServiceTestConfiguration {
        @Bean
        public BikeReservationService roomReservationService() {
            return new BikeReservationService();
        }
    }

    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingService();
        }
    }

    @TestConfiguration
    static class BikeServiceTestConfiguration {
        @Bean
        public BikeService bikeService() {
            return new BikeService();
        }
    }

    @TestConfiguration
    static class BikeFeedbackServiceTestConfiguration {
        @Bean
        public BikeFeedbackService bikeFeedbackService () {
            return new BikeFeedbackService();
        }
    }

    @Autowired
    BikeFeedbackService bikeFeedbackService;

    @Autowired
    BikeService bikeService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    BikeReservationService bikeReservationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BikeReservationRepository bikeReservationRepository;

    @Autowired
    BikeRepository bikeRepository;

    Building building;
    Building building2;
    Bike bike;
    Bike bike2;
    BikeReservation bikeReservation;
    BikeReservation bikeReservation2;
    AppUser appUser;
    AppUser appUser2;
    BikeFeedback bikeFeedback;
    BikeFeedback bikeFeedback2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        buildingService.add("EWI", "Mekelweg", 4);
        buildingService.add("EWI2", "Mekelweg2", 42);

        List<Building> buildings = new ArrayList<>(buildingService.all());
        building = buildings.get(0);
        building2 = buildings.get(1);

        bike = new Bike();
        bike.setLocation(building);
        bike.setAvailable(true);

        bike2 = new Bike();
        bike2.setAvailable(false);
        bike2.setLocation(building2);
        bikeRepository.save(bike);
        bikeRepository.save(bike2);

        appUser = new AppUser();
        appUser.setEmail("a.delia@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setFaculty("EEMCS");
        appUser.setName("Alto");
        appUser.setSurname("Delia");
        appUser.setRoles(new HashSet<>());
        appUser.setRoomReservations(new HashSet<>());
        appUser.setLoggedIn(false);
        userRepository.save(appUser);
        appUser = userRepository.findByEmail("a.delia@student.tudelft.nl");

        appUser2 = new AppUser();
        appUser2.setEmail("R.Jursevskis@student.tudelft.nl");
        userRepository.save(appUser2);

        bikeReservation = new BikeReservation();
        bikeReservation.setBike(bike);
        bikeReservation.setAppUser(appUser);
        bikeReservation.setFromBuilding(building);
        bikeReservation.setToBuilding(building2);
        bikeReservation.setFromTime(new Date(200));
        bikeReservation.setToTime(new Date(300));
        bikeReservationRepository.save(bikeReservation);

        bikeReservation2 = new BikeReservation();
        bikeReservation2.setBike(bike);
        bikeReservation2.setAppUser(appUser);
        bikeReservation2.setFromBuilding(building);
        bikeReservation2.setToBuilding(building2);
        bikeReservation2.setFromTime(new Date(200));
        bikeReservation2.setToTime(new Date(300));
        bikeReservationRepository.save(bikeReservation2);

        bikeFeedback = new BikeFeedback();
        bikeFeedback.setClient(appUser);
        bikeFeedback.setRecipient(appUser2);
        bikeFeedback.setBikeReservation(bikeReservation);
        bikeFeedback.setTime(new Date(300));
        bikeFeedback.setFeedback("good");
        bikeFeedback2 = new BikeFeedback();
        bikeFeedback2.setClient(appUser2);
        bikeFeedback2.setRecipient(appUser);
        bikeFeedback2.setBikeReservation(bikeReservation2);
        bikeFeedback2.setTime(new Date(500));
        bikeFeedback2.setFeedback("bad");

    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(bikeFeedbackService);
    }

    /**
     * Tests the saving and retrieval of an instance of RoomReservation.
     */
    @Test
    public void testCreate() {
        assertEquals(201, bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good"));
        //assertEquals(Collections.singletonList(roomFeedback), roomFeedbackService.all());
    }

    /**
     * Tests the creation of an instance with an invalid room reservation id.
     */
    @Test
    public void testCreateIllegalRoomReservation() {
        assertEquals(422, bikeFeedbackService.add(appUser.getEmail(),appUser2.getEmail(),-10,100, "nothing"));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    public void testCreateIllegalClient() {
        assertEquals(404, bikeFeedbackService.add("Wrong Client",appUser2.getEmail(),bikeReservation.getId(),300, "nothing"));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    public void testCreateIllegalRecipient() {
        assertEquals(404, bikeFeedbackService.add(appUser.getEmail(), "Wrong ",10,100, "nothing"));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(bikeReservationService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertNotNull(bikeFeedbackService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(421, bikeFeedbackService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertEquals(420, bikeFeedbackService.update(id, "nonexistent attribute", "random value"));
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeDate() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeReservationService.all().get(0).getId();
        assertNotEquals(50, bikeFeedbackService.all().get(0).getTime().getTime());
        bikeReservationService.update(id, "time", "50");
        assertEquals(300, bikeFeedbackService.all().get(0).getTime().getTime());
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeFeedback() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertNotEquals("bad", bikeFeedbackService.all().get(0).getFeedback());
        bikeFeedbackService.update(id, "feedback", "not so good");
        assertEquals("not so good", bikeFeedbackService.all().get(0).getFeedback());
    }

    /**
     * Tests the change of the room by using the service.
     */
    @Test
    public void testChangeRoomReservation() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertNotEquals(bikeReservation.getId() + 2, bikeFeedbackService.all().get(0).getBikeReservation());
        bikeReservationService.update(id, "bikereservationid", bikeReservation.getId().toString());
        assertEquals(bikeReservation.getId(), bikeFeedbackService.all().get(0).getBikeReservation().getId());
    }

//
//    /**
//     * Tests the addition of a RoomReservation to a Room.
//     */
//    @Test
//    public void testRoomFeedbackAddToRoomReservation() {
//        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good");
//        Set<RoomFeedback> roomFeedbacks = new HashSet<>();
//        roomFeedbacks.add(roomFeedbackService.all().get(0));
//        roomReservation.setRoomFeedback
//        assertEquals(roomFeedbacks, roomReservationService.feedback(roomService.all().get(0).getId()));
//    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        bikeFeedbackService.add(appUser2.getEmail(), appUser.getEmail(), bikeReservation.getId(), 900, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertEquals(2, bikeFeedbackService.all().size());
        List<BikeFeedback> bikeFeedbacks = new ArrayList<>();
        bikeFeedbacks.add(bikeFeedback);
        bikeFeedbacks.add(bikeFeedback2);
        //assertEquals(roomFeedbacks, roomFeedbackService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        bikeFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), bikeReservation.getId(), 300, "good");
        int id = bikeFeedbackService.all().get(0).getId();
        assertEquals(200, bikeFeedbackService.delete(id));
        //assertEquals(0, roomFeedbackService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(432, bikeFeedbackService.delete(0));
    }
}
