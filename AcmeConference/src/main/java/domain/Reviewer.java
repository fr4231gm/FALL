package domain;

import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Collection;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Reviewer extends DomainEntity {

	// Atributos
	private String					keywords;
	private Collection<Submission>					submissions;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getKeywords(){
		return this.keywords;
	}

	@ManyToMany
	public Collection<Submission> getSubmissions(){
		return this.submissions;
	}



	// Setters
	public void setKeywords(final String keywords){
		this.keywords = keywords; 
	}

	public void setSubmissions(final Collection<Submission> submissions){
		this.submissions = submissions; 
	}

}