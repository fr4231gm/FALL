package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Author;
import domain.Conference;
import domain.DomainEntity;
import domain.Paper;
import domain.Reviewer;

public class SubmissionForm extends DomainEntity {
	
	//Atributos ---------------------------------------------------------------
	private String		ticker;
	private Date		moment;
	private String		status;
	private Paper		paper;
	private Conference	conference;
	private Author		author;
	private Collection<Reviewer> reviewers;
	
	
	// Getters
		@NotBlank
		@Column(unique = true)
		@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
		public String getTicker() {
			return this.ticker;
		}

		@Temporal(TemporalType.TIMESTAMP)
		@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
		@Past
		public Date getMoment() {
			return this.moment;
		}

		@NotBlank
		@Pattern(regexp = "^UNDER-REVIEW|REJECTED|ACCEPTED")
		public String getStatus() {
			return this.status;
		}

		@Valid
		@NotNull
		public Paper getPaper() {
			return this.paper;
		}

		@OneToOne
		public Conference getConference() {
			return this.conference;
		}
		
		@ManyToMany
		public Collection<Reviewer> getReviewers(){
			return this.reviewers;
		}
		
		// Setters
		public void setTicker(final String ticker) {
			this.ticker = ticker;
		}

		public void setMoment(final Date moment) {
			this.moment = moment;
		}

		public void setStatus(final String status) {
			this.status = status;
		}
		
		public void setPaper(final Paper p) {
			this.paper = p;
		}

		public void setConference(final Conference conference) {
			this.conference = conference;
		}
		
		public void setReviewers(final Collection<Reviewer> reviewers) {
			this.reviewers = reviewers;
		}
		
		public void setAuthor(final Author author) {
			this.author = author;
		}

		@ManyToOne
		public Author getAuthor() {
			return this.author;
		}

}
