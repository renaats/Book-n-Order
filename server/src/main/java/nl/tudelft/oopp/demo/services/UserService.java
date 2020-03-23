package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;
import static nl.tudelft.oopp.demo.security.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.events.OnRegistrationSuccessEvent;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private MailSender mailSender;
    

    /**
     * Finds the appUser for some Http request token.
     * @param token = the token received in the request.
     * @param userRepository = the userRepository where all user information is stored.
     * @return an instance of AppUser, or null if no such AppUser exists.
     */
    public static AppUser getAppUser(String token, UserRepository userRepository) {
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null && userRepository.existsById(user)) {
                return userRepository.findByEmail(user);
            }
        }
        return null;
    }

    /**
     * Logs out from the current account.
     * @param request = the Http request that calls this method
     */
    public int logout(HttpServletRequest request) {
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
                return 201;
            }
        }
        return 419;
    }

    /**
     * Returns information about the user account.
     * @param request = the Http request that calls this method
     * @return account information about the account that requests it.
     */
    public String userInfo(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null && userRepository.existsById(user)) {
                AppUser appUser = userRepository.findByEmail(user);
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                return gson.toJson(appUser);
            }
        }
        return null;
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
        if (!EmailValidator.getInstance().isValid(URLDecoder.decode(email, StandardCharsets.UTF_8))) {
            return 423;
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
        try {
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUser));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Event did not go as expected.");
        }
        return 203;
    }

    /**
     *  Checks whether the input of the user is equal to the one sent in the email.
     * @param request The request, which validates the six digit code
     * @param sixDigitCode User's six digit input
     * @return  An error code corresponding outcome of the request
     */
    public int validate(HttpServletRequest request, int sixDigitCode) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null && userRepository.existsById(user)) {
                AppUser appUser = userRepository.findByEmail(user);
                if (sixDigitCode == appUser.getConfirmationNumber()) {
                    userRepository.save(appUser);
                    return 200;
                }
                return 431;
            }
        }
        return 419;
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
            case "password":
                appUser.setPassword(bcryptPasswordEncoder.encode(value));
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
    public List<AppUser> all() {
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
    public int addRole(String email, String roleName) {
        if (!userRepository.existsById(email)) {
            return 419;
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
        return 201;
    }

    /**
     * Retrieves a boolean value representing whether the user is allowed to access the admin panel.
     * @param request = the Http request that calls this method.
     */
    public boolean isAdmin(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null && userRepository.existsById(user)) {
                AppUser appUser = userRepository.findByEmail(user);
                return appUser.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))
                        || appUser.getRoles().contains(roleRepository.findByName("ROLE_BUILDING_ADMIN"))
                        || appUser.getRoles().contains(roleRepository.findByName("ROLE_BIKE_ADMIN"))
                        || appUser.getRoles().contains(roleRepository.findByName("ROLE_RESTAURANT"));
            }
        }
        return false;
    }

    /**
     * Sends an email with the new password to the user.
     * @param email User's email
     * @return  error code corresponding to the actions taken
     */
    public int recoverPassword(String email) {
        if (userRepository.findByEmail(email) != null) {
            RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 90)
                    .build();
            String password = pwdGenerator.generate(10);
            String recipient = email;
            String subject = "Password Recovery";
            SimpleMailMessage email2 = new SimpleMailMessage();
            email2.setTo(recipient);
            email2.setSubject(subject);
            email2.setText("This email provides you with the new password.\nTU Delft advices you to change it immediately "
                    + "after logging in to secure yourself from unwanted presence.\n"
                    + "New password:    " + password);
            mailSender.send(email2);
            AppUser appUser = userRepository.findByEmail(email);
            appUser.setPassword(bcryptPasswordEncoder.encode(password));
            userRepository.save(appUser);
            return 205;
        }
        return 419;
    }
}
