package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.services.RoleService;
import nl.tudelft.oopp.demo.services.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleServiceTest {
    @TestConfiguration
    static class RoleServiceTestConfiguration {
        @Bean
        public RoleService roleService() {
            return new RoleServiceImpl();
        }
    }

    @Autowired
    RoleService roleService;

    Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName("Manager");
    }

    @Test
    public void testConstructor() {
        assertNotNull(roleService);
    }

    @Test
    public void testCreate() {
        roleService.add(role.getName());
        assertEquals(Arrays.asList(role), roleService.all());
    }
}