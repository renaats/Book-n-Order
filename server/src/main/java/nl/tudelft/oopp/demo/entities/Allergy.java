package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    /*@JsonIgnore
    @OneToMany(mappedBy = "dish")
    Set<Dish> dish = new HashSet<>();*/

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

    public int getId() {
        return id;
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
        return allergyName == allergy.allergyName;
    }
}
