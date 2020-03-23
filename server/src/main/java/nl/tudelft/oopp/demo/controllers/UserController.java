package nl.tudelft.oopp.demo.controllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.events.OnRegistrationSuccessEvent;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;



/**
 * Creates server side endpoints and routes requests to the UserService.
 * Maps all requests that start with "/user".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /user (after Application path)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Logs out from the current account.
     * @param request = the Http request that calls this method
     */
    @PostMapping(path = "/logout")
    public int logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    /**
     * Returns information about the user account.
     * @param request = the Http request that calls this method
     * @return account information about the account that requests it.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/info")
    public String userInfo(HttpServletRequest request) {
        return userService.userInfo(request);
    }

    /**
     * Adds a user.
     * @param email = the email of the user
     * @param password = the password of the user
     * @param name = the name of the user
     * @param surname = the surname of the user
     * @param faculty = the faculty of the user
     * @return Error code
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public int addUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String faculty) {
        return userService.add(email,password,name,surname,faculty);
    }

    /**
     * Checks whether the input of the user is equal to the one sent in the email.
     * @param request The request, which validates the six digit code
     * @param sixDigitCode User's six digit input
     * @return  An error code corresponding outcome of the request
     */
    @PostMapping(path = "/validate")
    @ResponseBody
    public int validateUser(HttpServletRequest request,  @RequestParam int sixDigitCode) {
        return userService.validate(request, sixDigitCode);
    }

    /**
     * Updates a specified attribute for given user.
     * @param email = the email address of the user.
     * @param attribute = the attribute whose value is to be changed.
     * @param value = the new value of the attribute.
     * @return Error code
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/update")
    @ResponseBody
    public int updateAttribute(@RequestParam String email, @RequestParam String attribute, @RequestParam String value)
            throws UnsupportedEncodingException {
        return userService.update(email, attribute, value);
    }

    /**
     * Deletes an account.
     * @param email = the email of the account
     * @return Error code
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteUser(@RequestParam String email) {
        return userService.delete(email);
    }

    /**
     * Lists all accounts in the database.
     * Should be removed for the finished version!
     * @return Iterable of all accounts.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<AppUser> getAllUsers() {
        return userService.all();
    }

    /**
     * Retrieves an account given its email.
     * @return User with the specified email, or null if no such account exists.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/find")
    @ResponseBody
    public AppUser getUser(@RequestParam String email) {
        return userService.find(email);
    }

    /**
     * Adds a role to an account. If the role does not exist, it is created.
     * @param email = the email of the account
     * @param roleName = the name of the role
     */
    //@Secured("ROLE_ADMIN") SHOULD BE UNCOMMENTED WHEN IN PRODUCTION!
    @PostMapping(path = "/addRole")
    @ResponseBody
    public void addRole(@RequestParam String email, @RequestParam String roleName) {
        userService.addRole(email, roleName);
    }

    /**
     * Retrieves a boolean value representing whether the user is allowed to access the admin panel.
     * @param request = the Http request that calls this method.
     */
    @Secured("ROLE_USER")
    @GetMapping(path = "/admin")
    public boolean isAdmin(HttpServletRequest request) {
        return userService.isAdmin(request);
    }


    /**
     * Retrieves a boolean value representing whether the user account is activated.
     * @param request = the Http request that calls this method.
     */
    @GetMapping(path = "/activated")
    public boolean isActivated(HttpServletRequest request) {
        return userService.isActivated(request);
    }
}
