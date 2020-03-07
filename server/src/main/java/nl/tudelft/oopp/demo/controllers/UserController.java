package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Repository
@RestController // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /user (after Application path)
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Adds a user.
     * @param email = the email of the user
     * @param password = the password of the user
     * @param name = the name of the user
     * @param surname = the surname of the user
     * @param faculty = the faculty of the user
     * @return String to see if your request passed
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public String addUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String faculty) {
        return userService.add(email, password, name, surname, faculty);
    }

    /**
     * Updates a specified attribute for some user.
     * @param email = the email of the user
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/update")
    @ResponseBody
    public String updateAttribute(@RequestParam String email, @RequestParam String attribute, @RequestParam String value) {
        return userService.update(email, attribute, value);
    }

    /**
     * Deletes an account.
     * @param email = the email of the account
     * @return String to see if your request passed
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public String deleteUser(@RequestParam String email) {
        return userService.delete(email);
    }

    /**
     * Lists all accounts.
     * Should be removed for the finished version!
     * @return all accounts
     */
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<AppUser> getAllUsers() {
        return userService.all();
    }

    /**
     * Finds an account by its email.
     * @return an account that has the specified email or null if no such account exists
     */
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
    @PostMapping(path = "/addRole")
    @ResponseBody
    public void addRole(@RequestParam String email, @RequestParam String roleName) {
        userService.addRole(email, roleName);
    }
}
