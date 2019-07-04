package domain;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Section extends DomainEntity {

	// Atributos
	private String					title;
	private String					summary;
	private String					pictures;


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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Column(length = 5000)
	public String getPictures(){
		return this.pictures;
	}



	// Setters
	public void setTitle(final String title){
		this.title = title; 
	}

	public void setSummary(final String summary){
		this.summary = summary; 
	}

	public void setPictures(final String pictures){
		this.pictures = pictures; 
	}

}