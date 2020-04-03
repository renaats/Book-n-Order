package nl.tudelft.oopp.demo.entities;

import java.util.Set;

/**
 * Manages the Dish object that is retrieved from the server.
 */
public class Dish {

    private int id;
    private String name;
    private int price;
    private String description;
    private String image;
    private Menu menu;
    private Set<Allergy> allergies;
    private Set<FoodOrder> foodOrders;

    /**
     * Creates a new instance of Dish.
     * @param name = name of the dish.
     * @param menu = menu to which dish is associated.
     */
    public Dish(String name, Menu menu, int price, String description, String image) {
        this.name = name;
        this.menu = menu;
        this.price = price;
        this.description = description;
        this.image = image;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }
    
    public void setFoodOrders(Set<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public Set<FoodOrder> getFoodOrders() {
        return foodOrders;
    }
}
