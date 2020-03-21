package nl.tudelft.oopp.demo.entities;

import java.time.LocalTime;
import java.util.Objects;

public class BuildingHours {
    private int id;
    private int day;

    private Building building;
    private LocalTime startTime;
    private LocalTime endTime;

    public BuildingHours(int day, Building building, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.building = building;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BuildingHours() {

    }

    public void setDay(int day) {
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

    public int getDay() {
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
