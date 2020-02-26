package nl.tudelft.oopp.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Staff")
public class StaffAccount extends UserAccount {

}
