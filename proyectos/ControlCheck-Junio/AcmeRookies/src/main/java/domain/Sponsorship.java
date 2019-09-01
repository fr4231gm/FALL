
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	//Atributos --------------------------------------------------------------------------
	private String	banner;
	private String	targetPage;
	private Boolean	isEnabled;


	//Constructor ------------------------------------------------------------------------
	public Sponsorship() {
		super();
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@URL
	public String getTargetPage() {
		return this.targetPage;
	}

	@NotNull
	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	//Setters

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public void setIsEnabled(final Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	//Relaciones
	private Provider	provider;
	private Position	position;


	//Getters
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	//Setters
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}
}
