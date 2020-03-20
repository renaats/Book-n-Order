package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Allergy allergy;

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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllergy(Allergy allergy) {
        this.allergy = allergy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Allergy getAllergy() {
        return allergy;
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
        return name.equals(name)
                && Objects.equals(menu, dish.menu);
    }
}
