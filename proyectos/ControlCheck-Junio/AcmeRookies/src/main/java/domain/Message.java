
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	//Atributos ---------------------------------------------------------------
	private Date	moment;
	private String	subject;
	private String	body;
	private String	tags;
	private boolean	isSpam;


	//Constructor -------------------------------------------------------------
	public Message() {
		super();
	}

	//Getters -----------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

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

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTags() {
		return this.tags;
	}

	public boolean getIsSpam() {
		return this.isSpam;
	}

	//Setters -----------------------------------------------------------------

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public void setIsSpam(final boolean isSpam) {
		this.isSpam = isSpam;
	}


	//Relations ---------------------------------------------------------------
	private Actor	sender;
	private Actor	recipient;


	//Getters -----------------------------------------------------------------

	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	@ManyToOne
	public Actor getRecipient() {
		return this.recipient;
	}

	//Setters -----------------------------------------------------------------
	public void setSender(final Actor actor) {
		this.sender = actor;
	}

	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
		
	}

}
