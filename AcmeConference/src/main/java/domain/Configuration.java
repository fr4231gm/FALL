package domain;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Access; 
import javax.persistence.AccessType; 
import javax.persistence.Entity;

@Entity 
@Access(AccessType.PROPERTY) 
public class Configuration extends DomainEntity {

	// Atributos
	private String					systemname;
	private String					make;
	private String					language;
	private String					banner;
	private String					countrycode;
	private String					welcomemessage;
	private String					topics;
	private String					voidwords;
	private String					buzzwords;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getSystemname(){
		return this.systemname;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getMake(){
		return this.make;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getLanguage(){
		return this.language;
	}

	@NotBlank
	@URL
	public String getBanner(){
		return this.banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getCountrycode(){
		return this.countrycode;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getWelcomemessage(){
		return this.welcomemessage;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTopics(){
		return this.topics;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getVoidwords(){
		return this.voidwords;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getBuzzwords(){
		return this.buzzwords;
	}



	// Setters
	public void setSystemname(final String systemname){
		this.systemname = systemname; 
	}

	public void setMake(final String make){
		this.make = make; 
	}

	public void setLanguage(final String language){
		this.language = language; 
	}

	public void setBanner(final String banner){
		this.banner = banner; 
	}

	public void setCountrycode(final String countrycode){
		this.countrycode = countrycode; 
	}

	public void setWelcomemessage(final String welcomemessage){
		this.welcomemessage = welcomemessage; 
	}

	public void setTopics(final String topics){
		this.topics = topics; 
	}

	public void setVoidwords(final String voidwords){
		this.voidwords = voidwords; 
	}

	public void setBuzzwords(final String buzzwords){
		this.buzzwords = buzzwords; 
	}

}