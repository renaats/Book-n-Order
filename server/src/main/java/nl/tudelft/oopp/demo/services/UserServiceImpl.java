package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    /**
     * Adds a user.
     * @param email = the email of the user
     * @param password = the password of the user
     * @param name = the name of the user
     * @param surname = the surname of the user
     * @param faculty = the faculty of the user
     * @return String to see if your request passed
     */
    public String add(String email, String password, String name, String surname, String faculty) {
        if (userRepository.existsById(email)) {
            return "The account with email " + email + " already exists!";
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(bcryptPasswordEncoder.encode(password));
        appUser.setName(name);
        appUser.setSurname(surname);
        appUser.setFaculty(faculty);
        userRepository.save(appUser);
        return "Account created!";
    }

    /**
     * Updates a specified attribute for some user.
     * @param email = the email of the user
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return String to see if your request passed
     */
    public String update(String email, String attribute, String value) {
        if (userRepository.findById(email).isEmpty()) {
            return "User with email " + email + " does not exist!";
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
                return "No attribute with name " + attribute + " found!";
        }
        userRepository.save(appUser);
        return "The attribute has been set!";
    }

    /**
     * Deletes an account.
     * @param email = the email of the account
     * @return String to see if your request passed
     */
    public String delete(String email) {
        if (!userRepository.existsById(email)) {
            return "The account with email " + email + " does not exist!";
        }
        userRepository.deleteById(email);
        return "The account with email " + email + " has been deleted!";
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
        AppUser appUser = userRepository.getOne(email);
        return appUser;
    }
}
