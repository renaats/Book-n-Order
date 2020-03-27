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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Menu menu;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    Set<RestaurantHours> restaurantHours = new HashSet<>();

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     */
    public Restaurant(Building building, String name) {
        this.building = building;
        this.name = name;
    }

    /**
     * Creates a new instance of Restaurant, with menu and restaurant hours.
     * @param building Building in which restaurant is located.
     * @param name Name of the restaurant.
     * @param restaurantHours Hours of the restaurant.
     * @param menu Menu of the restaurant.
     */
    public Restaurant(Building building, String name, Set<RestaurantHours> restaurantHours, Menu menu) {
        this.building = building;
        this.menu = menu;
        this.name = name;
        this.restaurantHours = restaurantHours;
    }

    public Restaurant() {

    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setRestaurantHours(Set<RestaurantHours> restaurantHours) {
        this.restaurantHours = restaurantHours;
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

    public Menu getMenu() {
        return menu;
    }

    public Set<RestaurantHours> getRestaurantHours() {
        return restaurantHours;
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
                && Objects.equals(building, restaurant.building);
    }
}
