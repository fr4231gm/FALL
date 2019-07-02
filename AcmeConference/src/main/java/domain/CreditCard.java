package domain;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class CreditCard extends DomainEntity {

	// Atributos
	private String					holder;
	private String					number;
	private Integer					expirationmonth;
	private Integer					expirationyear;
	private String					make;
	private Integer					cvv;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getHolder(){
		return this.holder;
	}

	@NotBlank
	@CreditCardNumber
	public String getNumber(){
		return this.number;
	}

	@Range(min = 1, max = 12) 
	@NotNull
	public Integer getExpirationmonth(){
		return this.expirationmonth;
	}

	@Range(min = 1, max = 99) 
	@NotNull
	public Integer getExpirationyear(){
		return this.expirationyear;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getMake(){
		return this.make;
	}

	@Range(min = 100, max = 999) 
	@NotNull
	public Integer getCvv(){
		return this.cvv;
	}



	// Setters
	public void setHolder(final String holder){
		this.holder = holder; 
	}

	public void setNumber(final String number){
		this.number = number; 
	}

	public void setExpirationmonth(final Integer expirationmonth){
		this.expirationmonth = expirationmonth; 
	}

	public void setExpirationyear(final Integer expirationyear){
		this.expirationyear = expirationyear; 
	}

	public void setMake(final String make){
		this.make = make; 
	}

	public void setCvv(final Integer cvv){
		this.cvv = cvv; 
	}

}