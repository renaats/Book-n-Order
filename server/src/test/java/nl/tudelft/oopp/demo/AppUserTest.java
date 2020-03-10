package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AppUserTest {
    @Autowired
    private UserRepository userRepository;

    AppUser appUser;
    AppUser appUser2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setEmail("R.Jursevskis@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Renats");
        appUser.setSurname("Jursevskis");
        appUser.setFaculty("EWI");
        appUser.setRoomReservations(new HashSet<>());
        userRepository.save(appUser);
    }

    /** Tests the constructor of the User class
     */
    @Test
    public void testConstructor() {
        appUser2 = new AppUser();
        assertNotNull(appUser2);
    }

    @Test
    public void saveAndRetrieveUser() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals(appUser, appUser2);
    }

    @Test
    public void testSetters() {
        appUser2 = new AppUser();
        appUser2.setEmail("m.b.spasov@student.tudelft.nl");
        appUser2.setPassword("1234");
        appUser2.setName("Mihail");
        appUser2.setSurname("Spasov");
        appUser2.setFaculty("EEMCS");
        Set<Role> roleSet = new HashSet<Role>();
        appUser2.setRoles(roleSet);
        Set<RoomReservation> roomReservationSet = new HashSet<RoomReservation>();
        appUser2.setRoomReservations(roomReservationSet);
        assertEquals(appUser2.getEmail(), "m.b.spasov@student.tudelft.nl");
        assertEquals(appUser2.getPassword(), "1234");
        assertEquals(appUser2.getName(), "Mihail");
        assertEquals(appUser2.getSurname(), "Spasov");
        assertEquals(appUser2.getFaculty(), "EEMCS");
        assertEquals(appUser2.getRoles(), roleSet);
        assertEquals(appUser2.getRoomReservations(), roomReservationSet);
    }


    @Test
    public void testGetters() {
        appUser2 = userRepository.findAll().get(0);
        assertEquals(appUser.getEmail(), appUser2.getEmail());
        assertEquals(appUser.getPassword(), appUser2.getPassword());
        assertEquals(appUser.getName(), appUser2.getName());
        assertEquals(appUser.getSurname(), appUser2.getSurname());
        assertEquals(appUser.getFaculty(), appUser2.getFaculty());
    }

    @Test
    public void testEqualUsers() {
        appUser2 = new AppUser();
        appUser2.setEmail("R.Jursevskis@student.tudelft.nl");
        appUser2.setPassword("1234");
        appUser2.setName("Renats");
        appUser2.setSurname("Jursevskis");
        appUser2.setFaculty("EWI");
        assertEquals(appUser, appUser2);
        assertNotSame(appUser, appUser2);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }
}