package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ADDED_CONFIRM_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BIKE_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_ADMIN;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_CONFIRMATION;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_EMAIL;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_EMAIL_DOMAIN;
import static nl.tudelft.oopp.demo.config.Constants.RECOVER_PASSWORD;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT;
import static nl.tudelft.oopp.demo.config.Constants.STAFF;
import static nl.tudelft.oopp.demo.config.Constants.USER;
import static nl.tudelft.oopp.demo.config.Constants.USER_NOT_FOUND;
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
     * Automatically assigns some role to a user, sets their account as activated. Should not be used in production!
     * @param appUser = the user that is assigned the role.
     * @param roleName = the name of the new role.
     * @return an error code corresponding to the outcome of the request.
     */
    private int assign(AppUser appUser, String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        appUser.setConfirmationNumber(-1);
        appUser.addRole(roleRepository.findByName(roleName));
        userRepository.save(appUser);
        return ADDED;
    }

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
     * @return an error code corresponding to the outcome of the request
     */
    public int logout(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return USER_NOT_FOUND;
        }
        appUser.setLoggedIn(false);
        userRepository.save(appUser);
        return ADDED;
    }

    /**
     * Returns information about the user account.
     * @param request = the Http request that calls this method
     * @return account information about the account that requests it.
     */
    public String userInfo(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return null;
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(appUser);
    }

    /**
     * Adds a user.
     * @param email = the email of the user
     * @param password = the password of the user
     * @param name = the name of the user
     * @param surname = the surname of the user
     * @param faculty = the faculty of the user
     * @param study = the study of the user
     * @return an error code corresponding to the outcome of the request
     */
    public int add(String email, String password, String name, String surname, String faculty, String study) {
        if (!EmailValidator.getInstance().isValid(URLDecoder.decode(email, StandardCharsets.UTF_8))) {
            return INVALID_EMAIL;
        }
        if (!email.contains("@student.tudelft.nl") && !email.contains("@tudelft.nl")) {
            return INVALID_EMAIL_DOMAIN;
        }
        if (userRepository.existsById(email)) {
            return DUPLICATE_EMAIL;
        }
        AppUser appUser = new AppUser(email, bcryptPasswordEncoder.encode(password), name, surname, faculty, study);
        appUser.setRoles(new HashSet<>());
        if (!roleRepository.existsByName(USER)) {
            Role role = new Role();
            role.setName(USER);
            roleRepository.save(role);
        }
        appUser.addRole(roleRepository.findByName(USER));
        if (email.contains("@tudelft.nl")) {
            if (!roleRepository.existsByName(STAFF)) {
                Role role = new Role();
                role.setName(STAFF);
                roleRepository.save(role);
            }
            appUser.addRole(roleRepository.findByName(STAFF));
        }

        // NEED TO BE DELETED BEFORE PRODUCTION! ONLY USED FOR END-TO-END TESTING!
        if (appUser.getEmail().equals("staff@tudelft.nl")) {
            return assign(appUser, STAFF);
        }
        if (appUser.getEmail().equals("admin@tudelft.nl")) {
            return assign(appUser, ADMIN);
        }
        if (appUser.getEmail().equals("building_admin@tudelft.nl")) {
            return assign(appUser, BUILDING_ADMIN);
        }
        if (appUser.getEmail().equals("bike_admin@tudelft.nl")) {
            return assign(appUser, BIKE_ADMIN);
        }
        if (appUser.getEmail().equals("restaurant@tudelft.nl")) {
            return assign(appUser, RESTAURANT);
        }
        // NEED TO BE DELETED BEFORE PRODUCTION! ONLY USED FOR END-TO-END TESTING!

        userRepository.save(appUser);
        try {
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUser));
        } catch (Exception e) {
            //Left empty
        }
        return ADDED_CONFIRM_EMAIL;
    }

    /**
     * Checks whether the input of the user is equal to the one sent in the email.
     * @param request The request, which validates the six digit code
     * @param sixDigitCode User's six digit input
     * @return an error code corresponding to the outcome of the request
     */
    public int validate(HttpServletRequest request, int sixDigitCode) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return USER_NOT_FOUND;
        }
        if (sixDigitCode == appUser.getConfirmationNumber()) {
            appUser.setConfirmationNumber(-1);
            userRepository.save(appUser);
            return EXECUTED;
        }
        return INVALID_CONFIRMATION;
    }

    /**
     * Updates a specified attribute for some user.
     * @param email = the email of the user
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return an error code corresponding to the outcome of the request
     */
    public int update(String email, String attribute, String value) {
        if (userRepository.findById(email).isEmpty()) {
            return USER_NOT_FOUND;
        }
        AppUser appUser = userRepository.findById(email).get();

        switch (attribute) {
            case "password":
                appUser.setPassword(bcryptPasswordEncoder.encode(URLDecoder.decode(value, StandardCharsets.UTF_8)));
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
            case "study":
                appUser.setStudy(value);
                break;
            default:
                return ATTRIBUTE_NOT_FOUND;
        }
        userRepository.save(appUser);
        return EXECUTED;
    }

    /**
     * Deletes an account.
     * @param email = the email of the account
     * @return an error code corresponding to the outcome of the request
     */
    public int delete(String email) {
        if (!userRepository.existsById(email)) {
            return USER_NOT_FOUND;
        }
        userRepository.deleteById(email);
        return EXECUTED;
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
     * @return an error code corresponding to the outcome of the request
     */
    public int addRole(String email, String roleName) {
        if (!userRepository.existsById(email)) {
            return USER_NOT_FOUND;
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
        return ADDED;
    }

    /**
     * Retrieves a boolean value representing whether the user is allowed to access the admin panel.
     * @param request = the Http request that calls this method.
     * @return a boolean value representing the admin status of the user
     */
    public boolean isAdmin(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return false;
        }
        return appUser.getRoles().contains(roleRepository.findByName(ADMIN))
                || appUser.getRoles().contains(roleRepository.findByName(BUILDING_ADMIN))
                || appUser.getRoles().contains(roleRepository.findByName(BIKE_ADMIN))
                || appUser.getRoles().contains(roleRepository.findByName(RESTAURANT));
    }

    /**
     * Retrieves a boolean value representing whether the user has the admin role.
     * @param request = the Http request that calls this method.
     * @return a boolean value representing whether the user has an admin role.
     */
    public boolean hasAdminRole(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return false;
        }
        return appUser.getRoles().contains(roleRepository.findByName(ADMIN));
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
            String subject = "Password Recovery";
            SimpleMailMessage email2 = new SimpleMailMessage();
            email2.setTo(email);
            email2.setSubject(subject);
            email2.setText("This email provides you with the new password.\nTU Delft advices you to change it immediately "
                    + "after logging in to secure yourself from unwanted presence.\n"
                    + "New password:    " + password);
            mailSender.send(email2);
            AppUser appUser = userRepository.findByEmail(email);
            appUser.setPassword(bcryptPasswordEncoder.encode(password));
            userRepository.save(appUser);
            return RECOVER_PASSWORD;
        }
        return USER_NOT_FOUND;
    }

    /**
     * Retrieves a boolean value representing whether the user account is activated.
     * @param request = the Http request that calls this method.
     * @return a boolean value representing the status of the account's activation
     */
    public boolean isActivated(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return false;
        }
        return appUser.getConfirmationNumber() < 0;
    }

    /**
     * Changes a user's own password.
     * @param request = the Http request that calls this method.
     * @param password = the new password.
     * @return an error code corresponding to the outcome of the request
     */
    public int changePassword(HttpServletRequest request, String password) {
        String token = request.getHeader(HEADER_STRING);
        AppUser appUser = getAppUser(token, userRepository);
        if (appUser == null) {
            return USER_NOT_FOUND;
        }
        appUser.setPassword(bcryptPasswordEncoder.encode(password));
        appUser.setLoggedIn(false);
        userRepository.save(appUser);
        return EXECUTED;
    }
}