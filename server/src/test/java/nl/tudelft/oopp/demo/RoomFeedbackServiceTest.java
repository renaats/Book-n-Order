package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
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
public class RoomFeedbackServiceTest {
    @TestConfiguration
    static class RoomReservationServiceTestConfiguration {
        @Bean
        public RoomReservationService roomReservationService() {
            return new RoomReservationService();
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
    static class RoomServiceTestConfiguration {
        @Bean
        public RoomService roomService() {
            return new RoomService();
        }
    }

    @TestConfiguration
    static class RoomFeedbackServiceTestConfiguration {
        @Bean
        public RoomFeedbackService roomFeedbackService () {
            return new RoomFeedbackService();
        }
    }

    @Autowired
    RoomFeedbackService roomFeedbackService;

    @Autowired
    RoomService roomService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    RoomReservationService roomReservationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomReservationRepository roomReservationRepository;

    @Autowired
    RoomRepository roomRepository;

    Building building;
    Building building2;
    Room room;
    Room room2;
    RoomReservation roomReservation;
    RoomReservation roomReservation2;
    AppUser appUser;
    AppUser appUser2;
    RoomFeedback roomFeedback;
    RoomFeedback roomFeedback2;

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

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setNrPeople(200);
        room.setPlugs(200);
        roomRepository.save(room);

        room2 = new Room();
        room2.setName("Boole");
        room2.setBuilding(building2);
        room2.setFaculty("EWI2");
        room2.setFacultySpecific(false);
        room2.setScreen(true);
        room2.setProjector(true);
        room2.setNrPeople(200);
        room2.setPlugs(200);
        roomRepository.save(room2);

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

        roomReservation = new RoomReservation();
        roomReservation.setRoom(room);
        roomReservation.setAppUser(appUser);
        roomReservation.setFromTime(new Date(300));
        roomReservation.setToTime(new Date(500));
        roomReservationRepository.save(roomReservation);
        roomReservation = roomReservationRepository.findAll().get(0);
        roomReservation2 = new RoomReservation();
        roomReservation2.setAppUser(appUser2);
        roomReservation2.setRoom(room2);
        roomReservation2.setFromTime(new Date(200));
        roomReservation2.setToTime(new Date(300));

        roomFeedback = new RoomFeedback();
        roomFeedback.setClient(appUser);
        roomFeedback.setRecipient(appUser2);
        roomFeedback.setRoomReservation(roomReservation);
        roomFeedback.setTime(new Date(300));
        roomFeedback.setFeedback("good");
        roomFeedback2 = new RoomFeedback();
        roomFeedback2.setClient(appUser2);
        roomFeedback2.setRecipient(appUser);
        roomFeedback2.setRoomReservation(roomReservation2);
        roomFeedback2.setTime(new Date(500));
        roomFeedback2.setFeedback("bad");
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(roomFeedbackService);
    }

    /**
     * Tests the saving and retrieval of an instance of RoomReservation.
     */
    @Test
    public void testCreate() {
        assertEquals(201, roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good"));
        assertEquals(Collections.singletonList(roomFeedback), roomFeedbackService.all());
    }

    /**
     * Tests the creation of an instance with an invalid room reservation id.
     */
    @Test
    public void testCreateIllegalRoom() {
        assertEquals(416, roomFeedbackService.add("Wrong","This is wrong room Id",-10,100, "nothing"));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    public void testCreateIllegalClient() {
        assertEquals(404, roomFeedbackService.add("Wrong Client",appUser2.getEmail(),roomReservation.getId(),300, "nothing"));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    public void testCreateIllegalRecipient() {
        assertEquals(404, roomFeedbackService.add(appUser.getEmail(), "Wrong ",10,100, "nothing"));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(roomReservationService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), 1,300, "good");
        int id = roomFeedbackService.all().get(0).getId();
        assertNotNull(roomFeedbackService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(421, roomFeedbackService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good");
        int id = roomFeedbackService.all().get(0).getId();
        assertEquals(420, roomFeedbackService.update(id, "nonexistent attribute", "random value"));
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeDate() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good");
        int id = roomReservationService.all().get(0).getId();
        assertNotEquals(50, roomFeedbackService.all().get(0).getTime().getTime());
        roomReservationService.update(id, "time", "50");
        assertEquals(50, roomFeedbackService.all().get(0).getTime().getTime());
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeFeedback() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good");
        int id = roomFeedbackService.all().get(0).getId();
        assertNotEquals(100, roomFeedbackService.all().get(0).getFeedback());
        roomFeedbackService.update(id, "feedback", "not so good");
        assertEquals(100, roomFeedbackService.all().get(0).getFeedback());
    }

    /**
     * Tests the change of the room by using the service.
     */
    @Test
    public void testChangeRoomReservation() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), roomReservation.getId(), 300, "good");
        int id = roomFeedbackService.all().get(0).getId();
        assertNotEquals(roomReservation, roomFeedbackService.all().get(0).getRoomReservation());
        roomReservationService.update(id, "roomreservationid", roomReservation.getId().toString());
        assertEquals(roomReservation, roomFeedbackService.all().get(0).getRoomReservation());
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
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), 1,300, "good");
        roomFeedbackService.add(appUser2.getEmail(), appUser.getEmail(), 2,400, "not good");
        assertEquals(2, roomFeedbackService.all().size());
        List<RoomFeedback> roomFeedbacks = new ArrayList<>();
        roomFeedbacks.add(roomFeedback);
        roomFeedbacks.add(roomFeedback2);
        assertEquals(roomFeedbacks, roomFeedbackService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        roomFeedbackService.add(appUser.getEmail(), appUser2.getEmail(), 1,300, "good");
        int id = roomFeedbackService.all().get(0).getId();
        assertEquals(200, roomFeedbackService.delete(id));
        assertEquals(0, roomFeedbackService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(421, roomFeedbackService.delete(0));
    }
}
