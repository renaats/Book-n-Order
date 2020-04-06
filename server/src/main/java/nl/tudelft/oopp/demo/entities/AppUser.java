package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Represents a user account. Holds all necessary information about the user that is then stored in the database.
 * Is uniquely identified by its email.
 * Contains the password encoded with BCryptPasswordEncoder.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class AppUser {
    @Expose
    @Id
    private String email;
    private String password;
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String faculty;
    @Expose
    private String study;
    private boolean loggedIn = false;
    private int confirmationNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /** Creates a new instance of AppUser.
     * @param email the user's email address.
     * @param password the user's password.
     * @param name the user's first name.
     * @param surname the user's surname.
     * @param faculty the faculty the user is associated to.
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

    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private Set<RoomReservation> roomReservations = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private Set<BikeReservation> bikeReservations = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private Set<FoodOrder> foodOrders = new HashSet<>();

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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoomReservations(Set<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public void setBikeReservations(Set<BikeReservation> bikeReservations) {
        this.bikeReservations = bikeReservations;
    }
    
    public void setFoodOrder(Set<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public Set<BikeReservation> getBikeReservations() {
        return bikeReservations;
    }
    
    public Set<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
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
                && Objects.equals(name, appUser.name)
                && Objects.equals(surname, appUser.surname)
                && Objects.equals(faculty, appUser.faculty)
                && Objects.equals(study, appUser.study)
                && Objects.equals(roles, appUser.roles)
                && Objects.equals(roomReservations, appUser.roomReservations)
                && Objects.equals(foodOrders, appUser.foodOrders);
    }
}
