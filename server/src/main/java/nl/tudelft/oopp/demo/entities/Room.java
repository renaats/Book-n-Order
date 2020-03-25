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

/**
 * Represents a room. Holds all necessary information about the room that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Building as a foreign key.
 */
@Entity
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
    private Boolean facultySpecific;
    private Boolean projector;
    private Boolean screen;
    private int nrPeople;
    private int plugs;

    /**
     * Creates a new instance of Room.
     * @param name = name of the room.
     * @param building = building in which room is situated.
     * @param faculty = name of the faculty.
     * @param facultySpecific = whether the room is faculty specific.
     * @param projector = whether the room has a projector.
     * @param screen = whether the room has a screen.
     * @param nrPeople = number of people who can sit in the room.
     * @param plugs = number of plugs in the room.
     */
    public Room(String name, Building building, String faculty, boolean facultySpecific, boolean projector, boolean screen, int nrPeople, int plugs) {
        this.name = name;
        this.building = building;
        this.faculty = faculty;
        this.facultySpecific = facultySpecific;
        this.projector = projector;
        this.screen = screen;
        this.nrPeople = nrPeople;
        this.plugs = plugs;
    }

    public Room() {

    }

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
            return true;
        }
        for (RoomReservation roomReservation: roomReservations) {
            if (fromTime.compareTo(roomReservation.getFromTime()) <= 0 && toTime.compareTo(roomReservation.getFromTime()) > 0) {
                return true;
            }
            if (fromTime.compareTo(roomReservation.getToTime()) < 0 && toTime.compareTo(roomReservation.getToTime()) >= 0) {
                return true;
            }
            if (fromTime.compareTo(roomReservation.getFromTime()) >= 0 && fromTime.compareTo(roomReservation.getToTime()) < 0) {
                return true;
            }
            if (toTime.compareTo(roomReservation.getFromTime()) > 0 && toTime.compareTo(roomReservation.getToTime()) <= 0) {
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
