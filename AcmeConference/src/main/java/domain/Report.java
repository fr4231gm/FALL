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
	private Double					originalityScore;
	private Double					qualityScore;
	private Double					readabilityScore;
	private String					comments;
	private Reviewer				reviewer;
	private Submission				submission;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getDecision(){
		return this.decision;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getOriginalityScore(){
		return this.originalityScore;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getQualityScore(){
		return this.qualityScore;
	}

	@Range(min = 0, max = 10) 
	@NotNull
	public Double getReadabilityScore(){
		return this.readabilityScore;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getComments(){
		return this.comments;
	}

	@ManyToOne
	public Reviewer getReviewer(){
		return this.reviewer;
	}

	@ManyToOne
	public Submission getSubmission(){
		return this.submission;
	}

	// Setters
	public void setDecision(final String decision){
		this.decision = decision; 
	}

	public void setOriginalityScore(final Double originalityScore){
		this.originalityScore = originalityScore; 
	}

	public void setQualityScore(final Double qualityScore){
		this.qualityScore = qualityScore; 
	}

	public void setReadabilityScore(final Double readabilityScore){
		this.readabilityScore = readabilityScore; 
	}

	public void setComments(final String comments){
		this.comments = comments; 
	}

	public void setReviewer(final Reviewer reviewer){
		this.reviewer = reviewer; 
	}

	public void setSubmission(final Submission submission){
		this.submission = submission; 
	}

}