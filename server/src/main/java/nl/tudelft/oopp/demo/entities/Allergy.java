package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents an allergy. Holds all necessary information about the allergy that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Dish as a foreign key.
 */
@Entity
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Set<Dish> dishes;

    private String allergyName;

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

    public int getId() {
        return id;
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
        return allergyName == allergy.allergyName
                && Objects.equals(dishes, allergy.dishes);
    }
}
