package nl.tudelft.oopp.demo.entities;

import java.util.Date;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the RoomReservation object that is retrieved from the server
 */
public class RoomReservation {

    private Integer id;
    private boolean active;
    private Room room;
    private AppUser appUser;
    private Date fromTime;
    private Date toTime;

    /** Creates a new instance of FoodOrder.
     * @param room the reserved room.
     * @param appUser user who booked the room.
     * @param fromTime time at which the room reservation starts.
     * @param toTime time at which the room reservation ends.
     */
    public RoomReservation(Room room, AppUser appUser, Date fromTime, Date toTime) {
        this.room = room;
        this.appUser = appUser;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = true;
    }

    public RoomReservation() {
        this.active = true;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public Room getRoom() {
        return room;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    /**
     * Makes the table list the from time instead of the from time object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getRoomNameProperty() {
        String room = getRoom().getName();
        return new SimpleStringProperty(room);
    }

    /**
     * Makes the table list the from time instead of the from time object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getToTimeProperty() {
        String day = "" + getFromTime().getDay();
        String time = "" + getFromTime().getTime();
        return new SimpleStringProperty("Day: " + day + " At " + time);
    }

    /**
     * Makes the table list the from time instead of the from time object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getFromTimeProperty() {
        String day = "" + getFromTime().getDay();
        String time = "" + getFromTime().getTime();
        return new SimpleStringProperty("Day: " + day + " At " + time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return active == this.active
                && Objects.equals(room, that.room)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(fromTime, that.fromTime)
                && Objects.equals(toTime, that.toTime);
    }

}
