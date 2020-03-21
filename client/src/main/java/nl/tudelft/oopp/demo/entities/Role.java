package nl.tudelft.oopp.demo.entities;

import java.util.Set;

/**
 * Manages the user Role object that is retrieved from the server
 */
public class Role {

    private int id;
    private String name;
    private Set<AppUser> appUsers;

    /**
     * Creates a new instance of Role.
     * @param name = name of the role.

     */
    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }
}
