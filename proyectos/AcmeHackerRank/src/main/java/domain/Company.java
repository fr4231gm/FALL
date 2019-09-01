
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
// --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// --import javax.persistence.AccessType;
public class Company extends Actor {

	// Atributos --------------------------------------------------------------
	private String	commercialName;


	// Constructor ------------------------------------------------------------
	public Company() {
		super();
	}

	// Getters ----------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCommercialName() {
		return this.commercialName;
	}

	// Setters ----------------------------------------------------------------
	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	// Relaciones

}
