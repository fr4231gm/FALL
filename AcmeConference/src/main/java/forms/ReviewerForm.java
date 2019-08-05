package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ReviewerForm extends ActorForm {

	// Atributos --------------------------------------------------------------
	private String					keywords;
	
	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeywords() {
		return this.keywords;
	}

	// Setters ----------------------------------------------------------------
	public void setKeywords(final String keywords) {
		this.keywords = keywords;
	}
}
