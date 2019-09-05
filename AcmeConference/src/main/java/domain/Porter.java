package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity 
@Access(AccessType.PROPERTY) 
public class Porter extends DomainEntity {

	// Atributos
	private String					ticker;
	private String					body;
	private Date					publicationMoment;
	private String					picture;
	private Boolean					isDraft;
	private Administrator			administrator;
	private Conference				conference;
	private String					title;


	// Getters
	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTicker(){
		return this.ticker;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getBody(){
		return this.body;
	}

	@Past
	public Date getPublicationMoment(){
		return this.publicationMoment;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	@URL
	public String getPicture(){
		return this.picture;
	}

	public Boolean getIsDraft(){
		return this.isDraft;
	}

	@ManyToOne
	public Administrator getAdministrator(){
		return this.administrator;
	}

	@ManyToOne
	public Conference getConference(){
		return this.conference;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	@Size(min = 1, max = 251)
	public String getTitle(){
		return this.title;
	}



	// Setters
	public void setTicker(final String ticker){
		this.ticker = ticker; 
	}

	public void setBody(final String body){
		this.body = body; 
	}

	public void setPublicationMoment(final Date publicationMoment){
		this.publicationMoment = publicationMoment; 
	}

	public void setPicture(final String picture){
		this.picture = picture; 
	}

	public void setIsDraft(final Boolean isDraft){
		this.isDraft = isDraft; 
	}

	public void setAdministrator(final Administrator administrator){
		this.administrator = administrator; 
	}

	public void setConference(final Conference conference){
		this.conference = conference; 
	}

	public void setTitle(final String title){
		this.title = title; 
	}

}