package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity 
@Access(AccessType.PROPERTY) 
public class Quolet extends DomainEntity {

	// Atributos
	private String					ticker;
	private String					body;
	private Date					publicationMoment;
	private String					atributo1;
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
	public String getAtributo1(){
		return this.atributo1;
	}

	@NotNull
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

	public void setAtributo1(final String atributo1){
		this.atributo1 = atributo1; 
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