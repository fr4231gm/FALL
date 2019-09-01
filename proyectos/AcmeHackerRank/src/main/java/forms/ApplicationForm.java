
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Curricula;
import domain.DomainEntity;
import domain.Position;

public class ApplicationForm extends DomainEntity {

	// Attributes

	private Date		creationMoment;
	private String		answer;
	private String		linkCode;
	private String		status;
	private Date		submittedMoment;
	private Curricula	curricula;
	private Position	position;


	// Getters

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public String getAnswer() {
		return this.answer;
	}

	@URL
	public String getLinkCode() {
		return this.linkCode;
	}

	@Pattern(regexp = "^PENDING|SUBMITTED|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmittedMoment() {
		return this.submittedMoment;
	}

	public Curricula getCurricula() {
		return this.curricula;
	}

	public Position getPosition() {
		return this.position;
	}

	// Setters

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public void setLinkCode(final String linkCode) {
		this.linkCode = linkCode;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setSubmittedMoment(final Date submittedMoment) {
		this.submittedMoment = submittedMoment;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}
}
