package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AppUser {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String faculty;

    private Set<Role> roles;

    /**
     * Constructs an AppUser entity.
     * @param email the user's email.
     * @param password the user's password.
     * @param name the user's first name.
     * @param surname the user's last name.
     * @param faculty the faculty the user belongs to.
     */
    public AppUser(String email, String password, String name, String surname, String faculty) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
    }

    public AppUser() {

    }

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
