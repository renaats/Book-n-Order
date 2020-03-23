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
 * Represents a bike. Holds all necessary information about the bike that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Building as a foreign key.
 */
@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building location;

    private boolean available;

    /** Creates a new instance of Bike.
     * @param location the location of the building where the bike is located.
     * @param available whether the bike is available for renting.
     */
    public Bike(Building location, boolean available) {
        this.location = location;
        this.available = available;
    }

    public Bike() {

    }

    @JsonIgnore
    @OneToMany(mappedBy = "bike")
    Set<BikeReservation> bikeReservations = new HashSet<>();

    public void setLocation(Building location) {
        this.location = location;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBikeReservations(Set<BikeReservation> bikeReservations) {
        this.bikeReservations = bikeReservations;
    }

    public int getId() {
        return id;
    }

    public Building getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return available;
    }

    public Set<BikeReservation> getBikeReservations() {
        return bikeReservations;
    }

    /**
     * Checks if this bike has a reservation between the specified times.
     * @param fromTime = the starting time.
     * @param toTime = the ending time.
     * @return true if this bike has a reservation between these times, false otherwise.
     */
    public boolean hasBikeReservationBetween(Date fromTime, Date toTime) {
        if (fromTime.compareTo(toTime) > 0) {
            return true;
        }
        for (BikeReservation bikeReservation: bikeReservations) {
            if (fromTime.compareTo(bikeReservation.getFromTime()) <= 0 && toTime.compareTo(bikeReservation.getFromTime()) > 0) {
                return true;
            }
            if (fromTime.compareTo(bikeReservation.getToTime()) < 0 && toTime.compareTo(bikeReservation.getToTime()) >= 0) {
                return true;
            }
            if (fromTime.compareTo(bikeReservation.getFromTime()) >= 0 && fromTime.compareTo(bikeReservation.getToTime()) < 0) {
                return true;
            }
            if (toTime.compareTo(bikeReservation.getFromTime()) > 0 && toTime.compareTo(bikeReservation.getToTime()) <= 0) {
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
        Bike bike = (Bike) o;
        return available == bike.available
                && Objects.equals(location, bike.location);
    }
}
