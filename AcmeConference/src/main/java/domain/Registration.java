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
	private CreditCard					creditcard;
	private Conference					conference;
	private Author					author;


	// Getters
	@Valid
	@NotNull
	public CreditCard getCreditcard(){
		return this.creditcard;
	}

	@ManyToOne
	public Conference getConfernece(){
		return this.conference;
	}

	@ManyToOne
	public Author getAuthor(){
		return this.author;
	}



	// Setters
	public void setCreditcard(final CreditCard creditcard){
		this.creditcard = creditcard; 
	}

	public void setConfernece(final Conference conference){
		this.conference = conference; 
	}

	public void setAuthor(final Author author){
		this.author = author; 
	}

}