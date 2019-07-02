package domain;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Paper extends DomainEntity {

	// Atributos
	private String					title;
	private String					summary;
	private String					document;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTitle(){
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getSummary(){
		return this.summary;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getDocument(){
		return this.document;
	}



	// Setters
	public void setTitle(final String title){
		this.title = title; 
	}

	public void setSummary(final String summary){
		this.summary = summary; 
	}

	public void setDocument(final String document){
		this.document = document; 
	}

}