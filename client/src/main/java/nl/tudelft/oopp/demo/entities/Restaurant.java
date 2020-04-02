package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {
    private int id;

    private Building building;

    private String name;

    private Menu menu;

    private int feedbackCounter = 0;

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     * @param menu = menu of the restaurant.
     */
    public Restaurant(Building building, String name, Menu menu) {
        this.building = building;
        this.name = name;
        this.menu = menu;
    }

    public Restaurant() {

    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setFeedbackCounter(int feedbackCounter) { this.feedbackCounter = feedbackCounter; }

    public Menu getMenu() {
        return menu;
    }

    public int getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public String getName() {
        return name;
    }

    public int getFeedbackCounter() { return feedbackCounter; }

    /**
     * When the feedback is positive it adds 1 to the feedback counter score, when it's negative it subtracts one
     * @param feedback if the feedback is positive or negative
     */
    public void addFeedback(Boolean feedback) {
        if (feedback) {
            this.feedbackCounter++;
        } else {
            this.feedbackCounter--;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return name.equals(restaurant.name)
                && Objects.equals(building, restaurant.building);
    }
}
