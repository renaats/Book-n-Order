package nl.tudelft.oopp.demo.entities;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a dish. Holds all necessary information about the dish that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Menu as a foreign key.
 */
public class Dish {
    private int id;
    private String name;
    private int price;
    private String description;
    private String image;
    private Menu menu;
    private Set<Allergy> allergies;
    private Set<DishOrder> dishOrders;
    private int amount = 0;

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

    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDishOrders(Set<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
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

    public String getPriceInEuros() {
        return new DecimalFormat("##.00").format((double) Math.round(price * 100) / 10000);
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

    public Menu getMenu() {
        return menu;
    }

    public int getAmount() {
        return amount;
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
        return Objects.equals(name, dish.name)
                && Objects.equals(menu, dish.menu)
                && Objects.equals(price, dish.price)
                && Objects.equals(description, dish.description)
                && Objects.equals(image, dish.image)
                && Objects.equals(allergies, dish.allergies)
                && Objects.equals(dishOrders, dish.dishOrders);
    }
}