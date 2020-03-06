package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RoleTest {
    @Autowired
    private RoleRepository roleRepository;

    Role role;
    Role role2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName("Tutor");
        role.setUsers(new HashSet<>());
        roleRepository.save(role);
    }

    /** Tests the constructor of the Role class
     */
    @Test
    public void testConstructor() {
        role2 = new Role();
        assertNotNull(role);
    }

    /** Tests the getters of the Role class
     */
    @Test
    public void testGetters() {
        role2 = roleRepository.findAll().get(0);
        Set<User> userSet = new HashSet<User>();
        assertEquals(role.getId(), role2.getId());
        assertEquals(role.getId(), 1);
        assertEquals(role.getName(), role2.getName());
        assertEquals(role2.getName(), "Tutor");
    }

    /** Tests the setters of the Role class
     */
    @Test
    public void testSetters() {
        Set<User> userSet = new HashSet<User>();
        role2 = new Role();
        role2.setName("Manager");
        role2.setUsers(userSet);
        assertEquals(role2.getName(), "Manager");
        assertEquals(role2.getUsers(), userSet);
    }

    /** Tests retrieving and saving data from the RoleRepository.
     */
    @Test
    public void saveAndRetrieveUser() {
        role2 = roleRepository.findAll().get(0);
        assertTrue(role.getName() == role2.getName());
    }

    /** Deletes everything from the repositories after testing.
     */
    @AfterEach
    public void cleanup() {
        roleRepository.deleteAll();
    }
}