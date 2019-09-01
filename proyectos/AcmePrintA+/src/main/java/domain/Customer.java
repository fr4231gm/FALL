package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;


@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor{


	// Attributes -------------------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public Customer() {
		super();
	}

	
		
	// Getters ----------------------------------------------------------------
	
	// Setters ----------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
	// Attributes --------------------------------------------------------------
	
	// Getters ----------------------------------------------------------------
	
	// Setters ----------------------------------------------------------------
	
	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Customer [" + super.toString() + "]";
	}
}
