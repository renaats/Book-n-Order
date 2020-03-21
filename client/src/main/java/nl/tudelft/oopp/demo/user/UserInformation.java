package nl.tudelft.oopp.demo.user;

import nl.tudelft.oopp.demo.entities.RoomReservation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserInformation {
    private String email;
    private String name;
    private String surname;
    private String faculty;

    /**
     * Constructs an UserInformation entity.
     * @param email the user's email.
     * @param name the user's first name.
     * @param surname the user's last name.
     * @param faculty the faculty the user belongs to.
     */
    public UserInformation(String email, String password, String name, String surname, String faculty) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
    }

    public UserInformation() {

    }

    Set<RoomReservation> roomReservations = new HashSet<>();

    public String getEmail() {
        return email;
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

    public Set<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInformation)) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(faculty, that.faculty) &&
                Objects.equals(roomReservations, that.roomReservations);
    }
}
