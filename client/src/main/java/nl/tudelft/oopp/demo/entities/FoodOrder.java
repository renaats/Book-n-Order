package nl.tudelft.oopp.demo.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the FoodOrder object that is retrieved from the server
 */
public class FoodOrder implements Comparable {
    private Integer id;
    private Boolean active;
    private Restaurant restaurant;
    private Menu menu;
    private AppUser appUser;
    private Building deliveryLocation;
    private Date deliveryTime;
    private boolean feedback;
    private boolean feedbackHasBeenGiven;
    private Set<DishOrder> dishOrders;

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
        if (getDeliveryLocation() == null || getDeliveryLocation().getName() == null || getDeliveryLocation().getName().equals("")) {
            return new SimpleStringProperty("Pick Up");
        }
        return new SimpleStringProperty(getDeliveryLocation().getName());
    }

    /**
     * Makes the table list the Delivery day instead of the Delivery Time object
     * @return String property, a property recognized by the tables.
     */
    public SimpleStringProperty getDeliveryTimeProperty() {
        Date time = getDeliveryTime();
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        return new SimpleStringProperty(df.format(time));
    }

    /**
     * Makes the table list the score of the restaurant.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getYourFeedbackProperty() {
        if (this.isFeedbackHasBeenGiven()) {
            if (getFeedback()) {
                return new SimpleStringProperty("Thumbs Up");
            } else {
                return new SimpleStringProperty("Thumbs Down");
            }
        } else {
            return new SimpleStringProperty("No Feedback");
        }
    }

    public void setFeedbackHasBeenGiven(boolean feedbackGiven) {
        this.feedbackHasBeenGiven = feedbackGiven;
    }

    public boolean isFeedbackHasBeenGiven() {
        return feedbackHasBeenGiven;
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

    public void setDishOrders(Set<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
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

    /**
     * Gets the delivery location name.
     * @return the name of the location.
     */
    public String getLocationNameString() {
        if (deliveryLocation == null || deliveryLocation.getName() == null || deliveryLocation.getName().equals("")) {
            return "Pick Up";
        }
        return deliveryLocation.getName();
    }

    public String getDeliveryTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(deliveryTime);
    }

    public Menu getMenu() {
        return menu;
    }

    public Set<DishOrder> getDishOrders() {
        return dishOrders;
    }

    @Override
    public int compareTo(Object o) {
        long compareTime = ((FoodOrder)o).getDeliveryTime().getTime();
        if (this.getDeliveryTime().getTime() > compareTime) {
            return 1;
        }
        return -1;
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
                && Objects.equals(dishOrders, that.dishOrders);
    }

    public boolean getFeedback() {
        return this.feedback;
    }

    public void setFeedback(boolean b) {
        this.feedback = b;
    }
}
