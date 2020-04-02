package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;
import java.util.Objects;

public class FoodOrder {
    private Integer id;
    private Restaurant restaurant;
    private AppUser appUser;
    private Building deliveryLocation;
    private Date deliveryTime;
    private Boolean feedback;
    private Date date;

    /** Creates a new instance of FoodOrder.
     * @param restaurant the restaurant at which the food order is placed.
     * @param appUser user who placed the food order.
     * @param deliveryLocation location of building where food needs to be delivered to/will be picked up from.
     * @param deliveryTime time at which food needs to be delivered.
     */
    public FoodOrder(Restaurant restaurant, AppUser appUser, Building deliveryLocation, Date deliveryTime, Date date) {
        this.restaurant = restaurant;
        this.appUser = appUser;
        this.deliveryLocation = deliveryLocation;
        this.deliveryTime = deliveryTime;
        this.date = date;
    }

    public FoodOrder() {

    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setDeliveryLocation(Building deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setFeedback(Boolean feedback) { this.feedback = feedback; }

    public Integer getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Building getDeliveryLocation() {
        return deliveryLocation;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public Boolean getFeedback() { return feedback; }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodOrder that = (FoodOrder) o;
        return Objects.equals(restaurant, that.restaurant)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(deliveryLocation, that.deliveryLocation)
                && Objects.equals(deliveryTime, that.deliveryTime);
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String name = getDeliveryLocation().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the restaurant name instead of the restaurant object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getRestaurantNameProperty() {
        String name = getRestaurant().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the date day instead of the date object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getDateProperty() {
        int day = getDeliveryTime().getDay();
        String dateString = ""+day;
        return new SimpleStringProperty(dateString);
    }
}
