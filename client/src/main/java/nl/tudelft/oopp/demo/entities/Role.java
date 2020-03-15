package nl.tudelft.oopp.demo.entities;

import java.util.Set;

public class Role {

    private int id;
    private String name;
    private Set<AppUser> appUsers;

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
