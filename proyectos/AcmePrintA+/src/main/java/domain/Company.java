
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Attributes -------------------------------------------------------------
	private String	commercialName;
	private Finder	finder;


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

	@OneToOne
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	

	// Relationships ----------------------------------------------------------

	// Attributes --------------------------------------------------------------

	// Getters ----------------------------------------------------------------

	// Setters ----------------------------------------------------------------

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Company [ " + super.toString() + "commercialName=" + commercialName + ", finder="
				+ finder + "]";
	}
}
