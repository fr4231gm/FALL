package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.CreditCard;

public class SponsorForm extends ActorForm {

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
