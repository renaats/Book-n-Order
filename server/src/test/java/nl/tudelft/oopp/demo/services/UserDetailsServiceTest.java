package nl.tudelft.oopp.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the UserDetails service implementation.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserDetailsServiceTest {
    private org.springframework.security.core.userdetails.UserDetails userDetails;
    Collection<GrantedAuthority> authorities;

    @TestConfiguration
    static class UserDetailsServiceTestConfiguration {
        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository) {
            return new UserDetailsServiceImpl(userRepository);
        }
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    AppUser appUser;
    Role role;
    Set<Role> roleSet;
    Set<AppUser> appUsers;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        appUser.setPassword("1234");
        role = new Role();
        appUsers = new HashSet<>();
        appUsers.add(appUser);
        role.setAppUsers(appUsers);
        role.setName("Manager");
        roleSet = new HashSet<>();
        roleSet.add(role);
        appUser.setRoles(roleSet);
    }

    /**
     * Tests the exception that is thrown when a user cannot be found.
     */
    @Test
    public void testLoadUserByUsernameException() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("not.a.student@student.tudelft.nl"));
    }

    /**
     * Tests the authorities that are assigned to a user.
     */
    @Test
    public void testLoadUserByUsername() {
        roleRepository.save(role);
        userRepository.save(appUser);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: appUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        assertEquals(new User("m.b.spasov@student.tudelft.nl", "1234", authorities),
                userDetailsService.loadUserByUsername("m.b.spasov@student.tudelft.nl"));
    }
}