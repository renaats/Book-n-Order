package nl.tudelft.oopp.demo.entities;

/**
 * Creates and maneges an object for the current dishes in the database when in client side
 */
public class Dish {

    private int id;
    private String name;

    private Menu menu;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
