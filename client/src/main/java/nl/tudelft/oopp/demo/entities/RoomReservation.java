package nl.tudelft.oopp.demo.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the RoomReservation object that is retrieved from the server
 */
public class RoomReservation implements Comparable {

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
     * Makes the table list the name of the building instead of the building object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String room = getRoom().getBuilding().getName();
        return new SimpleStringProperty(room);
    }

    /**
     * Makes the table list the name of the room instead of the room object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getRoomNameProperty() {
        String room = getRoom().getName();
        return new SimpleStringProperty(room);
    }

    /**
     * Makes the table list the time in a readable form instead of the time object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getToTimeProperty() {
        Date time = getToTime();
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        return new SimpleStringProperty(df.format(time));
    }

    /**
     * Makes the table list the time in a readable form instead of the time object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getFromTimeProperty() {
        Date time = getFromTime();
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        return new SimpleStringProperty(df.format(time));
    }

    @Override
    public int compareTo(Object o) {
        long compareTime = ((RoomReservation)o).getFromTime().getTime();
        if (this.getFromTime().getTime() > compareTime) {
            return 1;
        }
        return -1;
    }

    public String getFromTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(fromTime);
    }

    public String getToTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(toTime);
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
        return active == that.active
                && Objects.equals(room, that.room)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(fromTime, that.fromTime)
                && Objects.equals(toTime, that.toTime);
    }

}
