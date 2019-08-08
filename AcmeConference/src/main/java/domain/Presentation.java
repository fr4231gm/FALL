package domain;

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
	@Valid
	public Paper getPaper() {
		return this.paper;
	}

	// Setters
	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

}