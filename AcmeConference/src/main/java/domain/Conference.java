
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends DomainEntity {

	// Atributos
	private String				title;
	private String				acronym;
	private String				venue;
	private Date				submissionDeadline;
	private Date				notificationDeadline;
	private Date				cameraReadyDeadline;
	private Date				startDate;
	private Date				endDate;
	private String				summary;
	private Double				fee;
	private Boolean				isDraft;
	private Administrator		administrator;
	private Category			category;
	private Collection<Comment>	comments;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAcronym() {
		return this.acronym;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getVenue() {
		return this.venue;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getSubmissionDeadline() {
		return this.submissionDeadline;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getNotificationDeadline() {
		return this.notificationDeadline;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getCameraReadyDeadline() {
		return this.cameraReadyDeadline;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getEndDate() {
		return this.endDate;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSummary() {
		return this.summary;
	}

	@NotNull
	@Range(min = 0, max = 1)
	public Double getFee() {
		return this.fee;
	}

	public Boolean getIsDraft() {
		return this.isDraft;
	}

	@ManyToOne
	public Administrator getAdministrator() {
		return this.administrator;
	}

	@ManyToOne
	public Category getCategory() {
		return this.category;
	}

	@OneToMany
	public Collection<Comment> getComments() {
		return this.comments;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	public void setVenue(final String venue) {
		this.venue = venue;
	}

	public void setSubmissionDeadline(final Date submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}

	public void setNotificationDeadline(final Date notificationDeadline) {
		this.notificationDeadline = notificationDeadline;
	}

	public void setCameraReadyDeadline(final Date cameraReadyDeadline) {
		this.cameraReadyDeadline = cameraReadyDeadline;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

}
