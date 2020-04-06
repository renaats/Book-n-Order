package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a room reservation. Holds all necessary information about the room reservation that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Room as a foreign key.
 * Contains AppUser as a foreign key.
 */
@Entity
public class RoomReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Room room;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser appUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;

    @Temporal(TemporalType.TIMESTAMP)
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

    public void setActive(boolean active) {
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

    public boolean isActive() {
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
