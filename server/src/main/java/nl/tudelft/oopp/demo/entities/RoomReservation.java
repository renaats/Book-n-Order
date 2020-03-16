package nl.tudelft.oopp.demo.entities;

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
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Room room;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser appUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date toTime;


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
        return Objects.equals(room, that.room)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(fromTime, that.fromTime)
                && Objects.equals(toTime, that.toTime);
    }

}
