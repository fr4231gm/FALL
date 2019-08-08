package domain;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {

	// Atributos
	private Paper paper;

	// Getters
	@NotBlank
	@Valid
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public Paper getPaper() {
		return this.paper;
	}

	// Setters
	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

}