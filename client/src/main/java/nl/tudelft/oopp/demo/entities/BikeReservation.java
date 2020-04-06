package nl.tudelft.oopp.demo.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the BikeReservations object that is retrieved from the server
 */
public class BikeReservation implements Comparable {
    private Integer id;
    private Boolean active;
    private Bike bike;
    private AppUser appUser;
    private Building fromBuilding;
    private Building toBuilding;
    private Date fromTime;
    private Date toTime;

    /** Creates a new instance of BikeReservation.
     * @param bike the bike associated to the reservation.
     * @param appUser user who made the bike reservation.
     * @param fromBuilding location of building where the bike will depart.
     * @param toBuilding location of the building where the bike will go to.
     * @param fromTime time at which reservation will start.
     * @param toTime time at which reservation will end.
     */
    public BikeReservation(Bike bike, AppUser appUser, Building fromBuilding, Building toBuilding, Date fromTime, Date toTime) {
        this.bike = bike;
        this.appUser = appUser;
        this.fromBuilding = fromBuilding;
        this.toBuilding = toBuilding;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = true;
    }

    public BikeReservation() {
        this.active = true;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setFromBuilding(Building fromBuilding) {
        this.fromBuilding = fromBuilding;
    }

    public void setToBuilding(Building toBuilding) {
        this.toBuilding = toBuilding;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public Bike getBike() {
        return bike;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Building getFromBuilding() {
        return fromBuilding;
    }

    public Building getToBuilding() {
        return toBuilding;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    /**
     * Makes the table list the id of the bike instead of the bike object
     * @return String property, a property recognized by the tables.
     */
    public IntegerProperty getBikeIdProperty() {
        int id = getBike().getId();
        return new SimpleIntegerProperty(id);
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getFromBuildingNameProperty() {
        String name = getFromBuilding().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getToBuildingNameProperty() {
        String name = getToBuilding().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the date in a readable way instead of the time object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getFromTimeProperty() {
        Date time = getFromTime();
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        return new SimpleStringProperty(df.format(time));
    }

    /**
     * Makes the table list the date in a readable way instead of the time object.
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getToTimeProperty() {
        Date time = getToTime();
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        return new SimpleStringProperty(df.format(time));
    }

    @Override
    public int compareTo(Object o) {
        long compareTime = ((BikeReservation)o).getFromTime().getTime();
        if (this.getFromTime().getTime() > compareTime) {
            return 1;
        }
        return -1;
    }
    
    public String getFromTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(fromTime);
    }

    public String getToTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(toTime);
    }

    /**
     * Returns the building name as a string.
     * @return the building name string.
     */
    public String getFromBuildingName() {
        if (fromBuilding == null) {
            return "";
        }
        return fromBuilding.getName();
    }

    /**
     * Returns the building name as a string.
     * @return the building name string.
     */
    public String getToBuildingName() {
        if (toBuilding == null) {
            return "";
        }
        return toBuilding.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BikeReservation that = (BikeReservation) o;
        return active == that.active
                && Objects.equals(bike, that.bike)
                && Objects.equals(appUser, that.appUser)
                && Objects.equals(fromBuilding, that.fromBuilding)
                && Objects.equals(toBuilding, that.toBuilding)
                && Objects.equals(fromTime, that.fromTime)
                && Objects.equals(toTime, that.toTime);
    }
}
