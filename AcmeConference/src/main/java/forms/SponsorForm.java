package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.CreditCard;

public class SponsorForm extends ActorForm {

	// Atributos --------------------------------------------------------------
	private CreditCard creditCard;

	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public CreditCard getCreditCard() {
		return creditCard;
	}

	// Setters ----------------------------------------------------------------
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
