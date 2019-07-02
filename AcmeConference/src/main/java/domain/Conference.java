package domain;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Conference extends DomainEntity {

	// Atributos
	private String					title;
	private String					acronym;
	private String					venue;
	private Date					submissiondeadline;
	private Date					notificationdeadline;
	private Date					camerareadydeadline;
	private Date					startdate;
	private Date					enddate;
	private String					summary;
	private Double					fee;
	private Boolean					isdraft;
	private Administrator					administrator;
	private Category					category;
	private Collection<Comment>					comments;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTitle(){
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getAcronym(){
		return this.acronym;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getVenue(){
		return this.venue;
	}

	@NotNull
	public Date getSubmissiondeadline(){
		return this.submissiondeadline;
	}

	@NotNull
	public Date getNotificationdeadline(){
		return this.notificationdeadline;
	}

	@NotNull
	public Date getCamerareadydeadline(){
		return this.camerareadydeadline;
	}

	@NotNull
	public Date getStartdate(){
		return this.startdate;
	}

	@NotNull
	public Date getEnddate(){
		return this.enddate;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getSummary(){
		return this.summary;
	}

	@NotNull
	@Range(min = 0, max = 1) 
	public Double getFee(){
		return this.fee;
	}

	@NotNull
	public Boolean getIsdraft(){
		return this.isdraft;
	}

	@ManyToOne
	public Administrator getAdministrator(){
		return this.administrator;
	}

	@ManyToOne
	public Category getCategory(){
		return this.category;
	}

	@OneToMany
	public Collection<Comment> getComments(){
		return this.comments;
	}



	// Setters
	public void setTitle(final String title){
		this.title = title; 
	}

	public void setAcronym(final String acronym){
		this.acronym = acronym; 
	}

	public void setVenue(final String venue){
		this.venue = venue; 
	}

	public void setSubmissiondeadline(final Date submissiondeadline){
		this.submissiondeadline = submissiondeadline; 
	}

	public void setNotificationdeadline(final Date notificationdeadline){
		this.notificationdeadline = notificationdeadline; 
	}

	public void setCamerareadydeadline(final Date camerareadydeadline){
		this.camerareadydeadline = camerareadydeadline; 
	}

	public void setStartdate(final Date startdate){
		this.startdate = startdate; 
	}

	public void setEnddate(final Date enddate){
		this.enddate = enddate; 
	}

	public void setSummary(final String summary){
		this.summary = summary; 
	}

	public void setFee(final Double fee){
		this.fee = fee; 
	}

	public void setIsdraft(final Boolean isdraft){
		this.isdraft = isdraft; 
	}

	public void setAdministrator(final Administrator administrator){
		this.administrator = administrator; 
	}

	public void setCategory(final Category category){
		this.category = category; 
	}

	public void setComments(final Collection<Comment> comments){
		this.comments = comments; 
	}

}