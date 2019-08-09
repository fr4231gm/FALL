package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity 
@Access(AccessType.PROPERTY) 
public class Message extends DomainEntity {

	// Atributos
	private Date					moment;
	private String					subject;
	private String					body;
	private String					topic;
	private Actor					sender;
	private Actor					recipient;
	private boolean 				isCopy;


	// Getters
	@Past
	public Date getMoment(){
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getSubject(){
		return this.subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getBody(){
		return this.body;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTopic(){
		return this.topic;
	}

	@ManyToOne
	public Actor getSender(){
		return this.sender;
	}

	@ManyToOne
	public Actor getRecipient(){
		return this.recipient;
	}
	
	@NotNull
	public boolean GetIsCopy() {
		return isCopy;
	}

	// Setters
	public void setMoment(final Date moment){
		this.moment = moment; 
	}

	public void setSubject(final String subject){
		this.subject = subject; 
	}

	public void setBody(final String body){
		this.body = body; 
	}

	public void setTopic(final String topic){
		this.topic = topic; 
	}

	public void setSender(final Actor sender){
		this.sender = sender; 
	}

	public void setRecipient(final Actor recipient){
		this.recipient = recipient; 
	}

	public void setIsCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

}