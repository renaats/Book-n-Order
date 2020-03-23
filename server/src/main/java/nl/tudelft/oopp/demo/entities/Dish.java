package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a dish. Holds all necessary information about the dish that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Menu as a foreign key.
 */
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Menu menu;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Allergy> allergies;

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes", fetch = FetchType.EAGER)
    private Set<FoodOrder> foodOrders;

    /**
     * Creates a new instance of Dish.
     * @param name = name of the dish.
     * @param menu = menu to which dish is associated.
     */
    public Dish(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public Dish() {

    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    public void setFoodOrders(Set<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }
    
    public Set<FoodOrder> getFoodOrders() {
        return foodOrders;
    }
    
    public Menu getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return Objects.equals(name, dish.name)
                && Objects.equals(menu, dish.menu)
                && Objects.equals(allergies, dish.allergies)
                && Objects.equals(foodOrders, dish.foodOrders);
    }
}