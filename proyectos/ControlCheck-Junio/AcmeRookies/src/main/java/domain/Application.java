
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
// para indicar que se trata de una entidad persistente --import
// javax.persistence.Entity;
@Access(AccessType.PROPERTY)
// la manera de acceder a la entidad--import javax.persistence.AccessType;
public class Application extends DomainEntity {

	// Attributes

	private Date	creationMoment;
	private String	answer;
	private String	linkCode;
	private String	status;
	private Date	submittedMoment;


	// Getters

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getAnswer() {
		return this.answer;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@URL
	public String getLinkCode() {
		return this.linkCode;
	}

	@Pattern(regexp = "^PENDING|SUBMITTED|ACCEPTED|REJECTED$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatus() {
		return this.status;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmittedMoment() {
		return this.submittedMoment;
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


	// Relationships

	private Position	position;
	private Problem		problem;
	private Rookie		rookie;
	private Curricula	curricula;


	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}

	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}
	
	@OneToOne
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

}
