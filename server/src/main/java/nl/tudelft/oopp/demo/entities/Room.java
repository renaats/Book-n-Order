package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity // This tells Hibernate to make a table out of this class
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Building building;

    private String faculty;
    private boolean facultySpecific;
    private boolean projector;
    private boolean screen;
    private int nrPeople;
    private int plugs;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setFacultySpecific(boolean facultySpecific) {
        this.facultySpecific = facultySpecific;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public void setScreen(boolean screen) {
        this.screen = screen;
    }

    public void setNrPeople(int nrPeople) {
        this.nrPeople = nrPeople;
    }

    public void setPlugs(int plugs) {
        this.plugs = plugs;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Building getBuilding() {
        return building;
    }

    public String getFaculty() {
        return faculty;
    }

    public boolean isFacultySpecific() {
        return facultySpecific;
    }

    public boolean isProjector() {
        return projector;
    }

    public boolean isScreen() {
        return screen;
    }

    public int getNrPeople() {
        return nrPeople;
    }

    public int getPlugs() {
        return plugs;
    }
}
