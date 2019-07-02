package domain;

import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Sponsorship extends DomainEntity {

	// Atributos
	private String					banner;
	private String					targeturl;
	private Sponsor					sponsor;


	// Getters
	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getBanner(){
		return this.banner;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTargeturl(){
		return this.targeturl;
	}

	@ManyToOne
	public Sponsor getSponsor(){
		return this.sponsor;
	}



	// Setters
	public void setBanner(final String banner){
		this.banner = banner; 
	}

	public void setTargeturl(final String targeturl){
		this.targeturl = targeturl; 
	}

	public void setSponsor(final Sponsor sponsor){
		this.sponsor = sponsor; 
	}

}