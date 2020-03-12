package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    @Test
    public void saveAndRetrieveUser() {
        user2 = userRepository.findAll().get(0);
        assertEquals(user, user2);
    }

    @Test
    public void testGetters() {
        user2 = userRepository.findAll().get(0);
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getSurname(), user2.getSurname());
        assertEquals(user.getFaculty(), user2.getFaculty());
    }

    @Test
    public void testEqualUsers() {
        user2 = new User();
        user2.setEmail("R.Jursevskis@student.tudelft.nl");
        user2.setPassword("1234");
        user2.setName("Renats");
        user2.setSurname("Jursevskis");
        user2.setFaculty("EWI");
        assertEquals(user, user2);
        assertNotSame(user, user2);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }
}