package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Represents an allergy. Holds all necessary information about the allergy that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Dish as a foreign key.
 */
@Entity
public class Allergy {
    @Id
    private String allergyName;

    @JsonIgnore
    @ManyToMany(mappedBy = "allergies", fetch = FetchType.EAGER)
    private Set<Dish> dishes;

    /** Creates a new instance of Allergy.
     * @param allergyName the name of the allergy.
     */
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
