
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Atributos
	private String	subject;
	private String	body;
	private String	tags;
	private String	priority;
	private Date	moment;
	private boolean	isSpam;


	// Constructor
	public Message() {
		super();
	}

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	public String getTags() {
		return this.tags;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPriority() {
		return this.priority;
	}

	public boolean getIsSpam() {
		return this.isSpam;
	}

	// Setters
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}


	// Relaciones
	private Actor				sender;
	private Collection<Actor>	recipients;
	private Collection<Box>		boxes;


	// Getters
	@NotEmpty
	@ManyToMany
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	@ManyToMany
	public Collection<Box> getBoxes() {
		return this.boxes;
	}

	// Setters
	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public void setBoxes(final Collection<Box> boxes) {
		this.boxes = boxes;
	}

	public void setIsSpam(final boolean isSpam) {
		this.isSpam = isSpam;
	}
}
