package nl.tudelft.oopp.demo.services;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ALREADY_RESERVED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESERVATION_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.ROOM_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.USER_NOT_FOUND;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.auth0.jwt.JWT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Tests the RoomReservation service.
 */
@DataJpaTest
class RoomReservationServiceTest {
    @TestConfiguration
    static class RoomReservationServiceTestConfiguration {
        @Bean
        public RoomReservationService roomReservationService() {
            return new RoomReservationService();
        }
    }

    @TestConfiguration
    static class RoomServiceTestConfiguration {
        @Bean
        public RoomService roomService() {
            return new RoomService();
        }
    }

    @Autowired
    RoomReservationService roomReservationService;

    @Autowired
    RoomService roomService;

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

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        room = new Room();
        room.setName("Ampere");
        room.setFaculty("EEMCS");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setPlugs(250);
        room.setCapacity(300);
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
        roomReservation.setFromTime(new Date(300));
        roomReservation.setToTime(new Date(500));

        roomReservation2 = new RoomReservation();
        roomReservation2.setAppUser(appUser2);
        roomReservation2.setRoom(room2);
        roomReservation2.setFromTime(new Date(300000000000000L));
        roomReservation2.setToTime(new Date(500000000000000L));
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(roomReservationService);
    }

    /**
     * Tests the saving and retrieval of an instance of RoomReservation.
     */
    @Test
    public void testCreate() {
        assertEquals(ADDED, roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500));
        assertEquals(Collections.singletonList(roomReservation), roomReservationService.all());
    }

    /**
     * Tests the creation of an instance with an invalid room id.
     */
    @Test
    public void testCreateIllegalRoom() {
        assertEquals(ID_NOT_FOUND, roomReservationService.add(-3,"This is wrong room Id.",50,100));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    public void testCreateIllegalUser() {
        assertEquals(NOT_FOUND, roomReservationService.add(room.getId(),"NotARealEmail@tudelft.nl",50,100));
    }

    /**
     * Tests the creation of an instance with the end time before the start time.
     */
    @Test
    public void testCreateIllegalTime() {
        assertEquals(ALREADY_RESERVED, roomReservationService.add(room.getId(), appUser.getEmail(), 300, 280));
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
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertNotNull(roomReservationService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(RESERVATION_NOT_FOUND, roomReservationService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, roomReservationService.update(id, "nonexistent attribute", "random value"));
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeFromDate() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertNotEquals(50, roomReservationService.all().get(0).getFromTime().getTime());
        roomReservationService.update(id, "fromdate", "50");
        assertEquals(50, roomReservationService.all().get(0).getFromTime().getTime());
    }

    /**
     * Tests the change of the from date by using the service.
     */
    @Test
    public void testChangeToDate() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertNotEquals(100, roomReservationService.all().get(0).getToTime().getTime());
        roomReservationService.update(id, "todate", "100");
        assertEquals(100, roomReservationService.all().get(0).getToTime().getTime());
    }

    /**
     * Tests the change of the room by using the service.
     */
    @Test
    public void testChangeRoom() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertNotEquals(room2, roomReservationService.all().get(0).getRoom());
        roomReservationService.update(id, "roomid", room2.getId().toString());
        assertEquals(room2, roomReservationService.all().get(0).getRoom());
    }

    /**
     * Tests the change of the room to a non-existing room by using the service.
     */
    @Test
    public void testChangeRoomNonExisting() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertEquals(ROOM_NOT_FOUND, roomReservationService.update(id, "roomid", "-3"));
    }

    /**
     * Tests the change of the user by using the service.
     */
    @Test
    public void testChangeUser() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertNotEquals(appUser2, roomReservationService.all().get(0).getAppUser());
        roomReservationService.update(id, "useremail", appUser2.getEmail());
        assertEquals(appUser2, roomReservationService.all().get(0).getAppUser());
    }

    /**
     * Tests the change of the user to a non-existing user by using the service.
     */
    @Test
    public void testChangeUserNonExisting() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertEquals(USER_NOT_FOUND, roomReservationService.update(id, "useremail", "non.existent.email@student.tudelft.nl"));
    }

    /**
     * Tests the addition of a RoomReservation to a Room.
     */
    @Test
    public void testRoomReservationAddToRoom() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        Set<RoomReservation> roomReservations = new HashSet<>();
        roomReservations.add(roomReservationService.all().get(0));
        room.setRoomReservations(roomReservations);
        assertEquals(roomReservations, roomService.reservations(roomService.all().get(0).getId()));
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 300000000000000L, 500000000000000L);
        assertEquals(2, roomReservationService.all().size());
        List<RoomReservation> roomReservations = new ArrayList<>();
        roomReservations.add(roomReservation);
        roomReservations.add(roomReservation2);
        assertEquals(roomReservations, roomReservationService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        int id = roomReservationService.all().get(0).getId();
        assertEquals(EXECUTED, roomReservationService.delete(id));
        assertEquals(0, roomReservationService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(RESERVATION_NOT_FOUND, roomReservationService.delete(0));
    }

    /**
     * Tests the retrieval of past room reservations for the user that sends the request.
     */
    @Test
    public void testGetPastRoomReservations() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals(Collections.singletonList(roomReservation), roomReservationService.past(request));
    }

    /**
     * Tests the retrieval of past room reservations for a non-existent user.
     */
    @Test
    public void testGetNonExistentPastRoomReservations() {
        roomReservationService.add(room.getId(), appUser.getEmail(), 300, 500);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), roomReservationService.past(request));
    }

    /**
     * Tests the retrieval of future room reservations for the user that sends the request.
     */
    @Test
    public void testGetFutureRoomReservations() {
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 300000000000000L, 500000000000000L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser2.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals(Collections.singletonList(roomReservation2), roomReservationService.future(request));
    }

    /**
     * Tests the retrieval of future room reservations for a non-existent user.
     */
    @Test
    public void testGetNonExistentFutureRoomReservations() {
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 300000000000000L, 500000000000000L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), roomReservationService.future(request));
    }

    @Test
    public void testGetReservationsForRoomWithReservation() {
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 1500, 5000);
        int id = roomReservationService.all().get(0).getId();
        RoomReservation r = roomReservationService.find(id);

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("appUser") || f.getAnnotation(JsonIgnore.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> c) {
                return c == AppUser.class;
            }
        });

        Gson gson = gsonBuilder.create();
        String test = gson.toJson(r);
        assertEquals("[" + test + "]", roomReservationService.forRoom(room2.getId()));
    }

    @Test
    public void testGetReservationsForNonExistentRoom() {
        roomReservationService.add(room2.getId(), appUser2.getEmail(), 1500, 5000);
        assertNull(roomReservationService.forRoom(-1));
    }

    @Test
    public void testGetReservationsForRoomWithoutReservation() {
        assertEquals("[]", roomReservationService.forRoom(room2.getId()));
    }
}