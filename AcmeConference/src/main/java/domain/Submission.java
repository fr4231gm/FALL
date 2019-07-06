package domain;

import javax.persistence.OneToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity 
@Access(AccessType.PROPERTY) 
public class Submission extends DomainEntity {

	// Atributos
	private String					ticker;
	private Date					moment;
	private String					cameraReadyPaper;
	private String					status;
	private Paper					paper;
	private Conference					conference;
	private Author					author;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTicker(){
		return this.ticker;
	}

	@Past
	@NotNull
	public Date getMoment(){
		return this.moment;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getCameraReadyPaper(){
		return this.cameraReadyPaper;
	}

	@NotBlank
	@Pattern(regexp = "^UNDER-REVIEW|REJECTED|ACCEPTED")
	public String getStatus(){
		return this.status;
	}

	@OneToOne
	public Paper getPaper(){
		return this.paper;
	}

	@OneToOne
	public Conference getConference(){
		return this.conference;
	}



	// Setters
	public void setTicker(final String ticker){
		this.ticker = ticker; 
	}

	public void setMoment(final Date moment){
		this.moment = moment; 
	}

	public void setCameraReadyPaper(final String cameraReadyPaper){
		this.cameraReadyPaper = cameraReadyPaper; 
	}

	public void setStatus(final String status){
		this.status = status; 
	}

	public void setPaper(final Paper paper){
		this.paper = paper; 
	}

	public void setConference(final Conference conference){
		this.conference = conference; 
	}
	
	public void setAuthor(final Author author){
		this.author = author; 
	}
	
	@ManyToOne
	public Author getAuthor(){
		return this.author;
	}

}