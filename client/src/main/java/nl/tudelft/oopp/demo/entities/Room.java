package nl.tudelft.oopp.demo.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the Room object that is retrieved from the server
 */
public class Room {

    private Integer id;
    private String name;
    private Building building;
    private String faculty;
    private String studySpecific;
    private String status;
    private boolean projector;
    private boolean screen;
    private int capacity;
    private int plugs;

    Set<RoomReservation> roomReservations = new HashSet<>();

    /**
     * Creates a new instance of Room.
     * @param name = name of the room.
     * @param building = building in which room is situated.
     * @param studySpecific = whether the room is faculty specific.
     * @param projector = whether the room has a projector.
     * @param screen = whether the room has a screen.
     * @param capacity = number of people who can sit in the room.
     * @param plugs = number of plugs in the room.
     * @param status = the status of the room.
     */
     
    public Room(String name, Building building, String studySpecific, boolean projector, boolean screen, int capacity, int plugs, String status) {
        this.name = name;
        this.building = building;
        this.studySpecific = studySpecific;
        this.projector = projector;
        this.screen = screen;
        this.capacity = capacity;
        this.plugs = plugs;
        this.status = status;
    }

    public Room() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setStudySpecific(String studySpecific) {
        this.studySpecific = studySpecific;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public void setScreen(boolean screen) {
        this.screen = screen;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPlugs(int plugs) {
        this.plugs = plugs;
    }

    public void setRoomReservations(Set<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudySpecific() {
        return studySpecific;
    }

    public String getStatus() {
        return status;
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

    public String getBuildingName() {
        return building.getName();
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String name = getBuilding().getName();
        return new SimpleStringProperty(name);
    }

    public String getFaculty() {
        return faculty;
    }

    public boolean isProjector() {
        return projector;
    }

    public boolean isScreen() {
        return screen;
    }

    public int getCapacity() {
        return capacity;
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
        for (RoomReservation roomReservation: roomReservations) {
            if (fromTime.compareTo(roomReservation.getFromTime()) < 0 && toTime.compareTo(roomReservation.getFromTime()) > 0) {
                return true;
            }
            if (fromTime.compareTo(roomReservation.getToTime()) < 0 && toTime.compareTo(roomReservation.getToTime()) > 0) {
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
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return projector == room.projector
                && screen == room.screen
                && capacity == room.capacity
                && plugs == room.plugs
                && Objects.equals(id, room.id)
                && Objects.equals(name, room.name)
                && Objects.equals(building, room.building)
                && Objects.equals(studySpecific, room.studySpecific)
                && Objects.equals(status, room.status)
                && Objects.equals(roomReservations, room.roomReservations);
    }
}
