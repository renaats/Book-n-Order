package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.Constants.ADMIN;

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

/**
 * Creates server side endpoints and routes requests to the RoleService.
 * Maps all requests that start with "/role".
 * Manages access control on a per-method basis.
 */
@Repository
@RestController
@RequestMapping(path = "/role")
public class RoleController {
    @Autowired
     private RoleService roleService;

    /**
     * Adds a role to the database.
     * @param name = the name of the new role.
     * @return Error code
     */
    @Secured(ADMIN)
    @PostMapping(path = "/add")
    @ResponseBody
    public int addRole(@RequestParam String name) {
        return roleService.add(name);
    }

    /**
     * Updates a the name of a role.
     * @param id = the role id.
     * @param name = new name.
     * @return Error code
     */
    @Secured(ADMIN)
    @PostMapping(path = "/update_name")
    @ResponseBody
    public int updateName(@RequestParam int id, @RequestParam String name) {
        return roleService.update(id, name);
    }

    /**
     * Updates a the users of a role,
     * @param id = the role id.
     * @return Error code
     */
    @Secured(ADMIN)
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRole(@RequestParam int id) {
        return roleService.delete(id);
    }

    /**
     * Lists all roles in the database.
     * @return An Iterable of all roles.
     */
    @Secured(ADMIN)
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
    @Secured(ADMIN)
    @GetMapping(path = "/find/{roleId}")
    @ResponseBody
    public Role findRole(@PathVariable(name = "roleId") int id) {
        return roleService.find(id);
    }
}