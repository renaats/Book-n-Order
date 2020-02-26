package nl.tudelft.oopp.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("User")
public class UserAccount extends Account {

}
