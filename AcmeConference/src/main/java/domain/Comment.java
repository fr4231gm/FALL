package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity 
@Access(AccessType.PROPERTY) 
public class Comment extends DomainEntity {

	// Atributos
	private String					title;
	private Date					moment;
	private String					author;
	private String					text;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTitle(){
		return this.title;
	}

	@Past
	@NotNull
	public Date getMoment(){
		return this.moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getAuthor(){
		return this.author;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getText(){
		return this.text;
	}

	// Setters
	public void setTitle(final String title){
		this.title = title; 
	}

	public void setMoment(final Date moment){
		this.moment = moment; 
	}

	public void setAuthor(final String author){
		this.author = author; 
	}

	public void setText(final String text){
		this.text = text; 
	}
}