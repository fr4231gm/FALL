package domain;

import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Sponsor extends DomainEntity {

	// Atributos
	private CreditCard					creditcard;


	// Getters
	public CreditCard getCreditcard(){
		return this.creditcard;
	}



	// Setters
	public void setCreditcard(final CreditCard creditcard){
		this.creditcard = creditcard; 
	}

}