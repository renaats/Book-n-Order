package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.*;

import java.awt.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Manages the FoodOrder object that is retrieved from the server
 */
public class FoodOrder {
    private Integer id;
    private Boolean active;
    private Restaurant restaurant;
    private Menu menu;
    private AppUser appUser;
    private Building deliveryLocation;
    private Date deliveryTime;
    private Set<Dish> dishes;
    private boolean feedback;

    /** Creates a new instance of FoodOrder.
     * @param restaurant the restaurant at which the food order is placed.
     * @param appUser user who placed the food order.
     * @param deliveryLocation location of building where food needs to be delivered to/will be picked up from.
     * @param deliveryTime time at which food needs to be delivered.
     */
    public FoodOrder(Restaurant restaurant, AppUser appUser, Building deliveryLocation, Date deliveryTime, Menu menu) {
        this.restaurant = restaurant;
        this.appUser = appUser;
        this.deliveryLocation = deliveryLocation;
        this.deliveryTime = deliveryTime;
        this.menu = menu;
        this.active = true;
        this.feedback = true;
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
     * Makes the table list the Delivery Location name instead of the Delivery Location object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getDeliveryLocationNameProperty() {
        String name = getDeliveryLocation().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the Delivery day instead of the Delivery Time object
     * @return String property, a property recognized by the tables.
     */
    public IntegerProperty getDeliveryDayProperty() {
        int day = getDeliveryTime().getDay();
        return new SimpleIntegerProperty(day);
    }

    /**
     * Makes the table list the Delivery day instead of the Delivery Time object
     * @return String property, a property recognized by the tables.
     */
    public LongProperty getDeliveryTimeProperty() {
        long time = getDeliveryTime().getTime();
        return new SimpleLongProperty(time);
    }

    /**
     * Makes the table list the score of the restaurant.
     * @return String property, a property recognized by the tables.
     */
    public IntegerProperty getRestaurantFeedbackProperty() {
        int feedback = getRestaurant().getFeedbackCounter();
        return new SimpleIntegerProperty(feedback);
    }



    public FoodOrder() {
        this.active = true;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
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

    public Menu getMenu() {
        return menu;
    }

    public Set<Dish> getDishes() {
        return dishes;
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
                && Objects.equals(deliveryTime, that.deliveryTime)
                && Objects.equals(menu, that.menu)
                && Objects.equals(dishes, that.dishes);
    }

    public boolean getFeedback() {
        return this.feedback;
    }
}
