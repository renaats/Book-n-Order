package nl.tudelft.oopp.demo.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Manages the BikeReservations object that is retrieved from the server
 */
public class BikeReservation {
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
