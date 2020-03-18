package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.services.RoleService;
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
        role.setName("ROLE_USER");

        role2 = new Role();
        role2.setName("ROLE_ADMIN");
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
        assertEquals(201, roleService.add(role.getName()));
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
        assertEquals(416, roleService.update(0, "a"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        roleService.add(role.getName());
        int id = roleService.all().get(0).getId();
        assertNotEquals("ROLE_ADMIN", roleService.find(id).getName());
        roleService.update(id, "ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", roleService.find(id).getName());
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
        assertEquals(200, roleService.delete(id));
        assertEquals(0, roleService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(416, roleService.delete(0));
    }
}