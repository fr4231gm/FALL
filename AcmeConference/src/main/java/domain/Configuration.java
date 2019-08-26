package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity 
@Access(AccessType.PROPERTY) 
public class Configuration extends DomainEntity {

	// Atributos
	private String					systemName;
	private String					make;
	private String					language;
	private String					banner;
	private String					countryCode;
	private String					welcomeMessage;
	private String					topics;
	private String					voidWords;
	private String					buzzWords;


	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getsystemName(){
		return this.systemName;
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
	public String getCountryCode(){
		return this.countryCode;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getWelcomeMessage(){
		return this.welcomeMessage;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getTopics(){
		return this.topics;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Column(length = 5000)
	public String getVoidWords(){
		return this.voidWords;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE) 
	public String getBuzzWords(){
		return this.buzzWords;
	}



	// Setters
	public void setsystemName(final String systemName){
		this.systemName = systemName; 
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

	public void setCountryCode(final String countryCode){
		this.countryCode = countryCode; 
	}

	public void setWelcomeMessage(final String welcomeMessage){
		this.welcomeMessage = welcomeMessage; 
	}

	public void setTopics(final String topics){
		this.topics = topics; 
	}

	public void setVoidWords(final String voidWords){
		this.voidWords = voidWords; 
	}

	public void setBuzzWords(final String buzzWords){
		this.buzzWords = buzzWords; 
	}

}