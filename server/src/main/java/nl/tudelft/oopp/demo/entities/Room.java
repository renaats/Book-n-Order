package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity // This tells Hibernate to make a table out of this class
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building building;

    private String faculty;
    private boolean facultySpecific;
    private boolean projector;
    private boolean screen;
    private int nrPeople;
    private int plugs;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    Set<RoomReservation> roomReservations = new HashSet<>();


    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setFacultySpecific(boolean facultySpecific) {
        this.facultySpecific = facultySpecific;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public void setScreen(boolean screen) {
        this.screen = screen;
    }

    public void setNrPeople(int nrPeople) {
        this.nrPeople = nrPeople;
    }

    public void setPlugs(int plugs) {
        this.plugs = plugs;
    }

    public void setRoomReservations(Set<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Building getBuilding() {
        return building;
    }

    public String getFaculty() {
        return faculty;
    }

    public boolean isFacultySpecific() {
        return facultySpecific;
    }

    public boolean isProjector() {
        return projector;
    }

    public boolean isScreen() {
        return screen;
    }

    public int getNrPeople() {
        return nrPeople;
    }

    public int getPlugs() {
        return plugs;
    }

    public Set<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public boolean hasRoomReservations() {
        return roomReservations.size() > 0;
    }

    /**
     * Checks if this room has a reservation between the specified times.
     * @param fromTime = the starting time.
     * @param toTime = the ending time.
     * @return true if this room has a reservation between these times, false otherwise.
     */
    public boolean hasRoomReservationBetween(Date fromTime, Date toTime) {
        if (fromTime.compareTo(toTime) > 0) {
            return false;
        }
        for (RoomReservation roomReservation: roomReservations) {
            if (fromTime.compareTo(roomReservation.getFromTime()) < 0 && toTime.compareTo(roomReservation.getFromTime()) > 0) {
                return true;
            }
            if (fromTime.compareTo(roomReservation.getToTime()) < 0 && toTime.compareTo(roomReservation.getToTime()) > 0) {
                return true;
            }
            if (fromTime.compareTo(roomReservation.getFromTime()) > 0 && fromTime.compareTo(roomReservation.getToTime()) < 0) {
                return true;
            }
            if (toTime.compareTo(roomReservation.getFromTime()) > 0 && toTime.compareTo(roomReservation.getToTime()) < 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return facultySpecific == room.facultySpecific
                && projector == room.projector
                && screen == room.screen
                && nrPeople == room.nrPeople
                && plugs == room.plugs
                && Objects.equals(name, room.name)
                && Objects.equals(building, room.building)
                && Objects.equals(faculty, room.faculty)
                && Objects.equals(roomReservations, room.roomReservations);
    }

}
