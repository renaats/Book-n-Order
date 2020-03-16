package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserServiceTest {
    @TestConfiguration
    static class UserServiceTestConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    UserService userService;

    AppUser appUser;
    AppUser appUser2;
    Role role;
    Set<Role> roleSet;

    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Mihail");
        appUser.setSurname("Spasov");
        appUser.setFaculty("EEMCS");
        role = new Role();
        role.setName("ROLE_USER");
        roleSet = new HashSet<>();
        roleSet.add(role);
        appUser.setRoles(roleSet);

        appUser2 = new AppUser();
        appUser2.setEmail("another.student@student.tudelft.nl");
        appUser2.setPassword("1111");
        appUser2.setName("Name");
        appUser2.setSurname("Surname");
        appUser2.setFaculty("IO");
        appUser2.setRoles(roleSet);
    }

    @Test
    public void testConstructor() {
        assertNotNull(userService);
    }

    @Test
    public void testCreate() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        assertEquals(userService.all().iterator().next().getEmail(), appUser.getEmail());
        assertEquals(423,userService.add("notavalidemail", "1111", "name","surname","faculty"));
        assertEquals(310,userService.add(appUser.getEmail(), "1111", "name","surname","faculty"));
    }

    @Test
    public void testAll() {
        Iterator<AppUser> iterator = userService.all().iterator();
        assertFalse(iterator.hasNext());
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        iterator = userService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        assertEquals(userService.all().iterator().next(), userService.find(userService.all().iterator().next().getEmail()));
        assertNull(userService.find("Not a valid email."));
    }

    @Test
    public void testUpdate() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        userService.add(appUser2.getEmail(), appUser2.getPassword(), appUser2.getName(), appUser2.getSurname(), appUser2.getFaculty());
        List<AppUser> users = new ArrayList<>();
        userService.all().forEach(users::add);
        assertEquals(2, users.size());
        appUser = users.get(0);
        appUser2 = users.get(1);

        assertEquals(419, userService.update("not a valid email", "nonexistent attribute", "random value"));
        assertNotEquals(userService.find(appUser.getEmail()), userService.find(appUser2.getEmail()));
        assertEquals(200, userService.update(appUser2.getEmail(),"password","1234"));
        userService.update(appUser2.getEmail(), "name", appUser.getName());
        assertEquals(userService.find(appUser.getEmail()).getName(), userService.find(appUser2.getEmail()).getName());
        userService.update(appUser2.getEmail(), "faculty", appUser.getFaculty());
        assertEquals(userService.find(appUser.getEmail()).getFaculty(), userService.find(appUser2.getEmail()).getFaculty());
        assertEquals(412,  userService.update(appUser2.getEmail(), "non_existent_attribute", "value"));
    }

    @Test
    public void testDelete() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        userService.add(appUser2.getEmail(), appUser2.getPassword(), appUser2.getName(), appUser2.getSurname(), appUser2.getFaculty());
        List<AppUser> users = new ArrayList<>();
        userService.all().forEach(users::add);
        assertEquals(2, users.size());
        appUser = users.get(0);
        appUser2 = users.get(1);
        userService.delete(users.get(0).getEmail());
        users = new ArrayList<>();
        userService.all().forEach(users::add);
        assertEquals(1, users.size());
        assertNull(userService.find(appUser.getEmail()));
        assertFalse(users.contains(appUser));
        assertNotNull(userService.find(appUser2.getEmail()));
        assertTrue(users.contains(appUser2));
    }

    @Test
    public void testAddRole() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty());
        userService.addRole(appUser.getEmail(),"MANAGER");
        Iterator<Role> roles = userService.find(appUser.getEmail()).getRoles().iterator();
        String role1 = roles.next().getName();
        String role2 = roles.next().getName();
        String swap;
        if(role2.equals("ROLE_USER")) {
            swap = role2;
            role2 = role1;
            role1 = swap;
        }
        assertEquals(role1, "ROLE_USER");
        assertEquals(role2,"MANAGER");
    }
}