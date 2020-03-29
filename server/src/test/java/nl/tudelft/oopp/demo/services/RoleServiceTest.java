package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.USER;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the Role service.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleServiceTest {
    @TestConfiguration
    static class RoleServiceTestConfiguration {
        @Bean
        public RoleService roleService() {
            return new RoleService();
        }
    }

    @Autowired
    RoleService roleService;

    Role role;
    Role role2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName(USER);

        role2 = new Role();
        role2.setName(ADMIN);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(roleService);
    }

    /**
     * Tests the saving and retrieval of an instance of Role.
     */
    @Test
    public void testCreate() {
        assertEquals(ADDED, roleService.add(role.getName()));
        assertEquals(Collections.singletonList(role), roleService.all());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(roleService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        roleService.add(role.getName());
        int id = roleService.all().get(0).getId();
        assertNotNull(roleService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(ID_NOT_FOUND, roleService.update(0, "a"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        roleService.add(role.getName());
        int id = roleService.all().get(0).getId();
        assertNotEquals(ADMIN, roleService.find(id).getName());
        roleService.update(id, ADMIN);
        assertEquals(ADMIN, roleService.find(id).getName());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        roleService.add(role.getName());
        roleService.add(role2.getName());
        assertEquals(2, roleService.all().size());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role2);
        assertEquals(roles, roleService.all());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        roleService.add(role.getName());
        int id = roleService.all().get(0).getId();
        assertEquals(EXECUTED, roleService.delete(id));
        assertEquals(0, roleService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(ID_NOT_FOUND, roleService.delete(0));
    }
}