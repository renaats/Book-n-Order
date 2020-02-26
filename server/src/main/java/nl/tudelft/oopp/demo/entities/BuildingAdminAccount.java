package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BuildingAdmin")
public class BuildingAdminAccount extends AdminAccount {
    @Column(name = "type", insertable = false, updatable = false)
    protected String type;

    public String getType() {
        return type;
    }
}
