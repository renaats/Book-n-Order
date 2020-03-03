package nl.tudelft.oopp.demo.controllers;

import java.util.Set;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/role") // This means URL's start with /role (after Application path)
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Adds a role.
     * @param name = the name of the role
     * @param users = the users of this role
     * @return String to see if your request passed
     */
    @PostMapping(path = "/add")
    @ResponseBody
    public String addRole(@RequestParam String name, @RequestParam Set<User> users) {
        Role role = new Role();
        role.setName(name);
        role.setUsers(users);
        roleRepository.save(role);
        return "Role has been created!";
    }

    /**
     * Deletes a role.
     * @param id = the id of the role
     * @return String to see if your request passed
     */
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public String deleteRole(@RequestParam int id) {
        if (!roleRepository.existsById(id)) {
            return "The role with ID " + id + " does not exist!";
        }
        roleRepository.deleteById(id);
        return "The role has been deleted!";
    }

    /**
     * Updates a role name.
     * @param id = the role id
     * @param name = The role name
     * @return message if it passes
     */
    @PostMapping(path = "/update_name")
    @ResponseBody
    public String updateName(@RequestParam int id, @RequestParam String name) {
        Role role = roleRepository.getOne(id);
        String old = role.getName();
        role.setName(name);
        roleRepository.save(role);
        return "The name of the role with ID " + id + " has been changed from " + old + " to " + name + "!";
    }

    /**
     * Updates a role users.
     * @param id = the role id
     * @param users = The role users
     * @return message if it passes
     */
    @PostMapping(path = "/update_users")
    @ResponseBody
    public String updateUsers(@RequestParam int id, @RequestParam Set<User> users) {
        Role role = roleRepository.getOne(id);
        Set<User> old = role.getUsers();
        role.setUsers(users);
        roleRepository.save(role);
        return "The users of the role with ID " + id + " has been updated!";
    }

    /**
     * Lists all roles.
     * @return all roles
     */
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Role> getAllRoles() {
        // This returns a JSON or XML with the roles
        return roleRepository.findAll();
    }
}
