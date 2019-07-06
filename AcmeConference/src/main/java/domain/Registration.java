package domain;

import javax.persistence.ManyToOne;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity 
@Access(AccessType.PROPERTY) 
public class Registration extends DomainEntity {

	// Atributos
	private CreditCard					creditCard;
	private Conference					conference;
	private Author					author;


	// Getters
	@Valid
	@NotNull
	public CreditCard getCreditCard(){
		return this.creditCard;
	}

	@ManyToOne
	public Conference getConference(){
		return this.conference;
	}

	@ManyToOne
	public Author getAuthor(){
		return this.author;
	}



	// Setters
	public void setCreditCard(final CreditCard creditCard){
		this.creditCard = creditCard; 
	}

	public void setConference(final Conference conference){
		this.conference = conference; 
	}

	public void setAuthor(final Author author){
		this.author = author; 
	}

}