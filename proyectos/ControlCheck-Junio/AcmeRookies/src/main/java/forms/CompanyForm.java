package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class CompanyForm extends ActorForm {

	// Atributos --------------------------------------------------------------
	private String commercialName;

	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCommercialName() {
		return commercialName;
	}

	// Setters ----------------------------------------------------------------
	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}
}
