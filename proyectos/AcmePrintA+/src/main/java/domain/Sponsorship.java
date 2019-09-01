
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	//Atributos ---------------------------------------------------------------
	private String	banner;
	private String	targetPage;
	private Boolean	isEnabled;
	private Double	cost;


	//Constructor -------------------------------------------------------------
	public Sponsorship() {
		super();
	}

	//Getters -----------------------------------------------------------------
	
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

	@Range(min = 0, max = 99999)
	public Double getCost(){
		return this.cost;
	}
	
	//Setters -----------------------------------------------------------------

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public void setIsEnabled(final Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setCost(Double cost){
		this.cost = cost;
	}

	//Relaciones
	private Provider	provider;
	private Post	post;


	//Getters
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	@ManyToOne(optional = false)
	public Post getPost() {
		return this.post;
	}

	//Setters
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	public void setPost(final Post post) {
		this.post = post;
	}

	// toString ---------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Sponsorship [banner=" + banner + ", targetPage=" + targetPage
				+ ", isEnabled=" + isEnabled + ", cost=" + cost + ", provider="
				+ provider.getMake() + ", post=" + post + "]";
	}
}
