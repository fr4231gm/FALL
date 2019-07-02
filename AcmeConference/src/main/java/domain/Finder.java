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
	private String					keyword;
	private String					acronym;
	private String					venue;
	private String					summary;
	private Date					startdate;
	private Date					enddate;
	private Double					fee;
	private Collection<Conference>					conferences;


	// Getters
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getKeyword(){
		return this.keyword;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getAcronym(){
		return this.acronym;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getVenue(){
		return this.venue;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getSummary(){
		return this.summary;
	}

	public Date getStartdate(){
		return this.startdate;
	}

	public Date getEnddate(){
		return this.enddate;
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
	public void setKeyword(final String keyword){
		this.keyword = keyword; 
	}

	public void setAcronym(final String acronym){
		this.acronym = acronym; 
	}

	public void setVenue(final String venue){
		this.venue = venue; 
	}

	public void setSummary(final String summary){
		this.summary = summary; 
	}

	public void setStartdate(final Date startdate){
		this.startdate = startdate; 
	}

	public void setEnddate(final Date enddate){
		this.enddate = enddate; 
	}

	public void setFee(final Double fee){
		this.fee = fee; 
	}

	public void setConferences(final Collection<Conference> conferences){
		this.conferences = conferences; 
	}

}