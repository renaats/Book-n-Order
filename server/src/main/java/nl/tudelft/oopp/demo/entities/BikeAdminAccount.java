package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BikeAdmin")
public class BikeAdminAccount extends AdminAccount {
    @Column(name = "type", insertable = false, updatable = false)
    protected String type;

    public String getType() {
        return type;
    }
}
