package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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
        if (userRepository.existsById(email)) {
            return 310;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setFaculty(faculty);
        userRepository.save(user);
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
        User user = userRepository.findById(email).get();

        switch (attribute) {
            case "email":
                user.setEmail(value);
                break;
            case "password":
                user.setPassword(value);
                break;
            case "name":
                user.setName(value);
                break;
            case "surname":
                user.setSurname(value);
                break;
            case "faculty":
                user.setFaculty(value);
                break;
            default:
                return 412;
        }
        userRepository.save(user);
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
    public Iterable<User> all() {
        return userRepository.findAll();
    }

    /**
     * Finds an account by its email.
     * @return an account that has the specified email or null if no such account exists
     */
    public User find(String email) {
        if (!userRepository.existsById(email)) {
            return null;
        }
        User user = userRepository.getOne(email);
        return user;
    }
}
