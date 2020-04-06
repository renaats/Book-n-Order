package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the AppUser entity.
 */
@DataJpaTest
public class AppUserTest {
    @Autowired
    private UserRepository userRepository;

    AppUser appUser;
    AppUser appUser2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser("R.Jursevskis@student.tudelft.nl", "1234", "Renats", "Jursevskis", "EWI", "CSE");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.save(appUser);
    }

    /**
     * Tests the constructor of the AppUser class
     */
    @Test
    public void testConstructor() {
        appUser2 = new AppUser();
        assertNotNull(appUser2);
    }

    /**
     * Tests the saving and retrieval of an instance of AppUser.
     */
    @Test
    public void testSaveAndRetrieveUser() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals(appUser, appUser2);
    }

    /**
     * Tests the getter for the email field.
     */
    @Test
    public void testEmailGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals("R.Jursevskis@student.tudelft.nl", appUser2.getEmail());
    }

    /**
     * Tests the getter for the password field.
     */
    @Test
    public void testPasswordGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals("1234", appUser2.getPassword());
    }

    /**
     * Tests the getter for the name field.
     */
    @Test
    public void testNameGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals("Renats", appUser2.getName());
    }

    /**
     * Tests the getter for the surname field.
     */
    @Test
    public void testSurnameGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals("Jursevskis", appUser2.getSurname());
    }

    /**
     * Tests the getter for the faculty field.
     */
    @Test
    public void testFacultyGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals("EWI", appUser2.getFaculty());
    }

    /**
     * Tests the getter for the confirmationNumber field.
     */
    @Test
    public void testConfirmationNumberGetter() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals(0, appUser2.getConfirmationNumber());
    }

    /**
     * Tests the change of the confirmationNumber by using a setter.
     */
    @Test
    public void testChangeConfirmationNumber() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals(0, appUser2.getConfirmationNumber());
        appUser2.setConfirmationNumber(123456);
        assertEquals(123456, appUser2.getConfirmationNumber());
    }

    /**
     * Tests the the change of the email by using a setter.
     */
    @Test
    public void testChangeEmail() {
        assertNotEquals(appUser.getEmail(), "m.b.spasov@student.tudelft.nl");
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        assertEquals(appUser.getEmail(), "m.b.spasov@student.tudelft.nl");
    }

    /**
     * Tests the the change of the password by using a setter.
     */
    @Test
    public void testChangePassword() {
        assertNotEquals(appUser.getPassword(), "12345");
        appUser.setPassword("12345");
        assertEquals(appUser.getPassword(), "12345");
    }

    /**
     * Tests the the change of the name by using a setter.
     */
    @Test
    public void testChangeName() {
        assertNotEquals(appUser.getName(), "Mihail");
        appUser.setName("Mihail");
        assertEquals(appUser.getName(), "Mihail");
    }

    /**
     * Tests the the change of the surname by using a setter.
     */
    @Test
    public void testChangeSurname() {
        assertNotEquals(appUser.getSurname(), "Spasov");
        appUser.setSurname("Spasov");
        assertEquals(appUser.getSurname(), "Spasov");
    }

    /**
     * Tests the the change of the faculty by using a setter.
     */
    @Test
    public void testChangeFaculty() {
        assertNotEquals(appUser.getFaculty(), "EEMCS");
        appUser.setFaculty("EEMCS");
        assertEquals(appUser.getFaculty(), "EEMCS");
    }

    /**
     * Tests the loggedIn getter and setter.
     */
    @Test
    public void testLoggedIn() {
        appUser = userRepository.findAll().get(0);
        assertFalse(appUser.isLoggedIn());
        appUser.setLoggedIn(true);
        assertTrue(appUser.isLoggedIn());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }
}