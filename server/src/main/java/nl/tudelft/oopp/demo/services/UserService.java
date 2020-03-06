package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.User;

public interface UserService {
    String add(String email, String password, String name, String surname, String faculty);

    String update(String email, String attribute, String value);

    String delete(String email);

    Iterable<User> all();

    User find(String email);
}
