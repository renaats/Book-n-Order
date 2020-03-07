package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.services.BikeService;
import nl.tudelft.oopp.demo.services.BikeServiceImpl;
import nl.tudelft.oopp.demo.services.RoleService;
import nl.tudelft.oopp.demo.services.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleServiceTest {
    @TestConfiguration
    static class RoleServiceTestConfiguration {
        @Bean
        public RoleService roleService() {
            return new RoleServiceImpl();
        }
    }

    @Autowired
    RoleService roleService;


}