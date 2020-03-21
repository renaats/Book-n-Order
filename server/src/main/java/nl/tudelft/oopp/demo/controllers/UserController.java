package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.VerificationToken;
import nl.tudelft.oopp.demo.events.OnRegistrationSuccessEvent;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;

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

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    /**
     * Logs out from the current account.
     * @param request = the Http request that calls this method
     */
    @PostMapping(path = "/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
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
            @RequestParam String faculty,
            WebRequest request) {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(password);
        appUser.setName(name);
        appUser.setSurname(surname);
        appUser.setFaculty(faculty);
        AppUser registeredUser = appUser;
        int result = userService.add(registeredUser.getEmail(),registeredUser.getPassword(),registeredUser.getName(),registeredUser.getSurname(),registeredUser.getFaculty());
        if(result != 201) return result;
        try {
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(),"/user"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 203;
    }

    @PostMapping(path = "/validate")
    @ResponseBody
    public int validateUser(@RequestParam int sixDigitCode) {
        return user
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
    public int updateAttribute(@RequestParam String email, @RequestParam String attribute, @RequestParam String value) {
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

//    @GetMapping(path = "/confirmRegistration")
//    public String confirmRegistration(WebRequest request ,@RequestParam("token") String token) {
//        Locale locale=request.getLocale();
//        VerificationToken verificationToken = userService.getVerificationToken(token);
//        if(verificationToken == null) {
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            return "redirect:access-denied";
//        }
//        AppUser user = verificationToken.getUser();
//        Calendar calendar = Calendar.getInstance();
//        if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
//            String message = messages.getMessage("auth.message.expired", null, locale);
//            return "redirect:access-denied";
//        }
//        user.setEnabled(true);
//        userService.enableRegisteredUser(user);
//        return null;
//    }
}
