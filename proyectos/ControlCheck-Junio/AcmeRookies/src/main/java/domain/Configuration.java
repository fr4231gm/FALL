
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "language")
})
public class Configuration extends DomainEntity {

	// Atributos
	private String	systemName;
	private String	banner;
	private String	welcomeMessage;
	private String	countryCode;
	private String	spamWords;
	private String	language;
	private Integer	finderLifeSpan;
	private Integer	maxFinder;
	private String 	makes;
	private Boolean rebrandingNotificated;
	private Double	vat;
	private Double	fare;

	// Getters
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSystemName() {
		return this.systemName;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpamWords() {
		return this.spamWords;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	@NotNull
	@Range(min = 1, max = 24)
	public Integer getFinderLifeSpan() {
		return this.finderLifeSpan;
	}

	@NotNull
	@Range(min = 10, max = 100)
	public Integer getMaxFinder() {
		return this.maxFinder;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLanguage() {
		return this.language;
	}
	
	@NotBlank
	public String getMakes(){
		return this.makes;
	}
	
	@NotNull
	@Range(min = 0, max = 1)
	public Double getVat() {
		return this.vat;
	}

	@NotNull
	public Double getFare() {
		return this.fare;
	}
	
	@NotNull
	public Boolean getRebrandingNotificated() {
		return rebrandingNotificated;
	}

	// Setters
	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public void setFinderLifeSpan(final Integer finderLifeSpan) {
		this.finderLifeSpan = finderLifeSpan;
	}

	public void setMaxFinder(final Integer maxFinder) {
		this.maxFinder = maxFinder;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}
	
	public void setMakes(final String makes){
		this.makes = makes;
	}

	public void setRebrandingNotificated(Boolean rebrandingNotificated) {
		this.rebrandingNotificated = rebrandingNotificated;
	}
	
	public void setVat(final Double vat) {
		this.vat = vat;
	}

	public void setFare(final Double fare) {
		this.fare = fare;
	}
}
