package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Adds a role.
     * @param name = the name of the role
     * @return String to see if your request passed
     */
    public String add(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return "Role has been created!";
    }

    /**
     * Updates a role name.
     * @param id = the role id
     * @param name = The role name
     * @return message if it passes
     */
    public String update(int id, String name) {
        Role role = roleRepository.getOne(id);
        String old = role.getName();
        role.setName(name);
        roleRepository.save(role);
        return "The name of the role with ID " + id + " has been changed from " + old + " to " + name + "!";
    }

    /**
     * Deletes a role.
     * @param id = the id of the role
     * @return String to see if your request passed
     */
    public String delete(int id) {
        if (!roleRepository.existsById(id)) {
            return "The role with ID " + id + " does not exist!";
        }
        roleRepository.deleteById(id);
        return "The role has been deleted!";
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
