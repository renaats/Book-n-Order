package nl.tudelft.oopp.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.entities.VerificationToken;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.repositories.VerificationTokenRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;

import static nl.tudelft.oopp.demo.security.SecurityConstants.*;

/**
 * Supports CRUD operations for the AppUser entity.
 * Receives requests from the UserController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public void createVerificationToken(AppUser user, String token) {
        VerificationToken newUserToken = new VerificationToken(token, user);
        verificationTokenRepository.save(newUserToken);
    }

    /**
     * Logs out from the current account.
     * @param request = the Http request that calls this method
     */
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null && userRepository.existsById(user)) {
                AppUser appUser = userRepository.findByEmail(user);
                appUser.setLoggedIn(false);
                userRepository.save(appUser);
            }
        }
    }

    /**
     * Adds a user.
     * @param email = the email of the user
     * @param password = the password of the user
     * @param name = the name of the user
     * @param surname = the surname of the user
     * @param faculty = the faculty of the user
     * @return String to see if your request passed
     */
    public int add(String email, String password, String name, String surname, String faculty) {
        try {
            if (!EmailValidator.getInstance().isValid(URLDecoder.decode(email, "UTF-8"))) {
                return 423;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 502;
        }
        if (!email.contains("@student.tudelft.nl") && !email.contains("@tudelft.nl")) {
            return 424;
        }
        if (userRepository.existsById(email)) {
            return 310;
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(bcryptPasswordEncoder.encode(password));
        appUser.setName(name);
        appUser.setSurname(surname);
        appUser.setFaculty(faculty);
        appUser.setRoles(new HashSet<>());
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        appUser.addRole(roleRepository.findByName("ROLE_USER"));
        if (email.contains("@tudelft.nl")) {
            if (!roleRepository.existsByName("ROLE_STAFF")) {
                Role role = new Role();
                role.setName("ROLE_STAFF");
                roleRepository.save(role);
            }
            appUser.addRole(roleRepository.findByName("ROLE_STAFF"));
        }
        userRepository.save(appUser);
        return 201;
    }

    /**
     * Updates a specified attribute for some user.
     * @param email = the email of the user
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public int update(String email, String attribute, String value) {
        if (userRepository.findById(email).isEmpty()) {
            return 419;
        }
        AppUser appUser = userRepository.findById(email).get();

        switch (attribute) {
            case "email":
                appUser.setEmail(value);
                break;
            case "password":
                appUser.setPassword(value);
                break;
            case "name":
                appUser.setName(value);
                break;
            case "surname":
                appUser.setSurname(value);
                break;
            case "faculty":
                appUser.setFaculty(value);
                break;
            default:
                return 412;
        }
        userRepository.save(appUser);
        return 200;
    }

    /**
     * Deletes an account.
     * @param email = the email of the account
     * @return String to see if your request passed
     */
    public int delete(String email) {
        if (!userRepository.existsById(email)) {
            return 419;
        }
        userRepository.deleteById(email);
        return 200;
    }

    /**
     * Lists all accounts.
     * Should be removed for the finished version!
     * @return all accounts
     */
    public Iterable<AppUser> all() {
        return userRepository.findAll();
    }

    /**
     * Finds an account by its email.
     * @return an account that has the specified email or null if no such account exists
     */
    public AppUser find(String email) {
        if (!userRepository.existsById(email)) {
            return null;
        }
        return userRepository.getOne(email);
    }

    /**
     * Adds a role to an account. If the role does not exist, it is created.
     * @param email = the email of the account
     * @param roleName = the name of the role
     */
    public void addRole(String email, String roleName) {
        if (!userRepository.existsById(email)) {
            return;
        }
        AppUser appUser = userRepository.getOne(email);
        Role role;
        if (!roleRepository.existsByName(roleName)) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        role = roleRepository.findByName(roleName);
        appUser.addRole(role);
        userRepository.save(appUser);
    }
}
