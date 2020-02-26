package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RestaurantAdmin")
public class RestaurantAdminAccount extends AdminAccount {
    @Column(name = "type", insertable = false, updatable = false)
    protected String type;

    public String getType() {
        return type;
    }
}
