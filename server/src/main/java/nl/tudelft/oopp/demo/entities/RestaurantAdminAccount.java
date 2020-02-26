package nl.tudelft.oopp.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RestaurantAdmin")
public class RestaurantAdminAccount extends AdminAccount {

}
