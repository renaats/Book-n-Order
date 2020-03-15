package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return 200;
    }

    /**
     * Updates a role name.
     * @param id = the role id
     * @param name = The role name
     * @return message if it passes
     */
    public int update(int id, String name) {
        Role role = roleRepository.getOne(id);
        String old = role.getName();
        role.setName(name);
        roleRepository.save(role);
        return 200;
    }

    /**
     * Deletes a role.
     * @param id = the id of the role
     * @return String to see if your request passed
     */
    public int delete(int id) {
        if (!roleRepository.existsById(id)) {
            return 416;
        }
        roleRepository.deleteById(id);
        return 200;
    }

    /**
     * Lists all roles.
     * @return all roles
     */
    public Iterable<Role> all() {
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
