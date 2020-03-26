package nl.tudelft.oopp.demo.entities;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Manages the Building hours object that is retrieved from the server
 */
public class BuildingHours {
    private int id;
    private long day;

    private Building building;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Constructor for building hours.
     * @param day = the day of the week in number representation (1 to 7).
     * @param building the building.
     * @param startTime = the starting time.
     * @param endTime = the ending time.
     */
    public BuildingHours(long day, Building building, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.building = building;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BuildingHours() {

    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public long getDay() {
        return day;
    }

    public Building getBuilding() {
        return building;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BuildingHours that = (BuildingHours) o;
        return day == that.day
                && Objects.equals(building, that.building)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }
}
