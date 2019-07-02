package domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Report extends DomainEntity {

	// Atributos
	private String					decision;
	private Double					originalityscore;
	private Double					qualityscore;
	private Double					readabilityscore;
	private String					comments;
	private Boolean					notified;
	private Reviewer					reviewer;
	private Conference					conference;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getDecision(){
		return this.decision;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getOriginalityscore(){
		return this.originalityscore;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getQualityscore(){
		return this.qualityscore;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getReadabilityscore(){
		return this.readabilityscore;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getComments(){
		return this.comments;
	}

	@NotNull
	public Boolean getNotified(){
		return this.notified;
	}

	@ManyToOne
	public Reviewer getReviewer(){
		return this.reviewer;
	}

	@ManyToOne
	public Conference getConference(){
		return this.conference;
	}



	// Setters
	public void setDecision(final String decision){
		this.decision = decision; 
	}

	public void setOriginalityscore(final Double originalityscore){
		this.originalityscore = originalityscore; 
	}

	public void setQualityscore(final Double qualityscore){
		this.qualityscore = qualityscore; 
	}

	public void setReadabilityscore(final Double readabilityscore){
		this.readabilityscore = readabilityscore; 
	}

	public void setComments(final String comments){
		this.comments = comments; 
	}

	public void setNotified(final Boolean notified){
		this.notified = notified; 
	}

	public void setReviewer(final Reviewer reviewer){
		this.reviewer = reviewer; 
	}

	public void setConference(final Conference conference){
		this.conference = conference; 
	}

}