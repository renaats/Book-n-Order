package nl.tudelft.oopp.demo.entities;

import java.time.LocalTime;
import java.util.Objects;

public class RestaurantHours {
    private int id;
    private int day;
    private Restaurant restaurant;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Creates a new instance of RestaurantHours.
     * @param day = the day of the week in number representation (1 to 7)
     * @param restaurant = the restaurant
     * @param startTime = the starting time
     * @param endTime = the ending time
     */
    public RestaurantHours(int day, Restaurant restaurant, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.restaurant = restaurant;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public RestaurantHours() {

    }

    public void setDay(int day) {
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

    public int getDay() {
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
