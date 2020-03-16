package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests the Role entity.
 */
@DataJpaTest
class RoleTest {
    @Autowired
    private RoleRepository roleRepository;

    Role role;
    Role role2;

    /**
     * Sets up the entities and saves them in the repository before executing every test.
     */
    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName("ROLE_USER");
        role.setAppUsers(new HashSet<>());
        roleRepository.save(role);
    }

    /**
     * Tests the constructor of the Role class
     */
    @Test
    public void testConstructor() {
        role2 = new Role();
        assertNotNull(role);
    }

    /**
     * Tests the saving and retrieval of an instance of Role.
     */
    @Test
    public void saveAndRetrieveUser() {
        role2 = roleRepository.findAll().get(0);
        assertEquals(role.getName(), role2.getName());
    }

    /**
     * Tests the getters of the Role class
     */
    @Test
    public void testGetters() {
        role2 = roleRepository.findAll().get(0);
        Set<AppUser> userSet = new HashSet<>();
        assertEquals(role.getName(), role2.getName());
        assertEquals(userSet, role.getAppUsers());
    }

    /**
     * Tests the setters of the Role class
     */
    @Test
    public void testSetters() {
        AppUser user = new AppUser();
        Set<AppUser> userSet = new HashSet<>();
        userSet.add(user);
        role2 = new Role();
        role2.setName("ROLE_ADMIN");
        role2.setAppUsers(userSet);
        assertEquals(role2.getName(), "ROLE_ADMIN");
        assertEquals(role2.getAppUsers(), userSet);
    }

    /**
     * Tests roles addition to a user.
     */
    @Test
    public void testUserRole() {
        AppUser appUser = new AppUser();
        appUser.setEmail("R.Jursevskis@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Renats");
        appUser.setSurname("Jursevskis");
        appUser.setFaculty("EWI");
        appUser.setRoomReservations(new HashSet<>());

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        appUser.setRoles(new HashSet<>());
        appUser.addRole(role);
        assertEquals(roleSet, appUser.getRoles());
    }

    /**
     * Cleans up the repositories after executing every test.
     */
    @AfterEach
    public void cleanup() {
        roleRepository.deleteAll();
    }
}