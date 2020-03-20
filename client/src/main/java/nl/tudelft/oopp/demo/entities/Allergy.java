package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Allergy {

    private int id;
    private Dish dish;
    private String allergyName;

    /**
     * Creates a new instance of Allergy.
     *
     * @param dish the dish the allergy is associated to.
     * @param allergyName the name of the allergy.
     */
    public Allergy(Dish dish, String allergyName) {
        this.dish = dish;
        this.allergyName = allergyName;
    }

    public Allergy() {

    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public int getId() {
        return id;
    }

    public Dish getDish() {
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
        return allergyName == allergy.allergyName
                && Objects.equals(dish, allergy.dish);
    }
}
