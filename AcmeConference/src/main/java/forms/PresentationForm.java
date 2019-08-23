package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Comment;
import domain.Conference;
import domain.Submission;

public class PresentationForm {
	// Atributos
	private int id;
	private int version;
	private String title;
	private String speakers;
	private Date startMoment;
	private Integer duration;
	private String room;
	private String summary;
	private String attachments;
	private Collection<Comment> comments;
	private Conference conference;
	private Submission submission;

	// Getters

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpeakers() {
		return this.speakers;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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
	public String getSummary() {
		return this.summary;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}

	public Collection<Comment> getComments() {
		return this.comments;
	}

	@NotNull
	public Conference getConference() {
		return this.conference;
	}

	@NotNull
	public Submission getSubmission() {
		return submission;
	}

	// Setters

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

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

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

}
