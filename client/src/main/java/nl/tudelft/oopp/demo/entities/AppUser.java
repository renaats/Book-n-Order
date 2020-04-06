package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Creates and manages an object for the current user of the application
 */
public class AppUser {
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    private String faculty;
    private String study;
    @JsonIgnore
    private boolean loggedIn;
    @JsonIgnore
    private int confirmationNumber;

    private Set<Role> roles;

    /**
     * Constructs an AppUser entity.
     * @param email the user's email.
     * @param password the user's password.
     * @param name the user's first name.
     * @param surname the user's last name.
     * @param faculty the faculty the user belongs to.
     * @param study the study of the user.
     */
    public AppUser(String email, String password, String name, String surname, String faculty, String study) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.study = study;
    }

    public AppUser() {

    }

    Set<FoodOrder> foodOrders = new HashSet<>();
    Set<RoomReservation> roomReservations = new HashSet<>();
    Set<BikeReservation> bikeReservations = new HashSet<>();

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoomReservations(Set<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public void setFoodOrders(Set<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public void setBikeReservations(Set<BikeReservation> bikeReservations) {
        this.bikeReservations = bikeReservations;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getStudy() {
        return study;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public Set<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public Set<BikeReservation> getBikeReservations() {
        return bikeReservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppUser appUser = (AppUser) o;
        return email.equals(appUser.email)
                && Objects.equals(password, appUser.password)
                && Objects.equals(name, appUser.name)
                && Objects.equals(surname, appUser.surname)
                && Objects.equals(faculty, appUser.faculty)
                && Objects.equals(study, appUser.study)
                && Objects.equals(roomReservations, appUser.roomReservations);
    }
}
