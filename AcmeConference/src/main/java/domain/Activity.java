package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {

	// Atributos
	private String title;
	private String speakers;
	private Date startMoment;
	private Integer duration;
	private String room;
	private String summary;
	private String attachments;
	private Collection<Comment> comments;
	private Conference conference;

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Column(name="titleActivity")
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpeakers() {
		return this.speakers;
	}

	@NotNull
	public Date getStartMoment() {
		return this.startMoment;
	}

	@Range(min = 0)
	public Integer getDuration() {
		return this.duration;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getRoom() {
		return this.room;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Column(name = "summaryActivity")
	public String getSummary() {
		return this.summary;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}

	@OneToMany
	public Collection<Comment> getComments() {
		return this.comments;
	}

	@OneToOne
	public Conference getConference() {
		return this.conference;
	}

	// Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setSpeakers(final String speakers) {
		this.speakers = speakers;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	public void setRoom(final String room) {
		this.room = room;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

}