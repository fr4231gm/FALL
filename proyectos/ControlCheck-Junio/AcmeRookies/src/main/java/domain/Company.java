
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
// --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// --import javax.persistence.AccessType;
public class Company extends Actor {

	// Atributos --------------------------------------------------------------
	private String	commercialName;
	private Double	score;


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

	@Range(min = 0, max = 1)
	public Double getScore() {
		return this.score;
	}

	// Setters ----------------------------------------------------------------
	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	// Relaciones

}
