package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Role;

public interface RoleService {
    int add(String name);

    int update(int id, String name);

    int delete(int id);

    Iterable<Role> all();

    Role find(int id);
}
