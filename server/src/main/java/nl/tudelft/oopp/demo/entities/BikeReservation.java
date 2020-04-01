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
 * Represents a bike reservation. Holds all necessary information about the bike reservation that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Bike as a foreign key.
 * Contains AppUser as a foreign key.
 * Contains 2 Buildings as foreign keys.
 */
@Entity
public class BikeReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Bike bike;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser appUser;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building fromBuilding;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building toBuilding;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date toTime;

    /** Creates a new instance of BikeReservation.
     * @param bike the bike associated to the reservation.
     * @param appUser user who made the bike reservation.
     * @param fromBuilding location of building where the bike will depart.
     * @param toBuilding location of the building where the bike will go to.
     * @param fromTime time at which reservation will start.
     * @param toTime time at which reservation will end.
     */
    public BikeReservation(Bike bike, AppUser appUser, Building fromBuilding, Building toBuilding, Date fromTime, Date toTime) {
        this.bike = bike;
        this.appUser = appUser;
        this.fromBuilding = fromBuilding;
        this.toBuilding = toBuilding;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = true;
    }

    public BikeReservation() {
        this.active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setFromBuilding(Building fromBuilding) {
        this.fromBuilding = fromBuilding;
    }

    public void setToBuilding(Building toBuilding) {
        this.toBuilding = toBuilding;
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

    public Bike getBike() {
        return bike;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Building getFromBuilding() {
        return fromBuilding;
    }

    public Building getToBuilding() {
        return toBuilding;
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
        BikeReservation that = (BikeReservation) o;
        return active == that.active
                && Objects.equals(bike, that.bike)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(fromBuilding, that.fromBuilding)
                && Objects.equals(toBuilding, that.toBuilding)
                && Objects.equals(fromTime, that.fromTime)
                && Objects.equals(toTime, that.toTime);
    }
}