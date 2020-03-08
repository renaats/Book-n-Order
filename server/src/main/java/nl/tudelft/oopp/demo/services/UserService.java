package nl.tudelft.oopp.demo.services;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.demo.entities.AppUser;

public interface UserService {
    void logout(HttpServletRequest request);

    int add(String email, String password, String name, String surname, String faculty);

    int update(String email, String attribute, String value);

    int delete(String email);

    Iterable<AppUser> all();

    AppUser find(String email);

    void addRole(String email, String roleName);
}
