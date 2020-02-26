package nl.tudelft.oopp.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BuildingAdmin")
public class BuildingAdminAccount extends AdminAccount {

}
