package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a restaurant. Holds all necessary information about the restaurant that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Building as a foreign key.
 * Contains Menu as a foreign key.
 */
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building building;
    private String name;
    private String email;

    private int feedbackCounter;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Menu menu;

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     */
    public Restaurant(Building building, String name, String email) {
        this.building = building;
        this.name = name;
        this.email = email;
    }

    public Restaurant() {

    }

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    Set<RestaurantHours> restaurantHours = new HashSet<>();

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getFeedbackCounter() {
        return feedbackCounter;
    }

    public void setFeedbackCounter(int feedbackCounter) {
        this.feedbackCounter = feedbackCounter;
    }

    /**
     * When the feedback is positive it adds 1 to the feedback counter score, when it's negative it subtracts one.
     * @param feedback if the feedback is positive or negative.
     */
    public void addFeedback(Boolean feedback) {
        if (feedback) {
            this.feedbackCounter = this.feedbackCounter + 1;
        } else {
            this.feedbackCounter = this.feedbackCounter - 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return name.equals(restaurant.name)
                && Objects.equals(email, restaurant.email)
                && Objects.equals(building, restaurant.building);
    }
}
