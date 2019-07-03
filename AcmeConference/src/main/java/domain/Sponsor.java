
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
	private CreditCard	creditcard;


	// Getters
	@Valid
	@NotNull
	public CreditCard getCreditcard() {
		return this.creditcard;
	}

	// Setters
	public void setCreditcard(final CreditCard creditcard) {
		this.creditcard = creditcard;
	}

}
