package nl.tudelft.oopp.demo.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a food order. Holds all necessary information about the food order that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Restaurant as a foreign key.
 * Contains AppUser as a foreign key.
 * Contains Building as a foreign key.
 */
@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Restaurant restaurant;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser appUser;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building deliveryLocation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryTime;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Dish> dishes;

    /** Creates a new instance of FoodOrder.
     * @param restaurant the restaurant at which the food order is placed.
     * @param appUser user who placed the food order.
     * @param deliveryLocation location of building where food needs to be delivered to/will be picked up from.
     * @param deliveryTime time at which food needs to be delivered.
     */
    public FoodOrder(Restaurant restaurant, AppUser appUser, Building deliveryLocation, Date deliveryTime) {
        this.restaurant = restaurant;
        this.appUser = appUser;
        this.deliveryLocation = deliveryLocation;
        this.deliveryTime = deliveryTime;
        this.active = true;
        dishes = new HashSet<>();
    }

    public FoodOrder() {
        this.active = true;
    }

    public void setActive(boolean active) {
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

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public Integer getId() {
        return id;
    }

    public boolean isActive() {
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
        return active == that.active
                && Objects.equals(restaurant, that.restaurant)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(deliveryLocation, that.deliveryLocation)
                && Objects.equals(deliveryTime, that.deliveryTime)
                && Objects.equals(dishes, that.dishes);
    }
}
