package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_NAME;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Supports CRUD operations for the Role entity.
 * Receives requests from the RoleController, manipulates the database and returns the answer.
 * Uses error codes defined in the client side package "errors".
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Adds a role.
     * @param name = the name of the role
     * @return String to see if your request passed
     */
    public int add(String name) {
        if (roleRepository.existsByName(name)) {
            return DUPLICATE_NAME;
        }
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return ADDED;
    }

    /**
     * Updates a role name.
     * @param id = the role id
     * @param name = The role name
     * @return message if it passes
     */
    public int update(int id, String name) {
        if (!roleRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        Role role = roleRepository.getOne(id);
        String old = role.getName();
        role.setName(name);
        roleRepository.save(role);
        return ADDED;
    }

    /**
     * Deletes a role.
     * @param id = the id of the role
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!roleRepository.existsById(id)) {
            return ID_NOT_FOUND;
        }
        roleRepository.deleteById(id);
        return EXECUTED;
    }

    /**
     * Lists all roles.
     * @return all roles
     */
    public List<Role> all() {
        return roleRepository.findAll();
    }

    /**
     * Finds a role with the specified id.
     * @param id = the id of the role
     * @return the role that matches the provided id
     */
    public Role find(int id) {
        return roleRepository.findById(id).orElse(null);
    }
}
