package domain;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;
import javax.validation.constraints.Past;

@Entity 
@Access(AccessType.PROPERTY) 
public class Message extends DomainEntity {

	// Atributos
	private Date					moment;
	private String					subject;
	private String					body;
	private String					topic;
	private Actor					sender;
	private Collection<Actor>					recipients;


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

	@ManyToMany
	public Collection<Actor> getRecipients(){
		return this.recipients;
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

	public void setRecipients(final Collection<Actor> recipients){
		this.recipients = recipients; 
	}

}