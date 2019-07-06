package domain;

import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Finder extends DomainEntity {

	// Atributos
	private String					keyWord;
	private Date					startDate;
	private Date					endDate;
	private Double					fee;
	private Collection<Conference>					conferences;


	// Getters
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getKeyWord(){
		return this.keyWord;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public Date getendDate(){
		return this.endDate;
	}

	@Range(min = 0, max = 1) 
	public Double getFee(){
		return this.fee;
	}

	@ManyToMany
	public Collection<Conference> getConferences(){
		return this.conferences;
	}



	// Setters
	public void setKeyWord(final String keyWord){
		this.keyWord = keyWord; 
	}

	public void setStartDate(final Date startDate){
		this.startDate = startDate; 
	}

	public void setendDate(final Date endDate){
		this.endDate = endDate; 
	}

	public void setFee(final Double fee){
		this.fee = fee; 
	}

	public void setConferences(final Collection<Conference> conferences){
		this.conferences = conferences; 
	}

}