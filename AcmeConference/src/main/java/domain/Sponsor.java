
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	// Atributos
	private CreditCard	creditCard;


	// Getters
	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	// Setters
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
