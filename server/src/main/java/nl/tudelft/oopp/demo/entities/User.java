package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    private String email;
    private String password;
    private String name;
    private String surname;
    private String faculty;
    @ManyToMany
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
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

    public void addRoomReservation(RoomReservation roomReservation) {
        roomReservations.add(roomReservation);
    }

    /**
     * Removes a room reservation from this user.
     * @param roomReservation = the room reservation that is to be removed
     */
    public void removeRoomReservation(RoomReservation roomReservation) {
        roomReservations.remove(roomReservation);
    }
}
