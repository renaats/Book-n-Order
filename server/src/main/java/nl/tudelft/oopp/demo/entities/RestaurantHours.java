package nl.tudelft.oopp.demo.entities;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents working hours for a restaurant. Holds all necessary information about the restaurant hours that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains an instance of Restaurant as a foreign key.
 */
@Entity
public class RestaurantHours {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long day;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Restaurant restaurant;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Creates a new instance of RestaurantHours.
     * @param day = the day of the week in number representation (1 to 7).
     * @param restaurant = the restaurant.
     * @param startTime = the starting time.
     * @param endTime = the ending time.
     */
    public RestaurantHours(long day, Restaurant restaurant, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.restaurant = restaurant;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public RestaurantHours() {

    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public long getDay() {
        return day;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantHours that = (RestaurantHours) o;
        return day == that.day
                && Objects.equals(restaurant, that.restaurant)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }
}
