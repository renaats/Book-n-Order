package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity // This tells Hibernate to make a table out of this class
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building location;

    private boolean available;

    public void setLocation(Building location) {
        this.location = location;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
