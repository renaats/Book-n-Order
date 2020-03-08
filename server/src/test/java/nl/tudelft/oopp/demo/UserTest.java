package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@DataJpaTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    User user;
    User user2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("R.Jursevskis@student.tudelft.nl");
        user.setPassword("1234");
        user.setName("Renats");
        user.setSurname("Jursevskis");
        user.setFaculty("EWI");
        user.setRoomReservations(new HashSet<>());
        userRepository.save(user);
    }

    /** Tests the constructor of the User class
     */
    @Test
    public void testConstructor() {
        user2 = new User();
        assertNotNull(user2);
    }

    /** Tests the getters of the User class
     */
    @Test
    public void testGetters() {
        user2 = userRepository.findAll().get(0);
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getSurname(), user2.getSurname());
        assertEquals(user.getFaculty(), user2.getFaculty());
    }

    /** Tests the setters of the User class
     */
    @Test
    public void testSetters() {
        user2 = new User();
        user2.setEmail("m.b.spasov@student.tudelft.nl");
        user2.setPassword("1234");
        user2.setName("Mihail");
        user2.setSurname("Spasov");
        user2.setFaculty("EEMCS");
        Set<Role> roleSet = new HashSet<Role>();
        user2.setRoles(roleSet);
        Set<RoomReservation> roomReservationSet = new HashSet<RoomReservation>();
        user2.setRoomReservations(roomReservationSet);
        assertEquals(user2.getEmail(), "m.b.spasov@student.tudelft.nl");
        assertEquals(user2.getPassword(), "1234");
        assertEquals(user2.getName(), "Mihail");
        assertEquals(user2.getSurname(), "Spasov");
        assertEquals(user2.getFaculty(), "EEMCS");
        assertEquals(user2.getRoles(), roleSet);
        assertEquals(user2.getRoomReservations(), roomReservationSet);
    }

    /** Tests retrieving and saving data from the UserRepository.
     */
    @Test
    public void saveAndRetrieveUser() {
        user2 = userRepository.findAll().get(0);
        assertEquals(user, user2);
    }

    /** Tests whether two objects are of instance User and whether they are equal.
     */
    @Test
    public void testEqualUsers() {
        user2 = new User();
        user2.setEmail("R.Jursevskis@student.tudelft.nl");
        assertNotEquals(user, user2);
        user2.setPassword("1234");
        user2.setName("Renats");
        assertNotEquals(user, user2);
        user2.setSurname("Jursevskis");
        user2.setFaculty("EWI");
        assertEquals(user, user2);
        assertNotSame(user, user2);
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }
}