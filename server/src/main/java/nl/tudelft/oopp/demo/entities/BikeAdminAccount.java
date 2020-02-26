package nl.tudelft.oopp.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BikeAdmin")
public class BikeAdminAccount extends AdminAccount {

}
