package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a dish order. Holds all necessary information about the dish order that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Dish as a foreign key.
 * Contains FoodOrder as a foreign key.
 */
@Entity
public class DishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Dish dish;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private FoodOrder foodOrder;

    private int amount;

    /** Creates a new instance of DishOrder.
     * @param dish the dish that is ordered.
     * @param foodOrder the food order that it represents.
     * @param amount the amount of these dishes in the food order.
     */
    public DishOrder(Dish dish, FoodOrder foodOrder, int amount) {
        this.dish = dish;
        this.foodOrder = foodOrder;
        this.amount = amount;
    }

    public DishOrder() {

    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Dish getDish() {
        return dish;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
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
        DishOrder that = (DishOrder) o;
        return amount == that.amount
                && Objects.equals(foodOrder, that.foodOrder)
                && Objects.equals(dish, that.dish);
    }
}
