package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import java.util.Set;

/**
 * Represents an allergy. Holds all necessary information about the allergy that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Dish as a foreign key.
 */
public class Allergy {
    private int id;
    private String allergyName;
    private Set<Dish> dishes;
    private Set<Dish> dish;

    /**
     * Creates a new instance of Allergy.
     * @param allergyName the name of the allergy.
     */
    public Allergy(String allergyName) {
        this.allergyName = allergyName;
    }

    public Allergy() {

    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setDish(Set<Dish> dish) {
        this.dish = dish;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public int getId() {
        return id;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public Set<Dish> getDish() {
        return dish;
    }

    public String getAllergyName() {
        return allergyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Allergy allergy = (Allergy) o;
        return Objects.equals(allergyName, allergy.allergyName)
                && Objects.equals(dishes, allergy.dishes);
    }
}
