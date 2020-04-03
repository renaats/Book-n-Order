package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import java.util.Set;

/**
 * Represents an allergy. Holds all necessary information about the allergy that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Dish as a foreign key.
 */
public class Allergy {
    private String allergyName;
    private Set<Dish> dishes;
    public Allergy(String allergyName) {
        this.allergyName = allergyName;
    }

    public Allergy() {

    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public void setDish(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public Set<Dish> getDish() {
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
        Allergy allergy = (Allergy) o;
        return Objects.equals(allergyName, allergy.allergyName)
                && Objects.equals(dishes, allergy.dishes);
    }
}
