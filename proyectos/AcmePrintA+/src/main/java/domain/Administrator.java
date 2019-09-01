package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;


@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor{

	// Attributes -------------------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public Administrator() {
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
		return "Administrator [" + super.toString() + "]";
	}
	
}
