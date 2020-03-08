package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.User;

public interface UserService {
    int add(String email, String password, String name, String surname, String faculty);

    int update(String email, String attribute, String value);

    int delete(String email);

    Iterable<User> all();

    User find(String email);
}
