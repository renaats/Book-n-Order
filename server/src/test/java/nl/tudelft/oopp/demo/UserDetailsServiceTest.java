package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    AppUser appUser;
    Role role;
    Set<Role> roleSet;
    Set<AppUser> appUsers;

    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        role = new Role();
        appUsers = new HashSet<>();
        appUsers.add(appUser);
        role.setAppUsers(appUsers);
        role.setName("Manager");
        roleSet = new HashSet<>();
        roleSet.add(role);
        appUser.setRoles(roleSet);
//        userRepository.save(appUser);
    }

    @Test
    public void testLoadUserByUsername() {
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("not.a.student@student.tudelft.nl");
        });
      //  userDetails = userDetailsService.loadUserByUsername(appUser.getEmail());
//        authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role.getName()));
   //     assertEquals(userDetails.getAuthorities(), authorities);

    }
}