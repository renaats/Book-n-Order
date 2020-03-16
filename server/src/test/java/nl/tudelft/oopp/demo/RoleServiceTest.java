package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
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

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName("Manager");

        role2 = new Role();
        role.setName("Tutor");
    }

    @Test
    public void testConstructor() {
        assertNotNull(roleService);
    }

    @Test
    public void testCreate() {
        roleService.add(role.getName());
        assertEquals(role.getName(), roleService.all().iterator().next().getName());
    }

    @Test
    public void testAll() {
        Iterator<Role> iterator = roleService.all().iterator();
        assertFalse(iterator.hasNext());
        roleService.add(role.getName());
        iterator = roleService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        roleService.add(role.getName());
        assertEquals(roleService.all().iterator().next(), roleService.find(roleService.all().iterator().next().getId()));
        assertNull(roleService.find(-3));
    }

    @Test
    public void testUpdate() {
        roleService.add(role.getName());
        roleService.add(role2.getName());
        List<Role> roles = new ArrayList<Role>();
        roleService.all().forEach(roles::add);
        assertEquals(2, roles.size());
        role = roles.get(0);
        role2 = roles.get(1);

        assertNotEquals(roleService.find(role.getId()).getName(), roleService.find(role2.getId()).getName());
        roleService.update(role2.getId(), role.getName());
        assertEquals(roleService.find(role2.getId()).getName(), roleService.find(role.getId()).getName());
    }

    @Test
    public void testDelete() {
        roleService.add(role.getName());
        roleService.add(role2.getName());
        List<Role> roles = new ArrayList<>();
        roleService.all().forEach(roles::add);
        assertEquals(2, roles.size());
        role = roles.get(0);
        role2 = roles.get(1);
        roleService.delete(roles.get(0).getId());
        roles = new ArrayList<>();
        roleService.all().forEach(roles::add);
        assertEquals(1, roles.size());
        assertNull(roleService.find(role.getId()));
        assertFalse(roles.contains(role));
        assertNotNull(roleService.find(role2.getId()));
        assertTrue(roles.contains(role2));
    }
}