package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import java.util.Set;

/**
 * Manages the Allergy object that is retrieved from the server.
 */
public class Allergy {
    private int id;
    private String allergyName;
    private Set<Dish> dishes;

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

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public int getId() {
        return id;
    }

    public Set<Dish> getDish() {
        return dishes;
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
