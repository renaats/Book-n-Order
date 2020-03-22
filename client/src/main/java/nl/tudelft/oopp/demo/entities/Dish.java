package nl.tudelft.oopp.demo.entities;

import java.util.Set;

/**
 * Manages the Dish object that is retrieved from the server
 */
public class Dish {

    private int id;
    private String name;
    private Menu menu;
    private Set<Allergy> allergies;

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

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
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
}
