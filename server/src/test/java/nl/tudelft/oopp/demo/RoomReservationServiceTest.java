package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.RoomReservationService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoomReservationServiceTest {
    @TestConfiguration
    static class RoomReservationServiceTestConfiguration {
        @Bean
        public RoomReservationService roomReservationService() {
            return new RoomReservationService();
        }
    }

    @Autowired
    RoomReservationService roomReservationService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    Room room;
    Room room2;
    AppUser appUser;
    AppUser appUser2;
    RoomReservation roomReservation;
    RoomReservation roomReservation2;
    Set<RoomReservation> roomReservationSet;

    @BeforeEach
    public void setup() {
        room = new Room();
        room.setName("Ampere");
        room.setFaculty("EEMCS");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        roomReservation2 = new RoomReservation();
        roomReservation2.setFromTime(new Date(200));
        roomReservation2.setToTime(new Date(300));
        roomReservationSet = new HashSet<>();
        roomReservationSet.add(roomReservation2);
        room.setRoomReservations(roomReservationSet);
        room.setPlugs(250);
        room.setNrPeople(300);
        roomRepository.save(room);

        room2 = new Room();
        room2.setName("Boole");
        room2.setRoomReservations(new HashSet<>());
        roomRepository.save(room2);

        appUser = new AppUser();
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setFaculty("EEMCS");
        appUser.setName("Mihail");
        appUser.setSurname("Spasov");
        appUser.setRoles(new HashSet<>());
        appUser.setRoomReservations(new HashSet<>());
        appUser.setLoggedIn(false);
        userRepository.save(appUser);

        appUser2 = new AppUser();
        appUser2.setEmail("R.Jursevskis@student.tudelft.nl");
        userRepository.save(appUser2);

        roomReservation = new RoomReservation();
        roomReservation.setRoom(room);
        roomReservation.setAppUser(appUser);
        roomReservation.setFromTime(new Date(100));
        roomReservation.setToTime(new Date(110));
    }

    @Test
    public void testConstructor() {
        assertNotNull(roomReservationService);
    }

    @Test
    public void testCreate() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 100, 110);
        assertEquals(Arrays.asList(roomReservation), roomReservationService.all());
        assertEquals(416, roomReservationService.add(-3,"This is wrong room Id.",50,100));
        assertEquals(404, roomReservationService.add(room.getId(),"NotARealEmail@tudelft.nl",50,100));
        assertEquals(308, roomReservationService.add(room.getId(), appUser.getEmail(), 260, 280));
    }

    @Test
    public void testAll() {
        Iterator<RoomReservation> iterator = roomReservationService.all().iterator();
        assertFalse(iterator.hasNext());
        roomReservationService.add(room.getId(), appUser.getEmail(), 50,100);
        iterator = roomReservationService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUpdate() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 50,100);
        roomReservationService.add(room2.getId(), appUser2.getEmail(),20, 70);
        List<RoomReservation> roomReservations = new ArrayList<>();
        roomReservationService.all().forEach(roomReservations::add);
        assertEquals(2, roomReservations.size());
        roomReservation = roomReservations.get(0);
        roomReservation2 = roomReservations.get(1);
        Iterator<RoomReservation> iterator = roomReservationService.all().iterator();

        assertEquals(420, roomReservationService.update(roomReservation.getId(), "nonexistent attribute", "random value"));
        assertNotEquals(iterator.next(), iterator.next());
        assertEquals(418, roomReservationService.update(roomReservation2.getId(), "roomId", "-3"));
        roomReservationService.update(roomReservation2.getId(), "roomId", roomReservation.getRoom().getId().toString());
        iterator = roomReservationService.all().iterator();
        assertNotEquals(iterator.next(), iterator.next());
        assertEquals(419, roomReservationService.update(roomReservation2.getId(), "userEmail", "non.existent.email@student.tudelft.nl"));
        roomReservationService.update(roomReservation2.getId(), "userEmail", roomReservation.getAppUser().getEmail());
        iterator = roomReservationService.all().iterator();
        assertNotEquals(iterator.next(), iterator.next());
        roomReservationService.update(roomReservation2.getId(), "fromDate", "50");
        roomReservationService.update(roomReservation2.getId(), "toDate", "100");
        iterator = roomReservationService.all().iterator();
        assertEquals(iterator.next(), iterator.next());
    }

    @Test
    public void testDelete() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 50 ,100);
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 70, 90);
        List<RoomReservation> roomReservations = new ArrayList<>();
        roomReservationService.all().forEach(roomReservations::add);
        assertEquals(2, roomReservations.size());
        roomReservation = roomReservations.get(0);
        roomReservation2 = roomReservations.get(1);
        roomReservationService.delete(roomReservations.get(0).getId());
        roomReservations = new ArrayList<>();
        roomReservationService.all().forEach(roomReservations::add);
        assertEquals(1, roomReservations.size());
        assertFalse(roomReservations.contains(roomReservation));
        assertTrue(roomReservations.contains(roomReservation2));
    }

    @AfterEach
    public void cleanup() {
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }
}