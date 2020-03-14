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
     * Adds a role to the database.
     * @param name = the name of the new role.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/add")
    @ResponseBody
    public int addRole(@RequestParam String name) {
        return roleService.add(name);
    }

    /**
     * Updates a the name of a role.
     * @param id = the role id.
     * @param name = new name.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/update_name")
    @ResponseBody
    public int updateName(@RequestParam int id, @RequestParam String name) {
        return roleService.update(id, name);
    }

    /**
     * Updates a the users of a role,
     * @param id = the role id.
     * @return String containing the result of your request.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public int deleteRole(@RequestParam int id) {
        return roleService.delete(id);
    }

    /**
     * Lists all roles in the database.
     * @return An Iterable of all roles.
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