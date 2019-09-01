
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;



import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
// para indicar que se trata de una entidad persistente --import javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Sponsorship extends DomainEntity {

	//Atributos
	private String	banner;
	private String	targetURL;
	private Boolean isEnabled;


	// Constructor2 -- vacío por defecto
	public Sponsorship() {
		super();
	}

	// Getters
	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	
	@NotBlank
	@URL
	public String getTargetURL() {
		return this.targetURL;
	}

	@NotNull
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	// Setters
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetURL(final String targetURL) {
		this.targetURL = targetURL;
	}


	// Relaciones
	private CreditCard	creditCard;
	private Parade		parade;
	private Sponsor		sponsor;
	// Getters
	
	@ManyToOne(optional = false)
	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	@ManyToOne(optional=false)
	public Parade getParade() {
		return parade;
	}
	
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}
 
	// Setters
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setParade(Parade parade) {
		this.parade = parade;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
}
