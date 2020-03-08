package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Repository
@RestController
@RequestMapping(path = "/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * Adds a role.
     * @param name = the name of the role
     * @return String to see if your request passed
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/add")
    @ResponseBody
    public String addRole(@RequestParam String name) {
        return roleService.add(name);
    }

    /**
     * Updates a role name.
     * @param id = the role id
     * @param name = The role name
     * @return message if it passes
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/update_name")
    @ResponseBody
    public String updateName(@RequestParam int id, @RequestParam String name) {
        return roleService.update(id, name);
    }

    /**
     * Deletes a role.
     * @param id = the id of the role
     * @return String to see if your request passed
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public String deleteRole(@RequestParam int id) {
        return roleService.delete(id);
    }

    /**
     * Lists all roles.
     * @return all roles
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<Role> getAllRoles() {
        return roleService.all();
    }

    /**
     * Finds a role with the specified id.
     * @param id = the id of the role
     * @return the role that matches the provided id
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/find/{roleId}")
    @ResponseBody
    public Role findRole(@PathVariable(name = "roleId") int id) {
        return roleService.find(id);
    }
}
