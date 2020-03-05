package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Role;

public interface RoleService {
    String add(String name);

    String update(int id, String name);

    String delete(int id);

    Iterable<Role> all();

    Role find(int id);
}
