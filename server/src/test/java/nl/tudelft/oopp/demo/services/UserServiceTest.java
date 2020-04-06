package nl.tudelft.oopp.demo.services;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ADDED_CONFIRM_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_CONFIRMATION;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_EMAIL_DOMAIN;
import static nl.tudelft.oopp.demo.config.Constants.RECOVER_PASSWORD;
import static nl.tudelft.oopp.demo.config.Constants.USER;
import static nl.tudelft.oopp.demo.config.Constants.USER_NOT_FOUND;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auth0.jwt.JWT;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;

import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the User service.
 */
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

    @Autowired
    RoleRepository roleRepository;

    AppUser appUser;
    AppUser appUser2;
    Role role;
    Set<Role> roleSet;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setEmail("m.b.spasov@student.tudelft.nl");
        appUser.setPassword("1234");
        appUser.setName("Mihail");
        appUser.setSurname("Spasov");
        appUser.setFaculty("EEMCS");
        appUser.setStudy("CSE");
        role = new Role();
        role.setName(USER);
        roleSet = new HashSet<>();
        roleSet.add(role);
        appUser.setRoles(roleSet);

        appUser2 = new AppUser();
        appUser2.setEmail("staff@tudelft.nl");
        appUser2.setPassword("1111");
        appUser2.setName("Name");
        appUser2.setSurname("Surname");
        appUser2.setFaculty("IO");
        appUser2.setStudy("EE");
        appUser2.setRoles(roleSet);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(userService);
    }

    /**
     * Tests the saving and retrieval of an instance of Room.
     */
    @Test
    public void testCreate() {
        roleRepository.save(role);
        assertEquals(ADDED_CONFIRM_EMAIL, userService.add(
                appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy()));
        userService.find(appUser.getEmail()).setRoles(roleSet);
        assertEquals(Collections.singletonList(appUser), userService.all());
    }

    /**
     * Tests the error message when the entered email is not valid.
     */
    @Test
    public void testCreateInvalidEmail() {
        assertEquals(INVALID_EMAIL, userService.add("notValidEmail", "1111", "name","surname","faculty", "study"));
    }

    /**
     * Tests the error message when the entered email is not from TU Delft.
     */
    @Test
    public void testCreateNonDelftEmail() {
        assertEquals(INVALID_EMAIL_DOMAIN, userService.add("r.jursevskis@gmail.com", "1111", "name","surname","faculty", "study"));
    }

    /**
     * Tests the error message when the entered email already has an account.
     */
    @Test
    public void testCreateDuplicateAccount() {
        assertEquals(ADDED_CONFIRM_EMAIL, userService.add(appUser.getEmail(), "1111", "name","surname","faculty", "study"));
        assertEquals(DUPLICATE_EMAIL, userService.add(appUser.getEmail(), "1111", "name","surname","faculty", "study"));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(userService.find("Not a valid email."));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        userService.add(
                appUser2.getEmail(), appUser2.getPassword(), appUser2.getName(), appUser2.getSurname(), appUser2.getFaculty(), appUser2.getStudy());
        String email = userService.all().get(0).getEmail();
        assertNotNull(userService.find(email));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(USER_NOT_FOUND, userService.update("not a valid email", "nonexistent attribute", "random value"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        assertEquals(ATTRIBUTE_NOT_FOUND,  userService.update(email, "non_existent_attribute", "value"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    public void testChangeName() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        assertNotEquals("Renats", userService.all().get(0).getName());
        userService.update(email, "name", "Renats");
        assertEquals("Renats", userService.all().get(0).getName());
    }

    /**
     * Tests the change of the surname by using the service.
     */
    @Test
    public void testChangeSurname() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        assertNotEquals("Jursevskis", userService.all().get(0).getSurname());
        userService.update(email, "surname", "Jursevskis");
        assertEquals("Jursevskis", userService.all().get(0).getSurname());
    }

    /**
     * Tests the change of the password by using the service.
     */
    @Test
    public void testChangePassword() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        String password = userService.all().get(0).getPassword();
        assertEquals(password, userService.all().get(0).getPassword());
        userService.update(email, "password", "abc");
        assertNotEquals(password, userService.all().get(0).getPassword());
    }

    /**
     * Tests the change of the faculty by using the service.
     */
    @Test
    public void testChangeFaculty() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        assertNotEquals("3M", userService.all().get(0).getFaculty());
        userService.update(email, "faculty", "3M");
        assertEquals("3M", userService.all().get(0).getFaculty());
    }

    /**
     * Tests the successful validation of the user.
     */
    @Test
    public void testSuccessfulValidation() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        int number = userService.find(appUser.getEmail()).getConfirmationNumber();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals(EXECUTED, userService.validate(request, number));
    }

    /**
     * Tests the failure of validation with wrong email or wrong sixDigitCode.
     */
    @Test
    public void testUnsuccessfulValidation() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals(INVALID_CONFIRMATION, userService.validate(request, 12345));
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        token = JWT.create()
                .withSubject("NotAnEmailOfARealUser@tudelft.nl")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request2.addHeader(HEADER_STRING, token);
        int number = userService.find(appUser.getEmail()).getConfirmationNumber();
        assertEquals(USER_NOT_FOUND, userService.validate(request2, number));
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDelete() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        String email = userService.all().get(0).getEmail();
        assertEquals(EXECUTED, userService.delete(email));
        assertEquals(0, userService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(USER_NOT_FOUND, userService.delete("asd"));
    }

    /**
     * Tests the addition of a role for a user.
     */
    @Test
    public void testAddRole() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        userService.addRole(appUser.getEmail(),ADMIN);
        Iterator<Role> roles = userService.find(appUser.getEmail()).getRoles().iterator();
        String role1 = roles.next().getName();
        String role2 = roles.next().getName();
        String swap;
        if (role2.equals(USER)) {
            swap = role2;
            role2 = role1;
            role1 = swap;
        }
        assertEquals(role1, USER);
        assertEquals(role2,ADMIN);
    }

    /**
     * Tests the addition of a role to a non-existent user.
     */
    @Test
    public void testAddRoleNonExistent() {
        assertEquals(USER_NOT_FOUND, userService.addRole("a",ADMIN));
    }

    /**
     * Tests the retrieval of user's own info for some user.
     */
    @Test
    public void testUserInfo() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals("{\"email\":\"" + appUser.getEmail() + "\",\"name\":\"" + appUser.getName() + "\",\"surname\":\"" + appUser.getSurname()
                + "\",\"faculty\":\"" + appUser.getFaculty() + "\",\"study\":\"" + appUser.getStudy() + "\"}", userService.userInfo(request));
    }

    /**
     * Tests the retrieval of user's own info for a non-existent user.
     */
    @Test
    public void testNonExistentUserInfo() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertNull(userService.userInfo(request));
    }

    /**
     * Tests the logging out for some user.
     */
    @Test
    public void testLogout() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertEquals(ADDED, userService.logout(request));
    }

    /**
     * Tests the logging out for a non-existent user.
     */
    @Test
    public void testNonExistentLogOut() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(USER_NOT_FOUND, userService.logout(request));
    }

    /**
     * Tests the admin request for some user with and without the required role.
     */
    @Test
    public void testAdmin() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        assertFalse(userService.isAdmin(request));
        userService.addRole(appUser.getEmail(), ADMIN);
        assertTrue(userService.isAdmin(request));
    }

    /**
     * Tests the hasAdminRole request for some user with and without the required role.
     */
    @Test
    public void testHasAdminRole() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        userService.addRole(appUser.getEmail(), BUILDING_ADMIN);
        assertFalse(userService.hasAdminRole(request));
        userService.addRole(appUser.getEmail(), ADMIN);
        assertTrue(userService.hasAdminRole(request));
    }

    /**
     * Tests the admin request for a non-existent user.
     */
    @Test
    public void testNonExistentAdmin() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertFalse(userService.isAdmin(request));
    }

    /**
     * Test the recoverPassword method.
     */
    @Test
    public void testRecoverPassword() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        assertEquals(RECOVER_PASSWORD, userService.recoverPassword(appUser.getEmail()));
        assertEquals(USER_NOT_FOUND, userService.recoverPassword("NotARealEmail@tudelft.nl"));
    }
     
    /**   
     * Tests the activation request for some user with and without an activated account.
     */
    @Test
    public void testActivation() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        int number = userService.find(appUser.getEmail()).getConfirmationNumber();
        assertFalse(userService.isActivated(request));
        userService.validate(request, number);
        assertTrue(userService.isActivated(request));
    }

    /**
     * Tests the activation request for a non-existent user.
     */
    @Test
    public void testNonExistentActivation() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertFalse(userService.isActivated(request));
    }

    /**
     * Tests the changePassword request for some user.
     */
    @Test
    public void testChangeOwnPassword() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        String password = userService.all().get(0).getPassword();
        assertEquals(EXECUTED, userService.changePassword(request, password));
        assertNotEquals(password, userService.all().get(0).getPassword());
    }

    /**
     * Tests the changePassword request for a non-existent user.
     */
    @Test
    public void testNonExistentPassword() {
        userService.add(appUser.getEmail(), appUser.getPassword(), appUser.getName(), appUser.getSurname(), appUser.getFaculty(), appUser.getStudy());
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(USER_NOT_FOUND, userService.changePassword(request, "123"));
    }
}