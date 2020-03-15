package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AppUser {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String faculty;
    private boolean loggedIn = false;
    private Set<Role> roles;

    Set<RoomReservation> roomReservations = new HashSet<>();

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoomReservations(Set<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppUser appUser = (AppUser) o;
        return email.equals(appUser.email)
                && Objects.equals(password, appUser.password)
                && Objects.equals(name, appUser.name)
                && Objects.equals(surname, appUser.surname)
                && Objects.equals(faculty, appUser.faculty)
                && Objects.equals(roles, appUser.roles)
                && Objects.equals(roomReservations, appUser.roomReservations);
    }

}
